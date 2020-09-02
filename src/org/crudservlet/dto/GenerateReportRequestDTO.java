package org.crudservlet.dto;

public class GenerateReportRequestDTO {

    private String FormatType;
    private ReportRequestsDetailedDTO ReportRequestsDetailed;
    private ReportRequestsConsolidatedDTO ReportRequestsConsolidated;

    public GenerateReportRequestDTO() {
    }

    public String getFormatType() {
        return FormatType;
    }

    public void setFormatType(String formatType) {
        FormatType = formatType;
    }

    public ReportRequestsDetailedDTO getReportRequestsDetailed() {
        return ReportRequestsDetailed;
    }

    public void setReportRequestsDetailed(ReportRequestsDetailedDTO reportRequestsDetailed) {
        ReportRequestsDetailed = reportRequestsDetailed;
    }

    public ReportRequestsConsolidatedDTO getReportRequestsConsolidated() {
        return ReportRequestsConsolidated;
    }

    public void setReportRequestsConsolidated(ReportRequestsConsolidatedDTO reportRequestsConsolidated) {
        ReportRequestsConsolidated = reportRequestsConsolidated;
    }
}
