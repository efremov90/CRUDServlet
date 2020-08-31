package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Report;
import org.crudservlet.model.ReportStatusType;
import org.crudservlet.model.ReportType;

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

    public Integer create(Report report) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "INSERT REPORTS (TYPE, STATUS, COMMENT, CONTENT, PARAMETERS, FROM_PERIOD_DATE, TO_PERIOD_DATE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?); ";
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, report.getType().name());
        st.setString(2, CREATED.name());
        st.setString(3, null);
        st.setString(4, null);
        st.setString(5, report.getParameters());
        st.setDate(6, report.getFromPeriodDate() != null ? new Date(report.getFromPeriodDate().getTime()) : null);
        st.setDate(7, report.getToPeriodDate() != null ? new Date(report.getToPeriodDate().getTime()) : null);
        st.executeUpdate();

        result = MySQLConnection.getLastInsertId();
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public ReportType getType(int reportId) throws SQLException {
        logger.info("start");

        ReportType result = null;
//        try {
        String sql = "SELECT TYPE  " +
                "FROM REPORTS r " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, reportId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            result = ReportType.valueOf(rs.getString("TYPE"));
        }
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public ReportStatusType getReportStatus(int reportId) throws SQLException {
        logger.info("start");

        ReportStatusType reportStatusType = null;
//        try {
        String sql = "SELECT STATUS  " +
                "FROM REPORTS r " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, reportId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            reportStatusType = ReportStatusType.valueOf(rs.getString("STATUS"));
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return reportStatusType;
    }
}
