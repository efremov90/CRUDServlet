package org.crudservlet.model;

import java.util.Date;

public class Audit {
    private int Id;
    private int AuditOperId;
    private AuditOperType AuditOperType;
    private int UserAccountId;
    private String UserName;
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

    public int getAuditOperId() {
        return AuditOperId;
    }

    public void setAuditOperId(int auditOperId) {
        AuditOperId = auditOperId;
    }

    public org.crudservlet.model.AuditOperType getAuditOperType() {
        return AuditOperType;
    }

    public void setAuditOperType(org.crudservlet.model.AuditOperType auditOperType) {
        AuditOperType = auditOperType;
    }

    public int getUserAccountId() {
        return UserAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        UserAccountId = userAccountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
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
