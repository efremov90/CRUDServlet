package org.crudservlet.dto;

import java.util.ArrayList;

public class GetRequestsResponseDTO {

    private ArrayList<RequestDTO> Requests;

    public GetRequestsResponseDTO(ArrayList<RequestDTO> requests) {
        Requests = requests;
    }

    public ArrayList<RequestDTO> getRequests() {
        return Requests;
    }

    public void setRequests(ArrayList<RequestDTO> requests) {
        Requests = requests;
    }
}
