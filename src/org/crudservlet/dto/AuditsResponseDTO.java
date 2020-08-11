package org.crudservlet.dto;

import java.util.ArrayList;

public class AuditsResponseDTO {

    private ArrayList<AuditDTO> Items;

    public AuditsResponseDTO() {
    }

    public AuditsResponseDTO(ArrayList<AuditDTO> items) {
        Items = items;
    }

    public ArrayList<AuditDTO> getItems() {
        return Items;
    }

    public void setItems(ArrayList<AuditDTO> items) {
        Items = items;
    }
}
