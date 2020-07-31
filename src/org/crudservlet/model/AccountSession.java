package org.crudservlet.model;

import java.util.Date;

public class AccountSession {
    private int Id;
    private String SessionId;
    private Date CreateDateTime;
    private Date LastEventDateTime;
    private int UserAccountId;

    public AccountSession() {
    }

    public AccountSession(int id, String sessionId, Date createDateTime, Date lastEventDateTime, int userAccountId) {
        Id = id;
        SessionId = sessionId;
        CreateDateTime = createDateTime;
        LastEventDateTime = lastEventDateTime;
        UserAccountId = userAccountId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public Date getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        CreateDateTime = createDateTime;
    }

    public Date getLastEventDateTime() {
        return LastEventDateTime;
    }

    public void setLastEventDateTime(Date lastEventDateTime) {
        LastEventDateTime = lastEventDateTime;
    }

    public int getUserAccountId() {
        return UserAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        UserAccountId = userAccountId;
    }

    @Override
    public String toString() {
        return "AccountSession{" +
                "Id=" + Id +
                ", SessionId='" + SessionId + '\'' +
                ", CreateDateTime=" + CreateDateTime +
                ", LastEventDateTime=" + LastEventDateTime +
                ", UserAccountId=" + UserAccountId +
                '}';
    }
}
