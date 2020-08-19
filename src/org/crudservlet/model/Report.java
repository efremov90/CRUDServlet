package org.crudservlet.model;

import java.sql.Blob;
import java.util.Date;

public class Report {

    private int Id;
    private ReportType Type;
    private Date StartDateTime;
    private Date FinishDateTime;
    private ReportStatusType Status;
    private String Comment;
    private Blob Content;
    private String Parameters;
    private int UserAccountId;

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
}
