package org.crudservlet.dto;

import java.util.ArrayList;

public class GetRequestsResponseDTO {

    private ArrayList<RequestDTO> Items;

    public GetRequestsResponseDTO() {
    }

    public GetRequestsResponseDTO(ArrayList<RequestDTO> items) {
        Items = items;
    }

    public ArrayList<RequestDTO> getItems() {
        return Items;
    }

    public void setItems(ArrayList<RequestDTO> items) {
        Items = items;
    }
}
