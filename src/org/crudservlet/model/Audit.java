package org.crudservlet.model;

import java.util.Date;

public class Audit {
    private int Id;
    private String AuditOperId;
    private int UserAccountId;
    private Date EventDateTime;
    private String Description;
    private String Content;

    public Audit() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAuditOperId() {
        return AuditOperId;
    }

    public void setAuditOperId(String auditOperId) {
        AuditOperId = auditOperId;
    }

    public int getUserAccountId() {
        return UserAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        UserAccountId = userAccountId;
    }

    public Date getEventDateTime() {
        return EventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        EventDateTime = eventDateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
