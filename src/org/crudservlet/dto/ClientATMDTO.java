package org.crudservlet.dto;

import java.util.Date;

public class ClientATMDTO {

    private int Id;
    private String ClientCode;
    private String ClientName;
    private String ATMType;
    private String Address;
    private String CloseDate;

    public ClientATMDTO() {
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

    public String getATMType() {
        return ATMType;
    }

    public void setATMType(String ATMType) {
        this.ATMType = ATMType;
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
        return "ClientATMDTO{" +
                "Id=" + Id +
                ", ClientCode='" + ClientCode + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", ATMType='" + ATMType + '\'' +
                ", Address='" + Address + '\'' +
                ", CloseDate=" + CloseDate +
                '}';
    }
}
