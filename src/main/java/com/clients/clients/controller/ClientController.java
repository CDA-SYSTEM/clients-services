package com.clients.clients.controller;

import com.clients.clients.useCase.CreateClientUseCase;
import com.clients.clients.dto.ClientRequestDTO;
import com.clients.shared.entities.Client;
import com.clients.shared.dto.NotFoundErrorDTO;
import com.clients.shared.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Operaciones CRUD para clientes")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;

    @Autowired
    public ClientController(CreateClientUseCase createClientUseCase) {
        this.createClientUseCase = createClientUseCase;
    }

    @PostMapping
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente")
    public ApiResponse<?> createClient(@RequestBody ClientRequestDTO dto) {
        Object result = createClientUseCase.execute(dto);
        if (result instanceof NotFoundErrorDTO) {
            return new ApiResponse<>(false, ((NotFoundErrorDTO) result).getMessage(), result);
        }
        return new ApiResponse<>(true, "Cliente creado", result);
    }
}
