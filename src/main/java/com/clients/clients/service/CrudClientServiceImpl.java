package com.clients.clients.service;

import com.clients.shared.entities.Client;
import com.clients.shared.entities.PersonType;
import com.clients.shared.entities.DocumentType;
import com.clients.shared.dto.NotFoundErrorDTO;
import com.clients.clients.repository.ClientRepository;
import com.clients.clients.personType.CrudPersonTypeService;
import com.clients.clients.documentType.CrudDocumentTypeService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrudClientServiceImpl implements CrudClientService {
    private final ClientRepository clientRepository;
    private final CrudPersonTypeService personTypeService;
    private final CrudDocumentTypeService documentTypeService;

    public CrudClientServiceImpl(ClientRepository clientRepository, CrudPersonTypeService personTypeService, CrudDocumentTypeService documentTypeService) {
        this.clientRepository = clientRepository;
        this.personTypeService = personTypeService;
        this.documentTypeService = documentTypeService;
    }

    @Override
    public Client createClient(Client client) {
        // Validar identidad única
        Client existing = clientRepository.findByIdentity(client.getIdentity());
        if (existing != null) {
            throw new IllegalArgumentException("Ya existe un cliente con la misma identidad: " + client.getIdentity());
        }
        return clientRepository.save(client);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAllByActiveTrue();
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client existing = getClientById(id);
        if (existing != null) {
            client.setId(id);
            return clientRepository.save(client);
        }
        return null;
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null && client.isActive()) {
            client.setActive(false);
            clientRepository.save(client);
        }
    }
    @Override
    public void setClientActiveStatus(Long id, boolean active) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        client.setActive(active);
        clientRepository.save(client);
    }
}
