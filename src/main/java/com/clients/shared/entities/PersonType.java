package com.clients.shared.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PersonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "personType")
    private java.util.List<Client> clients;

    // Getters and setters
    public java.util.List<Client> getClients() { return clients; }
    public void setClients(java.util.List<Client> clients) { this.clients = clients; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
