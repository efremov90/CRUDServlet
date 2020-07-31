package org.crudservlet.model;

import java.util.Date;

import static org.crudservlet.model.ClientTypeType.DOPOFFICE;

public class ClientDopoffice {
    private int Id;
    private String ClientCode;
    private String ClientName;
    final private static ClientTypeType ClientType = DOPOFFICE;
    private String Address;
    private Date CloseDate;

    public ClientDopoffice() {
    }

    public ClientDopoffice(int id, String clientCode, String clientName, String address, Date closeDate) {
        Id = id;
        ClientCode = clientCode;
        ClientName = clientName;
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

    public static ClientTypeType getClientType() {
        return ClientType;
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

    @Override
    public String toString() {
        return "ClientDopoffice{" +
                "Id=" + Id +
                ", ClientCode='" + ClientCode + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", Address='" + Address + '\'' +
                ", CloseDate=" + CloseDate +
                '}';
    }
}
