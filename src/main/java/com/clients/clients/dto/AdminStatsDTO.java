package com.clients.clients.dto;

import java.util.List;
import java.util.Map;

public class AdminStatsDTO {
    private long totalClients;
    private long activeClients;
    private long inactiveClients;
    private List<Map<String, Object>> clientsByDocumentType;
    private List<Map<String, Object>> clientsByPersonType;

    public long getTotalClients() { return totalClients; }
    public void setTotalClients(long totalClients) { this.totalClients = totalClients; }
    public long getActiveClients() { return activeClients; }
    public void setActiveClients(long activeClients) { this.activeClients = activeClients; }
    public long getInactiveClients() { return inactiveClients; }
    public void setInactiveClients(long inactiveClients) { this.inactiveClients = inactiveClients; }
    public List<Map<String, Object>> getClientsByDocumentType() { return clientsByDocumentType; }
    public void setClientsByDocumentType(List<Map<String, Object>> clientsByDocumentType) { this.clientsByDocumentType = clientsByDocumentType; }
    public List<Map<String, Object>> getClientsByPersonType() { return clientsByPersonType; }
    public void setClientsByPersonType(List<Map<String, Object>> clientsByPersonType) { this.clientsByPersonType = clientsByPersonType; }
}
