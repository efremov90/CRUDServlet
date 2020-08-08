package org.crudservlet.dto;

import java.util.ArrayList;

public class AuditsResponseDTO {

    private ArrayList<AuditDTO> Audits;

    public AuditsResponseDTO() {
    }

    public ArrayList<AuditDTO> getAudits() {
        return Audits;
    }

    public void setAudits(ArrayList<AuditDTO> audits) {
        Audits = audits;
    }
}
