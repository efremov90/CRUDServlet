package org.crudservlet.dto;

public class ClientDTO {

    private int Id;
    private String ClientCode;
    private String ClientName;
    private String ClientType;
    private String ClientTypeDescription;
    private String Address;
    private String CloseDate;
    private String ATMType;
    private String ATMTypeDescription;

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

    public String getATMType() {
        return ATMType;
    }

    public void setATMType(String ATMType) {
        this.ATMType = ATMType;
    }

    public String getATMTypeDescription() {
        return ATMTypeDescription;
    }

    public void setATMTypeDescription(String ATMTypeDescription) {
        this.ATMTypeDescription = ATMTypeDescription;
    }
}
