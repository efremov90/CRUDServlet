package org.crudservlet.dto;

public class GenerateReportRequestDTO {

    private ReportRequestsDetailedDTO ReportRequestsDetailed;
    private ReportRequestsConsolidatedDTO ReportRequestsConsolidated;

    public GenerateReportRequestDTO() {
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
