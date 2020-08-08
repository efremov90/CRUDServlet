package org.crudservlet.dto;

public class AuditDTO {

    private int Id;
    private String Event;
    private String User;
    private String EventDateTime;
    private String Description;
    private String Content;

    public AuditDTO() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getEventDateTime() {
        return EventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
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
