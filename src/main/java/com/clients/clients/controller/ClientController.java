package com.clients.clients.controller;

import com.clients.clients.useCase.CreateClientUseCase;
import com.clients.clients.useCase.UpdateClientUseCase;
import com.clients.clients.dto.ClientRequestDTO;
import com.clients.clients.dto.ClientUpdateDTO;
import com.clients.clients.dto.ClientSearchFilterDTO;
import com.clients.clients.dto.ClientListResponseDTO;
import com.clients.clients.dto.ClientResponseDTO;
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

    @Autowired
    public ClientController(CreateClientUseCase createClientUseCase, UpdateClientUseCase updateClientUseCase, ClientSearchService clientSearchService, com.clients.clients.service.CrudClientService crudClientService) {
        this.createClientUseCase = createClientUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.clientSearchService = clientSearchService;
        this.crudClientService = crudClientService;
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
    public ApiResponse<?> createClient(@RequestBody ClientRequestDTO dto) {
        try {
            Object result = createClientUseCase.execute(dto);
            if (result instanceof NotFoundErrorDTO) {
                return new ApiResponse<>(false, ((NotFoundErrorDTO) result).getMessage(), result);
            }
            // Mapear Client a ClientResponseDTO
            Client client = (Client) result;
            ClientResponseDTO responseDto = new ClientResponseDTO();
            responseDto.setId(client.getId());
            responseDto.setNombre(client.getNombre());
            responseDto.setApellido(client.getApellido());
            responseDto.setBirthDate(client.getBirthDate());
            responseDto.setIdentity(client.getIdentity());
            responseDto.setDireccion(client.getDireccion());
            responseDto.setCelular(client.getCelular());
            responseDto.setEmail(client.getEmail());
            if (client.getDocumentType() != null) {
                ClientResponseDTO.DocumentTypeDTO docDto = new ClientResponseDTO.DocumentTypeDTO();
                docDto.setId(client.getDocumentType().getId());
                docDto.setType(client.getDocumentType().getType());
                responseDto.setDocumentType(docDto);
            }
            if (client.getPersonType() != null) {
                ClientResponseDTO.PersonTypeDTO perDto = new ClientResponseDTO.PersonTypeDTO();
                perDto.setId(client.getPersonType().getId());
                perDto.setType(client.getPersonType().getType());
                responseDto.setPersonType(perDto);
            }
            return new ApiResponse<>(true, "Cliente creado", responseDto);
        } catch (IllegalArgumentException ex) {
            return new ApiResponse<>(false, ex.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente")
    public ApiResponse<?> updateClient(@PathVariable Long id, @RequestBody ClientUpdateDTO dto) {
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
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene los datos de un cliente activo por su ID")
    public ApiResponse<?> getClientById(@PathVariable Long id) {
        return crudClientService.getActiveClientById(id)
                .map(client -> {
                    ClientResponseDTO responseDto = new ClientResponseDTO();
                    responseDto.setId(client.getId());
                    responseDto.setNombre(client.getNombre());
                    responseDto.setApellido(client.getApellido());
                    responseDto.setBirthDate(client.getBirthDate());
                    responseDto.setIdentity(client.getIdentity());
                    responseDto.setDireccion(client.getDireccion());
                    responseDto.setCelular(client.getCelular());
                    responseDto.setEmail(client.getEmail());
                    if (client.getDocumentType() != null) {
                        ClientResponseDTO.DocumentTypeDTO docDto = new ClientResponseDTO.DocumentTypeDTO();
                        docDto.setId(client.getDocumentType().getId());
                        docDto.setType(client.getDocumentType().getType());
                        responseDto.setDocumentType(docDto);
                    }
                    if (client.getPersonType() != null) {
                        ClientResponseDTO.PersonTypeDTO perDto = new ClientResponseDTO.PersonTypeDTO();
                        perDto.setId(client.getPersonType().getId());
                        perDto.setType(client.getPersonType().getType());
                        responseDto.setPersonType(perDto);
                    }
                    return new ApiResponse<>(true, "Cliente encontrado", responseDto);
                })
                .orElse(new ApiResponse<>(false, "Cliente no encontrado o inactivo", null));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos los clientes", description = "Lista todos los clientes incluyendo los inactivos")
    public ApiResponse<?> getAllClientsIncludingInactive() {
        List<Client> clients = crudClientService.getAllClientsIncludingInactive();
        List<ClientResponseDTO> responseDtos = clients.stream().map(client -> {
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
        }).collect(java.util.stream.Collectors.toList());
        return new ApiResponse<>(true, "Clientes listados", responseDtos);
    }

    @GetMapping("/{id}/full")
    @Operation(summary = "Obtener cliente por ID (sin filtro)", description = "Obtiene los datos de un cliente por su ID sin importar su estado activo")
    public ApiResponse<?> getClientByIdIncludingInactive(@PathVariable Long id) {
        return crudClientService.getClientByIdIncludingInactive(id)
                .map(client -> {
                    ClientResponseDTO responseDto = new ClientResponseDTO();
                    responseDto.setId(client.getId());
                    responseDto.setNombre(client.getNombre());
                    responseDto.setApellido(client.getApellido());
                    responseDto.setBirthDate(client.getBirthDate());
                    responseDto.setIdentity(client.getIdentity());
                    responseDto.setDireccion(client.getDireccion());
                    responseDto.setCelular(client.getCelular());
                    responseDto.setEmail(client.getEmail());
                    responseDto.setActive(client.isActive());
                    if (client.getDocumentType() != null) {
                        ClientResponseDTO.DocumentTypeDTO docDto = new ClientResponseDTO.DocumentTypeDTO();
                        docDto.setId(client.getDocumentType().getId());
                        docDto.setType(client.getDocumentType().getType());
                        responseDto.setDocumentType(docDto);
                    }
                    if (client.getPersonType() != null) {
                        ClientResponseDTO.PersonTypeDTO perDto = new ClientResponseDTO.PersonTypeDTO();
                        perDto.setId(client.getPersonType().getId());
                        perDto.setType(client.getPersonType().getType());
                        responseDto.setPersonType(perDto);
                    }
                    return new ApiResponse<>(true, "Cliente encontrado", responseDto);
                })
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
}
