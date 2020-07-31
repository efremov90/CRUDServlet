package org.crudservlet.dto;

public class GetClientsRequestDTO {

    private String ClientType;

    public GetClientsRequestDTO() {
    }

    public GetClientsRequestDTO(String clientType) {
        ClientType = clientType;
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    @Override
    public String toString() {
        return "GetClientRequestDTO{" +
                "ClientType='" + ClientType + '\'' +
                '}';
    }
}
