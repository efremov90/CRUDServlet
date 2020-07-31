package org.crudservlet.dto;

public class ErrorDTO {
    String ErrorCode;
    String ErrorMessage;
    String StackTrace;

    public ErrorDTO() {
    }

    public ErrorDTO(String errorCode, String errorMessage, String stackTrace) {
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
        StackTrace = stackTrace;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getStackTrace() {
        return StackTrace;
    }

    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }
}
