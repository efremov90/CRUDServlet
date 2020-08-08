package org.crudservlet.dto;

public class ResultDTO {
    String Code;
    String Message;

    public ResultDTO() {
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
