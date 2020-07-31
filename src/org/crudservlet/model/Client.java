package org.crudservlet.model;

import java.util.Date;

import static org.crudservlet.model.ClientTypeType.SELFSERVICE;

public class Client {
    private int Id;
    private String ClientCode;
    private String ClientName;
    private ClientTypeType clientType;
    private ATMTypeType atmType;
    private String Address;
    private Date CloseDate;

    public Client() {
    }

    public Client(int id, String clientCode, String clientName, ClientTypeType clientType, ATMTypeType atmType, String address, Date closeDate) {
        Id = id;
        ClientCode = clientCode;
        ClientName = clientName;
        this.clientType = clientType;
        this.atmType = atmType;
        Address = address;
        CloseDate = closeDate;
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

    public ClientTypeType getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeType clientType) {
        this.clientType = clientType;
    }

    public ATMTypeType getAtmType() {
        return atmType;
    }

    public void setAtmType(ATMTypeType atmType) {
        this.atmType = atmType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Date getCloseDate() {
        return CloseDate;
    }

    public void setCloseDate(Date closeDate) {
        CloseDate = closeDate;
    }
}
