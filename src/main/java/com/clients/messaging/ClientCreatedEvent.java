package com.clients.messaging;

public class ClientCreatedEvent {

    private String id;
    private String nombres;
    private String documento;

    public ClientCreatedEvent() {
    }

    public ClientCreatedEvent(String id, String nombres, String documento) {
        this.id = id;
        this.nombres = nombres;
        this.documento = documento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
