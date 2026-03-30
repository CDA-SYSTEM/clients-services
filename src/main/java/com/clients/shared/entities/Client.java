package com.clients.shared.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Client {
        private boolean active = true;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 2, max = 50)
    private String nombre;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 2, max = 50)
    private String apellido;

    private LocalDate birthDate;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 5, max = 20)
    private String identity;

    @jakarta.validation.constraints.Size(max = 100)
    private String direccion;

    @jakarta.validation.constraints.Size(min = 7, max = 15)
    private String celular;

    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.Size(max = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @ManyToOne
    @JoinColumn(name = "person_type_id")
    private PersonType personType;

    // Getters and setters
    public Long getId() { return id; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getIdentity() { return identity; }
    public void setIdentity(String identity) { this.identity = identity; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
    public PersonType getPersonType() { return personType; }
    public void setPersonType(PersonType personType) { this.personType = personType; }
}
