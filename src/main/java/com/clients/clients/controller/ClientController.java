package com.clients.clients.controller;

import com.clients.clients.useCase.CreateClientUseCase;
import com.clients.clients.useCase.UpdateClientUseCase;
import com.clients.clients.dto.AdminStatsDTO;
import com.clients.clients.dto.ClientRequestDTO;
import com.clients.clients.dto.ClientUpdateDTO;
import com.clients.clients.dto.ClientSearchFilterDTO;
import com.clients.clients.dto.ClientListResponseDTO;
import com.clients.clients.dto.ClientResponseDTO;
import com.clients.clients.service.AdminStatsService;
import java.util.List;
import com.clients.clients.service.ClientSearchService;
import com.clients.shared.entities.Client;
import com.clients.shared.dto.NotFoundErrorDTO;
import com.clients.shared.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Operaciones CRUD para clientes")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final ClientSearchService clientSearchService;
    private final com.clients.clients.service.CrudClientService crudClientService;
    private final AdminStatsService adminStatsService;

    @Autowired
    public ClientController(CreateClientUseCase createClientUseCase, UpdateClientUseCase updateClientUseCase, ClientSearchService clientSearchService, com.clients.clients.service.CrudClientService crudClientService, AdminStatsService adminStatsService) {
        this.createClientUseCase = createClientUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.clientSearchService = clientSearchService;
        this.crudClientService = crudClientService;
        this.adminStatsService = adminStatsService;
    }
    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista clientes paginados y filtrados")
    public ApiResponse<ClientListResponseDTO> listClients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long documentTypeId,
            @RequestParam(required = false) Long personTypeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        ClientSearchFilterDTO filter = new ClientSearchFilterDTO();
        filter.setSearch(search);
        filter.setDocumentTypeId(documentTypeId);
        filter.setPersonTypeId(personTypeId);
        filter.setPage(page);
        filter.setSize(size);
        ClientListResponseDTO response = clientSearchService.searchClients(filter);
        return new ApiResponse<>(true, "Clientes listados", response);
    }

    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente")
    public ApiResponse<?> createClient(@Valid @RequestBody ClientRequestDTO dto) {
        try {
            Object result = createClientUseCase.execute(dto);
            if (result instanceof NotFoundErrorDTO) {
                return new ApiResponse<>(false, ((NotFoundErrorDTO) result).getMessage(), result);
            }
            // Mapear Client a ClientResponseDTO
            Client client = (Client) result;
            ClientResponseDTO responseDto = toResponseDTO(client);
            return new ApiResponse<>(true, "Cliente creado", responseDto);
        } catch (IllegalArgumentException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente")
    public ApiResponse<?> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO dto) {
        Object result = updateClientUseCase.execute(id, dto);
        if (result instanceof NotFoundErrorDTO) {
            return new ApiResponse<>(false, ((NotFoundErrorDTO) result).getMessage(), result);
        }
        return new ApiResponse<>(true, "Cliente actualizado", result);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente (soft delete)", description = "Desactiva un cliente (soft delete)")
    public ApiResponse<?> deleteClient(@PathVariable Long id) {
        try {
            crudClientService.setClientActiveStatus(id, false);
            return new ApiResponse<>(true, "Cliente desactivado", null);
        } catch (Exception ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

     @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente activo por ID", description = "Obtiene los datos de un cliente solo si está activo (no ha sido eliminado con soft delete). Retorna error si no existe o está inactivo.")
    public ApiResponse<?> getClientById(@PathVariable Long id) {
        return crudClientService.getActiveClientById(id)
                .map(client -> new ApiResponse<>(true, "Cliente encontrado", toResponseDTO(client)))
                .orElse(new ApiResponse<>(false, "Cliente no encontrado o inactivo", null));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos los clientes (incluso inactivos)", description = "Lista todos los clientes registrados en la base de datos, incluyendo aquellos que han sido desactivados (soft delete). El campo 'active' indica el estado de cada cliente.")
    public ApiResponse<?> getAllClientsIncludingInactive() {
        List<Client> clients = crudClientService.getAllClientsIncludingInactive();
        List<ClientResponseDTO> responseDtos = clients.stream()
                .map(this::toResponseDTO)
                .collect(java.util.stream.Collectors.toList());
        return new ApiResponse<>(true, "Clientes listados", responseDtos);
    }

    @GetMapping("/{id}/full")
    @Operation(summary = "Obtener cliente por ID (sin filtro de estado)", description = "Obtiene los datos de un cliente por su ID independientemente de si está activo o inactivo. Útil para administración y auditoría.")
    public ApiResponse<?> getClientByIdIncludingInactive(@PathVariable Long id) {
        return crudClientService.getClientByIdIncludingInactive(id)
                .map(client -> new ApiResponse<>(true, "Cliente encontrado", toResponseDTO(client)))
                .orElse(new ApiResponse<>(false, "Cliente no encontrado", null));
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activar cliente", description = "Activa un cliente previamente desactivado")
    public ApiResponse<?> activateClient(@PathVariable Long id) {
        try {
            crudClientService.setClientActiveStatus(id, true);
            return new ApiResponse<>(true, "Cliente activado", null);
        } catch (Exception ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas de administración", description = "Obtiene estadísticas agregadas de clientes (totales, por tipo, etc.)")
    public ApiResponse<AdminStatsDTO> getAdminStats() {
        AdminStatsDTO stats = adminStatsService.getStats();
        return new ApiResponse<>(true, "Estadísticas de clientes", stats);
    }

    private ClientResponseDTO toResponseDTO(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setNombre(client.getNombre());
        dto.setApellido(client.getApellido());
        dto.setBirthDate(client.getBirthDate());
        dto.setIdentity(client.getIdentity());
        dto.setDireccion(client.getDireccion());
        dto.setCelular(client.getCelular());
        dto.setEmail(client.getEmail());
        dto.setActive(client.isActive());
        if (client.getDocumentType() != null) {
            ClientResponseDTO.DocumentTypeDTO docDto = new ClientResponseDTO.DocumentTypeDTO();
            docDto.setId(client.getDocumentType().getId());
            docDto.setType(client.getDocumentType().getType());
            dto.setDocumentType(docDto);
        }
        if (client.getPersonType() != null) {
            ClientResponseDTO.PersonTypeDTO perDto = new ClientResponseDTO.PersonTypeDTO();
            perDto.setId(client.getPersonType().getId());
            perDto.setType(client.getPersonType().getType());
            dto.setPersonType(perDto);
        }
        return dto;
    }
}
