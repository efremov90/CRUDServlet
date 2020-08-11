package org.crudservlet.model;

import java.util.Date;

public class Request {
    private int Id;
    private String RequestUUID;
    private Date CreateDate;
    private Date CreateDateTime;
    private String ClientCode;
    private String Comment;
    private RequestStatusType RequestStatus;
    private String CommentRequestStatus;
    private Date LastDateTimeChangeRequestStatus;
    private int LastUserAccountIdChangeRequestStatus;

    public Request() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRequestUUID() {
        return RequestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        RequestUUID = requestUUID;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public Date getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        CreateDateTime = createDateTime;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public RequestStatusType getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(RequestStatusType requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getCommentRequestStatus() {
        return CommentRequestStatus;
    }

    public void setCommentRequestStatus(String commentRequestStatus) {
        CommentRequestStatus = commentRequestStatus;
    }

    public Date getLastDateTimeChangeRequestStatus() {
        return LastDateTimeChangeRequestStatus;
    }

    public void setLastDateTimeChangeRequestStatus(Date lastDateTimeChangeRequestStatus) {
        LastDateTimeChangeRequestStatus = lastDateTimeChangeRequestStatus;
    }

    public int getLastUserAccountIdChangeRequestStatus() {
        return LastUserAccountIdChangeRequestStatus;
    }

    public void setLastUserAccountIdChangeRequestStatus(int lastUserAccountIdChangeRequestStatus) {
        LastUserAccountIdChangeRequestStatus = lastUserAccountIdChangeRequestStatus;
    }
}
