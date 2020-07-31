package org.crudservlet.model;

import java.util.Date;

public class Audit {
    private int Id;
    private String AuditOperId;
    private Date EventDateTime;
    private String Content;

    public Audit() {
    }

    public Audit(int id, String auditOperId, Date eventDateTime, String content) {
        Id = id;
        AuditOperId = auditOperId;
        EventDateTime = eventDateTime;
        Content = content;
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

    public Date getEventDateTime() {
        return EventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        EventDateTime = eventDateTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
