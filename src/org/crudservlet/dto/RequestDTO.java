package org.crudservlet.dto;

public class RequestDTO {

    private int RequestId;
    private String RequestUUID;
    private String CreateDate;
    private String CreateDatetime;
    private String ClientCode;
    private String ClientName;
    private String ClientType;
    private String ClientTypeDescription;
    private String Comment;
    private String RequestStatus;
    private String RequestStatusDescription;
    private String CommentRequestStatus;
    private String LastDateTimeChangeRequestStatus;
    private int LastUserAccountIdChangeRequestStatus;
    private String LastUserNameChangeRequestStatus;

    public RequestDTO() {
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public String getRequestUUID() {
        return RequestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        RequestUUID = requestUUID;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateDatetime() {
        return CreateDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        CreateDatetime = createDatetime;
    }

    public String getClientCode() {
        return ClientCode;
    }

    public void setClientCode(String clientCode) {
        ClientCode = clientCode;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    public String getClientTypeDescription() {
        return ClientTypeDescription;
    }

    public void setClientTypeDescription(String clientTypeDescription) {
        ClientTypeDescription = clientTypeDescription;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getRequestStatusDescription() {
        return RequestStatusDescription;
    }

    public void setRequestStatusDescription(String requestStatusDescription) {
        RequestStatusDescription = requestStatusDescription;
    }

    public String getCommentRequestStatus() {
        return CommentRequestStatus;
    }

    public void setCommentRequestStatus(String commentRequestStatus) {
        CommentRequestStatus = commentRequestStatus;
    }

    public String getLastDateTimeChangeRequestStatus() {
        return LastDateTimeChangeRequestStatus;
    }

    public void setLastDateTimeChangeRequestStatus(String lastDateTimeChangeRequestStatus) {
        LastDateTimeChangeRequestStatus = lastDateTimeChangeRequestStatus;
    }

    public int getLastUserAccountIdChangeRequestStatus() {
        return LastUserAccountIdChangeRequestStatus;
    }

    public void setLastUserAccountIdChangeRequestStatus(int lastUserAccountIdChangeRequestStatus) {
        LastUserAccountIdChangeRequestStatus = lastUserAccountIdChangeRequestStatus;
    }

    public String getLastUserNameChangeRequestStatus() {
        return LastUserNameChangeRequestStatus;
    }

    public void setLastUserNameChangeRequestStatus(String lastUserNameChangeRequestStatus) {
        LastUserNameChangeRequestStatus = lastUserNameChangeRequestStatus;
    }
}
