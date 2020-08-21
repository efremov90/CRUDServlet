package org.crudservlet.model;

import java.util.EnumMap;

public class TaskStatusToReportStatus {

    private EnumMap<TaskStatusType, ReportStatusType> taskToReport;

    public TaskStatusToReportStatus() {
        taskToReport.put(TaskStatusType.CREATED, ReportStatusType.CREATED);
        taskToReport.put(TaskStatusType.STARTED, ReportStatusType.STARTED);
        taskToReport.put(TaskStatusType.FINISH, ReportStatusType.FINISH);
    }

    public ReportStatusType getStatus(TaskStatusType taskStatusType) {
        return taskToReport.get(taskStatusType);
    }
}
