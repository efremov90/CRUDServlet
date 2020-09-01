package org.crudservlet.model;

import java.sql.Blob;
import java.util.Date;

public class Task {

    private int Id;
    private TaskType Type;
    private Date CreateDate;
    private Date CreateDateTime;
    private Date PlannedStartDateTime;
    private Date StartDateTime;
    private Date FinishDateTime;
    private TaskStatusType Status;
    private String Comment;
    private int UserAccountId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public TaskType getType() {
        return Type;
    }

    public void setType(TaskType type) {
        Type = type;
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

    public Date getPlannedStartDateTime() {
        return PlannedStartDateTime;
    }

    public void setPlannedStartDateTime(Date plannedStartDateTime) {
        PlannedStartDateTime = plannedStartDateTime;
    }

    public Date getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        StartDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return FinishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        FinishDateTime = finishDateTime;
    }

    public TaskStatusType getStatus() {
        return Status;
    }

    public void setStatus(TaskStatusType status) {
        Status = status;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getUserAccountId() {
        return UserAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        UserAccountId = userAccountId;
    }
}
