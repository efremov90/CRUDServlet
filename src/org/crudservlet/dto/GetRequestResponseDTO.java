package org.crudservlet.dto;

public class GetRequestResponseDTO {

    private RequestDTO Request;

    public GetRequestResponseDTO() {
    }

    public RequestDTO getRequest() {
        return Request;
    }

    public void setRequest(RequestDTO request) {
        Request = request;
    }
}
