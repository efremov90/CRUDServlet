package org.crudservlet.dto;

import java.util.ArrayList;

public class GetReportsResponseDTO {

    private ArrayList<ReportDTO> Items;

    public GetReportsResponseDTO() {
    }

    public GetReportsResponseDTO(ArrayList<ReportDTO> items) {
        Items = items;
    }

    public ArrayList<ReportDTO> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ReportDTO> items) {
        Items = items;
    }
}
