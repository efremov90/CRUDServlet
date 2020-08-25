package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.ReportType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ReportTasksDAO {
    private Connection conn;
    private Logger logger = Logger.getLogger(ReportTasksDAO.class.getName());

    public ReportTasksDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(int reportId, int taskId) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "INSERT REPORT_XREF_TASK (REPORT_ID, TASK_ID) " +
                "VALUES (?, ?);";

        PreparedStatement st = conn.prepareStatement(sql);

        st.setInt(1, reportId);
        st.setInt(2, taskId);
        st.executeUpdate();

        result = MySQLConnection.getLastInsertId();
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public Integer getTaskByReport(int reportId) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "SELECT TASK_ID " +
                "FROM REPORT_XREF_TASK r " +
                "WHERE REPORT_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, reportId);
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
