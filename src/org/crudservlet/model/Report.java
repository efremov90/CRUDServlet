package org.crudservlet.model;

import java.sql.Blob;
import java.util.Date;

public class Report {

    private int Id;
    private ReportType Type;
    private Date CreateDate;
    private Date CreateDatetime;
    private Date StartDateTime;
    private Date FinishDateTime;
    private ReportStatusType Status;
    private String Comment;
    private Blob Content;
    private String Parameters;
    private int UserAccountId;
    private int TaskId;
    private Date FromPeriodDate;
    private Date ToPeriodDate;
    private FormatReportType Format;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public ReportType getType() {
        return Type;
    }

    public void setType(ReportType type) {
        Type = type;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public Date getCreateDatetime() {
        return CreateDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        CreateDatetime = createDatetime;
    }

    public Date getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        StartDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return FinishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        FinishDateTime = finishDateTime;
    }

    public ReportStatusType getStatus() {
        return Status;
    }

    public void setStatus(ReportStatusType status) {
        Status = status;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Blob getContent() {
        return Content;
    }

    public void setContent(Blob content) {
        Content = content;
    }

    public String getParameters() {
        return Parameters;
    }

    public void setParameters(String parameters) {
        Parameters = parameters;
    }

    public int getUserAccountId() {
        return UserAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        UserAccountId = userAccountId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }

    public Date getFromPeriodDate() {
        return FromPeriodDate;
    }

    public void setFromPeriodDate(Date fromPeriodDate) {
        FromPeriodDate = fromPeriodDate;
    }

    public Date getToPeriodDate() {
        return ToPeriodDate;
    }

    public void setToPeriodDate(Date toPeriodDate) {
        ToPeriodDate = toPeriodDate;
    }

    public FormatReportType getFormat() {
        return Format;
    }

    public void setFormat(FormatReportType format) {
        Format = format;
    }
}
