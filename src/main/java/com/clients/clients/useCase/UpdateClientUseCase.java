package com.clients.clients.useCase;

import com.clients.shared.entities.Client;
import com.clients.shared.entities.PersonType;
import com.clients.shared.entities.DocumentType;
import com.clients.clients.service.CrudClientService;
import com.clients.clients.personType.CrudPersonTypeService;
import com.clients.clients.documentType.CrudDocumentTypeService;
import com.clients.clients.dto.ClientUpdateDTO;
import com.clients.shared.dto.NotFoundErrorDTO;
import org.springframework.stereotype.Component;

@Component
public class UpdateClientUseCase {
    private final CrudClientService crudClientService;
    private final CrudPersonTypeService personTypeService;
    private final CrudDocumentTypeService documentTypeService;

    public UpdateClientUseCase(CrudClientService crudClientService,
                               CrudPersonTypeService personTypeService,
                               CrudDocumentTypeService documentTypeService) {
        this.crudClientService = crudClientService;
        this.personTypeService = personTypeService;
        this.documentTypeService = documentTypeService;
    }

    public Object execute(Long id, ClientUpdateDTO dto) {
        Client existing = crudClientService.getClientById(id);
        if (existing == null) {
            return new NotFoundErrorDTO("Cliente no encontrado", "Client", id);
        }
        if (dto.getPersonTypeId() != null) {
            PersonType personType = personTypeService.getPersonTypeById(dto.getPersonTypeId());
            if (personType == null) {
                return new NotFoundErrorDTO("Tipo de persona no encontrado", "PersonType", dto.getPersonTypeId());
            }
            existing.setPersonType(personType);
        }
        if (dto.getDocumentTypeId() != null) {
            DocumentType documentType = documentTypeService.getDocumentTypeById(dto.getDocumentTypeId());
            if (documentType == null) {
                return new NotFoundErrorDTO("Tipo de documento no encontrado", "DocumentType", dto.getDocumentTypeId());
            }
            existing.setDocumentType(documentType);
        }
        if (dto.getNombre() != null) existing.setNombre(dto.getNombre());
        if (dto.getApellido() != null) existing.setApellido(dto.getApellido());
        if (dto.getBirthDate() != null) existing.setBirthDate(dto.getBirthDate());
        if (dto.getIdentity() != null) existing.setIdentity(dto.getIdentity());
        if (dto.getDireccion() != null) existing.setDireccion(dto.getDireccion());
        if (dto.getCelular() != null) existing.setCelular(dto.getCelular());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getPersonTypeId() != null) {
            PersonType personType = personTypeService.getPersonTypeById(dto.getPersonTypeId());
            if (personType == null) {
                return new NotFoundErrorDTO("Tipo de persona no encontrado", "PersonType", dto.getPersonTypeId());
            }
            existing.setPersonType(personType);
        }
        if (dto.getDocumentTypeId() != null) {
            DocumentType documentType = documentTypeService.getDocumentTypeById(dto.getDocumentTypeId());
            if (documentType == null) {
                return new NotFoundErrorDTO("Tipo de documento no encontrado", "DocumentType", dto.getDocumentTypeId());
            }
            existing.setDocumentType(documentType);
        }
        return crudClientService.updateClient(id, existing);
    }
}
