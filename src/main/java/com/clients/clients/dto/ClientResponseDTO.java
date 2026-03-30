package com.clients.clients.dto;

import java.time.LocalDate;

public class ClientResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate birthDate;
    private String identity;
    private String direccion;
    private String celular;
    private String email;
    private DocumentTypeDTO documentType;
    private PersonTypeDTO personType;

    // Inner DTOs for relations
    public static class DocumentTypeDTO {
        private Long id;
        private String type;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
    public static class PersonTypeDTO {
        private Long id;
        private String type;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    // Getters and setters
    public Long getId() { return id; }
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
    public DocumentTypeDTO getDocumentType() { return documentType; }
    public void setDocumentType(DocumentTypeDTO documentType) { this.documentType = documentType; }
    public PersonTypeDTO getPersonType() { return personType; }
    public void setPersonType(PersonTypeDTO personType) { this.personType = personType; }
}
