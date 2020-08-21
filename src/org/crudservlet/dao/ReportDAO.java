package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Report;
import org.crudservlet.model.ReportStatusType;
import org.crudservlet.model.Request;
import org.h2.command.ddl.CreateDomain;

import java.sql.*;
import java.util.logging.Logger;

import static org.crudservlet.model.ReportStatusType.CREATED;

public class ReportDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(ReportDAO.class.getName());

    public ReportDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(Report report) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "INSERT REPORTS (TYPE, START_DATETIME, FINISH_DATETIME, STATUS, COMMENT, CONTENT, PARAMETERS, " +
                "USER_ACCOUNT_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?); ";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, report.getType().name());
        st.setString(2, new Timestamp(report.getStartDateTime().getTime()).toString());
        st.setString(3, null);
        st.setString(4, CREATED.name());
        st.setString(5, null);
        st.setString(6, null);
        st.setString(7, null);
        st.setInt(8, report.getUserAccountId());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public boolean edit(Report report) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE REPORTS " +
                "SET FINISH_DATETIME = ?, " +
                "STATUS = ?, " +
                "COMMENT = ?, " +
                "CONTENT = ?, " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1,
                report.getFinishDateTime() != null ? new Timestamp(report.getFinishDateTime().getTime()).toString() :
                        null);
        st.setString(2, report.getStatus().name());
        st.setString(3, report.getComment());
        st.setBlob(4, report.getContent());
        st.setInt(5, report.getId());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public boolean setTaskId(int reportId, int taskId) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE REPORTS " +
                "SET TASK_ID = ? " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, taskId);
        st.setInt(2, reportId);
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
