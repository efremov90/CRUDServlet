package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RequestTasksDAO {
    private Connection conn;
    private Logger logger = Logger.getLogger(RequestTasksDAO.class.getName());

    public RequestTasksDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(int requestId, int taskId) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "INSERT REQUEST_XREF_TASK (REQUEST_ID, TASK_ID) " +
                "VALUES (?, ?);";

        PreparedStatement st = conn.prepareStatement(sql);

        st.setInt(1, requestId);
        st.setInt(2, taskId);
        st.executeUpdate();

        result = MySQLConnection.getLastInsertId();
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public Integer getTaskByRequest(int requestId) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "SELECT TASK_ID " +
                "FROM REQUEST_XREF_TASK r " +
                "WHERE REQUEST_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, requestId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            result = rs.getInt("TASK_ID");
        }
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
