package com.clients.clients.documentType;

import com.clients.shared.entities.DocumentType;
import java.util.List;

public interface CrudDocumentTypeService {
    DocumentType createDocumentType(DocumentType documentType);
    DocumentType getDocumentTypeById(Long id);
    List<DocumentType> getAllDocumentTypes();
    DocumentType updateDocumentType(Long id, DocumentType documentType);
    void deleteDocumentType(Long id);
}
