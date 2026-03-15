package com.clients.clients.personType.controller;

import com.clients.shared.utils.ApiResponse;
import com.clients.clients.personType.useCase.ListPersonTypesUseCase;
import com.clients.clients.personType.dto.PersonTypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/person-types")
@Tag(name = "Person Types", description = "Operaciones para tipos de persona")
public class PersonTypeController {

    private final ListPersonTypesUseCase listPersonTypesUseCase;

    @Autowired
    public PersonTypeController(ListPersonTypesUseCase listPersonTypesUseCase) {
        this.listPersonTypesUseCase = listPersonTypesUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de persona", description = "Obtiene todos los tipos de persona")
    public ApiResponse<List<PersonTypeResponseDTO>> listPersonTypes() {
        List<PersonTypeResponseDTO> dtos = listPersonTypesUseCase.execute().stream()
            .map(entity -> {
                PersonTypeResponseDTO dto = new PersonTypeResponseDTO();
                dto.setId(entity.getId());
                dto.setType(entity.getType());
                dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
                return dto;
            })
            .toList();
        return new ApiResponse<>(true, "Tipos de persona", dtos);
    }
}
