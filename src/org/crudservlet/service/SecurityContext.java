package org.crudservlet.service;

import org.crudservlet.model.SecurityContextStatusCodeType;

public class SecurityContext {
    String Account;
    String Password;
    String SessionId;
    SecurityContextStatusCodeType StatusCode;

    public SecurityContext() {
    }

    public SecurityContext(String account, String password, String sessionId,
                           SecurityContextStatusCodeType statusCode) {
        Account = account;
        Password = password;
        SessionId = sessionId;
        StatusCode = statusCode;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public SecurityContextStatusCodeType getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(SecurityContextStatusCodeType statusCode) {
        StatusCode = statusCode;
    }

    @Override
    public String toString() {
        return "SecurityContext{" +
                "Account='" + Account + '\'' +
                ", Password='" + Password + '\'' +
                ", SessionId='" + SessionId + '\'' +
                ", StatusCode=" + StatusCode +
                '}';
    }
}
