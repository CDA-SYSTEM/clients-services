package com.clients.shared.dto;

public class NotFoundErrorDTO {
    private String message;
    private String resource;
    private Long resourceId;

    public NotFoundErrorDTO(String message, String resource, Long resourceId) {
        this.message = message;
        this.resource = resource;
        this.resourceId = resourceId;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
}
