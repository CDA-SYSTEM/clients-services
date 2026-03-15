package com.clients.clients.service;

import com.clients.shared.entities.Client;
import java.util.List;

public interface CrudClientService {
    Client createClient(Client client);
    Client getClientById(Long id);
    List<Client> getAllClients();
    Client updateClient(Long id, Client client);
    void deleteClient(Long id);
}
