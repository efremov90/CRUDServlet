package org.crudservlet.dto;

public class RequestStatusHistoryDTO {

    private int Id;
    private String Status;
    private String StatusDescription;
    private String User;
    private String EventDateTime;
    private String Comment;

    public RequestStatusHistoryDTO() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
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

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
