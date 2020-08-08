package org.crudservlet.dto;

public class GetRequestsRequestDTO {

    private String FromCreateDate;
    private String ToCreateDate;

    public GetRequestsRequestDTO() {
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
