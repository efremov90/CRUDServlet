package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Task;

import java.sql.*;
import java.util.logging.Logger;

import static org.crudservlet.model.TaskStatusType.CREATED;

public class TaskDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(TaskDAO.class.getName());

    public TaskDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(Task task) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "INSERT TASKS (TYPE, CREATE_DATE, CREATE_DATETIME, PLANNED_START_DATETIME, START_DATETIME, " +
                "FINISH_DATETIME, " +
                "STATUS, COMMENT, USER_ACCOUNT_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?); ";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, task.getType().name());
        st.setString(2, new Date(task.getCreateDateTime().getTime()).toString());
        st.setString(3, new Timestamp(task.getCreateDateTime().getTime()).toString());
        st.setString(4, task.getPlannedStartDateTime() != null ?
                new Timestamp(task.getPlannedStartDateTime().getTime()).toString() : null);
        st.setString(5, null);
        st.setString(6, null);
        st.setString(7, CREATED.name());
        st.setString(8, null);
        st.setInt(9, task.getUserAccountId());
        st.executeUpdate();

        result = MySQLConnection.getLastInsertId();
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
