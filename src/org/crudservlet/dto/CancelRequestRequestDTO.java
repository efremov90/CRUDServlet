package org.crudservlet.dto;

public class CancelRequestRequestDTO {

    private int RequestId;
    private String Comment;

    public CancelRequestRequestDTO() {
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
