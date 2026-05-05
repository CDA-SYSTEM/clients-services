package com.clients.clients.service;

import com.clients.shared.entities.Client;
import java.util.List;
import java.util.Optional;

public interface CrudClientService {
    Client createClient(Client client);
    Client getClientById(Long id);
    List<Client> getAllClients();
    Client updateClient(Long id, Client client);
    void deleteClient(Long id);
    void setClientActiveStatus(Long id, boolean active);
    Optional<Client> getActiveClientById(Long id);
}
