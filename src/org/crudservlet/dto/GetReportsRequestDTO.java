package org.crudservlet.dto;

public class GetReportsRequestDTO {

    private String FromRunDate;
    private String ToRunDate;

    public GetReportsRequestDTO() {
    }

    public String getFromRunDate() {
        return FromRunDate;
    }

    public void setFromRunDate(String fromRunDate) {
        FromRunDate = fromRunDate;
    }

    public String getToRunDate() {
        return ToRunDate;
    }

    public void setToRunDate(String toRunDate) {
        ToRunDate = toRunDate;
    }
}
