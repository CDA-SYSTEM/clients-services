package com.clients.clients.documentType.useCase;

import com.clients.shared.entities.DocumentType;
import com.clients.clients.documentType.CrudDocumentTypeService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ListDocumentTypesUseCase {
    private final CrudDocumentTypeService documentTypeService;

    public ListDocumentTypesUseCase(CrudDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    public List<DocumentType> execute() {
        return documentTypeService.getAllDocumentTypes();
    }
}
