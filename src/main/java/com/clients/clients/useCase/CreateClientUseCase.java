package com.clients.clients.useCase;

import com.clients.shared.entities.Client;
import com.clients.shared.entities.PersonType;
import com.clients.shared.entities.DocumentType;
import com.clients.clients.service.CrudClientService;
import com.clients.clients.personType.CrudPersonTypeService;
import com.clients.clients.documentType.CrudDocumentTypeService;
import com.clients.clients.dto.ClientRequestDTO;
import com.clients.shared.dto.NotFoundErrorDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateClientUseCase {
    private final CrudClientService crudClientService;
    private final CrudPersonTypeService personTypeService;
    private final CrudDocumentTypeService documentTypeService;

    public CreateClientUseCase(CrudClientService crudClientService,
                               CrudPersonTypeService personTypeService,
                               CrudDocumentTypeService documentTypeService) {
        this.crudClientService = crudClientService;
        this.personTypeService = personTypeService;
        this.documentTypeService = documentTypeService;
    }

    public Object execute(ClientRequestDTO dto) {
        // Validar PersonType
        PersonType personType = personTypeService.getPersonTypeById(dto.getPersonTypeId());
        if (personType == null) {
            return new NotFoundErrorDTO("Tipo de persona no encontrado", "PersonType", dto.getPersonTypeId());
        }
        // Validar DocumentType
        DocumentType documentType = documentTypeService.getDocumentTypeById(dto.getDocumentTypeId());
        if (documentType == null) {
            return new NotFoundErrorDTO("Tipo de documento no encontrado", "DocumentType", dto.getDocumentTypeId());
        }
        // Mapear DTO a entidad
        Client client = new Client();
        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setBirthDate(dto.getBirthDate());
        client.setIdentity(dto.getIdentity());
        client.setDireccion(dto.getDireccion());
        client.setCelular(dto.getCelular());
        client.setEmail(dto.getEmail());
        client.setPersonType(personType);
        client.setDocumentType(documentType);
        return crudClientService.createClient(client);
    }
}
