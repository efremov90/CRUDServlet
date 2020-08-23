package org.crudservlet.dto;

public class GetReportRequestDTO {

    int ReportId;
    String Format;

    public GetReportRequestDTO() {
    }

    public int getReportId() {
        return ReportId;
    }

    public void setReportId(int reportId) {
        ReportId = reportId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
