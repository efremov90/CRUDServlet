package org.crudservlet.dto;

public class ReportRequestsDetailedDTO {

    private String FromCreateDate;
    private String ToCreateDate;
    private String ClientCode;

    public ReportRequestsDetailedDTO() {
    }

    public String getFromCreateDate() {
        return FromCreateDate;
    }

    public void setFromCreateDate(String fromCreateDate) {
        FromCreateDate = fromCreateDate;
    }

    public String getToCreateDate() {
        return ToCreateDate;
    }

    public void setToCreateDate(String toCreateDate) {
        ToCreateDate = toCreateDate;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }
}
