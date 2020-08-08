package org.crudservlet.dto;

import java.util.ArrayList;

public class GetRequestStatusHistoryResponseDTO {

    private ArrayList<RequestStatusHistoryDTO> RequestStatusHistory;

    public GetRequestStatusHistoryResponseDTO() {
    }

    public ArrayList<RequestStatusHistoryDTO> getRequestStatusHistory() {
        return RequestStatusHistory;
    }

    public void setRequestStatusHistory(ArrayList<RequestStatusHistoryDTO> requestStatusHistory) {
        RequestStatusHistory = requestStatusHistory;
    }
}
