package org.crudservlet.service;

import org.crudservlet.dao.TaskDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.*;
import java.util.Date;
import java.util.logging.Logger;

import static org.crudservlet.model.TaskStatusType.*;

public class TaskService {

    private Connection conn;
    private Logger logger = Logger.getLogger(TaskService.class.getName());

    public TaskService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(Task task, int userAccountId, int entityId) throws Exception {
        logger.info("start");

        Integer result = null;

        task.setStatus(CREATED);
        task.setUserAccountId(userAccountId);

        Integer taskId = new TaskDAO().create(task);

        result = taskId;

        new AuditService().create(
                AuditOperType.CREATE_TASK,
                userAccountId,
                task.getCreateDateTime(),
                String.format("Тип задания: %s \n" +
                                (task.getType().equals(TaskType.REPORT) ? "Id отчета" : "") + ": %s \n",
                        task.getType().getDescription(),
                        entityId),
                taskId
        );

        return result;
    }

    public boolean start(int taskId) throws Exception {
        logger.info("start");

        boolean result = false;

        String sql = "UPDATE TASKS " +
                "SET STATUS = ? " +
                "WHERE ID = ?";

//        conn.setAutoCommit(false);

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, STARTED.name());
        st.setInt(2, taskId);
        result = st.executeUpdate() > 0;

        new AuditService().create(
                AuditOperType.START_TASK,
                -1,
                new Date(),
                "",
                taskId
        );

//        conn.commit();
//        conn.setAutoCommit(true);

        logger.info(":" + result);

        return result;
    }

    public boolean finish(int taskId) throws Exception {
        logger.info("start");

        boolean result = false;

        String sql = "UPDATE TASKS " +
                "SET FINISH_DATETIME = ?, " +
                "STATUS = ? " +
                "WHERE ID = ?";

//        conn.setAutoCommit(false);

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, new Timestamp(new java.util.Date().getTime()).toString());
        st.setString(2, FINISH.name());
        st.setInt(3, taskId);
        result = st.executeUpdate() > 0;

        new AuditService().create(
                AuditOperType.FINISH_TASK,
                -1,
                new Date(),
                "",
                taskId
        );

//        conn.commit();
//        conn.setAutoCommit(true);

        return result;
    }

    public TaskStatusType getTaskStatus(int taskId) throws SQLException {
        logger.info("start");

        TaskStatusType taskStatusType = null;

        String sql = "SELECT STATUS  " +
                "FROM REPORTS r " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, taskId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            taskStatusType = TaskStatusType.valueOf(rs.getString("STATUS"));
        }

        return taskStatusType;
    }
}
