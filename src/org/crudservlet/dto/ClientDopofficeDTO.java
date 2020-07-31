package org.crudservlet.dto;

import java.util.Date;

public class ClientDopofficeDTO {

    private int Id;
    private String ClientCode;
    private String ClientName;
    private String Address;
    private String CloseDate;

    public ClientDopofficeDTO() {
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

    @Override
    public String toString() {
        return "ClientDopofficeDTO{" +
                "Id=" + Id +
                ", ClientCode='" + ClientCode + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", Address='" + Address + '\'' +
                ", CloseDate=" + CloseDate +
                '}';
    }
}
