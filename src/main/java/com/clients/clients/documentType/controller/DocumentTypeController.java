package com.clients.clients.documentType.controller;

import com.clients.shared.utils.ApiResponse;
import com.clients.clients.documentType.useCase.ListDocumentTypesUseCase;
import com.clients.clients.documentType.dto.DocumentTypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/document-types")
@Tag(name = "Document Types", description = "Operaciones para tipos de documento")
public class DocumentTypeController {

    private final ListDocumentTypesUseCase listDocumentTypesUseCase;

    @Autowired
    public DocumentTypeController(ListDocumentTypesUseCase listDocumentTypesUseCase) {
        this.listDocumentTypesUseCase = listDocumentTypesUseCase;
    }

    @GetMapping
    @Operation(summary = "Listar tipos de documento", description = "Obtiene todos los tipos de documento")
    public ApiResponse<List<DocumentTypeResponseDTO>> listDocumentTypes() {
        List<DocumentTypeResponseDTO> dtos = listDocumentTypesUseCase.execute().stream()
            .map(entity -> {
                DocumentTypeResponseDTO dto = new DocumentTypeResponseDTO();
                dto.setId(entity.getId());
                dto.setType(entity.getType());
                dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
                return dto;
            })
            .toList();
        return new ApiResponse<>(true, "Tipos de documento", dtos);
    }
}
