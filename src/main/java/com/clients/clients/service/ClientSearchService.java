package com.clients.clients.service;

import com.clients.clients.dto.ClientSearchFilterDTO;
import com.clients.clients.dto.ClientListResponseDTO;

public interface ClientSearchService {
    ClientListResponseDTO searchClients(ClientSearchFilterDTO filter);
}
