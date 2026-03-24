package com.clients.clients.dto;

public class ClientSearchFilterDTO {
    private String search;
        public String getSearch() { return search; }
        public void setSearch(String search) { this.search = search; }
    // Eliminados: identity, nombre, apellido, celular, email. Usar solo search.
    private String identity;
    private String nombre;
    private String apellido;
    private String direccion;
    private String celular;
    private String email;
    private Long documentTypeId;
    private Long personTypeId;
    private Integer page;
    private Integer size;

    // Eliminados getters/setters de identity, nombre, apellido, celular, email.
    // public String getIdentity() { return identity; }
    // public void setIdentity(String identity) { this.identity = identity; }
    // public String getNombre() { return nombre; }
    // public void setNombre(String nombre) { this.nombre = nombre; }
    // public String getApellido() { return apellido; }
    // public void setApellido(String apellido) { this.apellido = apellido; }
    // public String getCelular() { return celular; }
    // public void setCelular(String celular) { this.celular = celular; }
    // public String getEmail() { return email; }
    // public void setEmail(String email) { this.email = email; }
    public void setCelular(String celular) { this.celular = celular; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Long getDocumentTypeId() { return documentTypeId; }
    public void setDocumentTypeId(Long documentTypeId) { this.documentTypeId = documentTypeId; }
    public Long getPersonTypeId() { return personTypeId; }
    public void setPersonTypeId(Long personTypeId) { this.personTypeId = personTypeId; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}
