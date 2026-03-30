package com.clients.clients.documentType;

import com.clients.shared.entities.DocumentType;
import org.springframework.stereotype.Service;
import java.util.List;
import com.clients.clients.documentType.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CrudDocumentTypeServiceImpl implements CrudDocumentTypeService {
    private final DocumentTypeRepository documentTypeRepository;

    @Autowired
    public CrudDocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public DocumentType createDocumentType(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    @Override
    public DocumentType getDocumentTypeById(Long id) {
        return documentTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    @Override
    public DocumentType updateDocumentType(Long id, DocumentType documentType) {
        if (documentTypeRepository.existsById(id)) {
            documentType.setId(id);
            return documentTypeRepository.save(documentType);
        }
        return null;
    }

    @Override
    public void deleteDocumentType(Long id) {
        documentTypeRepository.deleteById(id);
    }
}
