package org.crudservlet.dto;

public class CreateRequestRequestDTO {

    private RequestDTO Request;

    public CreateRequestRequestDTO() {
    }

    public RequestDTO getRequest() {
        return Request;
    }

    public void setRequest(RequestDTO request) {
        Request = request;
    }
}
