package com.clients.clients.service;

import com.clients.clients.dto.AdminStatsDTO;
import com.clients.clients.repository.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class AdminStatsService {

    private final ClientRepository clientRepository;

    public AdminStatsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public AdminStatsDTO getStats() {
        CompletableFuture<Long> totalFuture = CompletableFuture.supplyAsync(() ->
                clientRepository.count());
        CompletableFuture<Long> activeFuture = CompletableFuture.supplyAsync(() ->
                clientRepository.countByActiveTrue());
        CompletableFuture<Long> inactiveFuture = CompletableFuture.supplyAsync(() ->
                clientRepository.countByActiveFalse());
        CompletableFuture<List<Map<String, Object>>> byDocTypeFuture = CompletableFuture.supplyAsync(() -> {
            List<Object[]> results = clientRepository.countByDocumentType();
            return results.stream().map(r -> Map.of("label", r[0] != null ? r[0] : "Sin tipo", "count", r[1])).toList();
        });
        CompletableFuture<List<Map<String, Object>>> byPersonTypeFuture = CompletableFuture.supplyAsync(() -> {
            List<Object[]> results = clientRepository.countByPersonType();
            return results.stream().map(r -> Map.of("label", r[0] != null ? r[0] : "Sin tipo", "count", r[1])).toList();
        });

        AdminStatsDTO stats = new AdminStatsDTO();
        stats.setTotalClients(totalFuture.join());
        stats.setActiveClients(activeFuture.join());
        stats.setInactiveClients(inactiveFuture.join());
        stats.setClientsByDocumentType(byDocTypeFuture.join());
        stats.setClientsByPersonType(byPersonTypeFuture.join());

        return stats;
    }
}
