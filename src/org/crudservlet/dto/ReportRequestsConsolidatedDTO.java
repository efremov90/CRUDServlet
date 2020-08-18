package org.crudservlet.dto;

public class ReportRequestsConsolidatedDTO {

    private String FromCreateDate;
    private String ToCreateDate;

    public ReportRequestsConsolidatedDTO() {
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
}
