package org.crudservlet.dto;

import java.sql.Blob;

public class GetReportResponseDTO {

    Blob Report;

    public GetReportResponseDTO() {
    }

    public Blob getReport() {
        return Report;
    }

    public void setReport(Blob report) {
        Report = report;
    }
}
