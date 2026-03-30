package com.clients.clients.dto;

import java.time.LocalDate;

public class ClientRequestDTO {
    private String nombre;
    private String apellido;
    private LocalDate birthDate;
    private String identity;
    private String direccion;
    private String celular;
    private String email;
    private Long documentTypeId;
    private Long personTypeId;

    // Getters and setters
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
    public Long getDocumentTypeId() { return documentTypeId; }
    public void setDocumentTypeId(Long documentTypeId) { this.documentTypeId = documentTypeId; }
    public Long getPersonTypeId() { return personTypeId; }
    public void setPersonTypeId(Long personTypeId) { this.personTypeId = personTypeId; }
}
