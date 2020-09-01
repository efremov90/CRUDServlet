package org.crudservlet.dto;

public class ReportDTO {

    private int ReportId;
    private String RunDate;
    private String RunDatetime;
    private String ReportName;
    private String FromPeriodDate;
    private String ToPeriodDate;
    private String Parameters;
    private String Status;

    public ReportDTO() {
    }

    public int getReportId() {
        return ReportId;
    }

    public void setReportId(int reportId) {
        ReportId = reportId;
    }

    public String getRunDate() {
        return RunDate;
    }

    public void setRunDate(String runDate) {
        RunDate = runDate;
    }

    public String getRunDatetime() {
        return RunDatetime;
    }

    public void setRunDatetime(String runDatetime) {
        RunDatetime = runDatetime;
    }

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String reportName) {
        ReportName = reportName;
    }

    public String getFromPeriodDate() {
        return FromPeriodDate;
    }

    public void setFromPeriodDate(String fromPeriodDate) {
        FromPeriodDate = fromPeriodDate;
    }

    public String getToPeriodDate() {
        return ToPeriodDate;
    }

    public void setToPeriodDate(String toPeriodDate) {
        ToPeriodDate = toPeriodDate;
    }

    public String getParameters() {
        return Parameters;
    }

    public void setParameters(String parameters) {
        Parameters = parameters;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
