package org.crudservlet.dto;

public class GetRequestStatusHistoryRequestDTO {

    private int RequestId;

    public GetRequestStatusHistoryRequestDTO() {
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }
}
