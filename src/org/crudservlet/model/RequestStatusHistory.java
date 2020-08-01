package org.crudservlet.model;

import java.util.Date;

public class RequestStatusHistory {

    private int Id;
    private int RequestId;
    private RequestStatusType Status;
    private String Comment;
    private boolean IsLastStatus;
    private Date EventDateTime;
    private int UserId;

    public RequestStatusHistory() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public RequestStatusType getStatus() {
        return Status;
    }

    public void setStatus(RequestStatusType status) {
        Status = status;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public boolean isLastStatus() {
        return IsLastStatus;
    }

    public void setLastStatus(boolean lastStatus) {
        IsLastStatus = lastStatus;
    }

    public Date getEventDateTime() {
        return EventDateTime;
    }

    public void setEventDateTime(Date eventDateTime) {
        EventDateTime = eventDateTime;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
