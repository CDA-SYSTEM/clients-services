package com.clients.clients.service;

import com.clients.shared.entities.Client;
import com.clients.shared.entities.PersonType;
import com.clients.shared.entities.DocumentType;
import com.clients.shared.dto.NotFoundErrorDTO;
import com.clients.clients.personType.CrudPersonTypeService;
import com.clients.clients.documentType.CrudDocumentTypeService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrudClientServiceImpl implements CrudClientService {
    private final List<Client> clients = new ArrayList<>(); // Temporal, reemplazar por repositorio
    private final CrudPersonTypeService personTypeService;
    private final CrudDocumentTypeService documentTypeService;

    public CrudClientServiceImpl(CrudPersonTypeService personTypeService, CrudDocumentTypeService documentTypeService) {
        this.personTypeService = personTypeService;
        this.documentTypeService = documentTypeService;
    }

    @Override
    public Client createClient(Client client) {
        clients.add(client);
        return client;
    }

    @Override
    public Client getClientById(Long id) {
        return clients.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        return clients;
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client existing = getClientById(id);
        if (existing != null) {
            clients.remove(existing);
            clients.add(client);
            return client;
        }
        return null;
    }

    @Override
    public void deleteClient(Long id) {
        clients.removeIf(c -> c.getId().equals(id));
    }
}
