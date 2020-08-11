package org.crudservlet.dto;

public class ClientDTO {

    private int Id;
    private String ClientCode;
    private String ClientName;
    private String ClientType;
    private String ClientTypeDescription;
    private String AtmType;
    private String AtmTypeDescription;
    private String Address;
    private String CloseDate;

    public ClientDTO() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    public String getClientTypeDescription() {
        return ClientTypeDescription;
    }

    public void setClientTypeDescription(String clientTypeDescription) {
        ClientTypeDescription = clientTypeDescription;
    }

    public String getAtmType() {
        return AtmType;
    }

    public void setAtmType(String atmType) {
        AtmType = atmType;
    }

    public String getAtmTypeDescription() {
        return AtmTypeDescription;
    }

    public void setAtmTypeDescription(String atmTypeDescription) {
        AtmTypeDescription = atmTypeDescription;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCloseDate() {
        return CloseDate;
    }

    public void setCloseDate(String closeDate) {
        CloseDate = closeDate;
    }

}
