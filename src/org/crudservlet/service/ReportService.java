package org.crudservlet.service;

import org.crudservlet.dao.*;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.crudservlet.model.ReportStatusType.*;

public class ReportService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ReportService.class.getName());

    public ReportService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(Report report, int userAccountId) throws Exception {
        logger.info("start");

        boolean result = false;
//        try {
        conn.setAutoCommit(false);

        report.setStatus(CREATED);
        report.setStartDateTime(new java.util.Date());
        report.setUserAccountId(userAccountId);
        result = new ReportDAO().create(report);

        Integer reportId = MySQLConnection.getLastInsertId();

        new AuditService().create(
                AuditOperType.RUN_REPORT,
                userAccountId,
                report.getStartDateTime(),
                String.format("Тип отчета: %s \n" +
                                "Параметры формирования: %s \n",
                        report.getStatus().getDescription(),
                        report.getParameters()),
                reportId
        );

        conn.commit();
        conn.setAutoCommit(true);
/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public boolean start(int reportId) throws Exception {
        logger.info("start");

        boolean result = false;
//        try {

        Report report = new Report();

        report.setId(reportId);
        report.setStatus(CREATED);
        result = new ReportDAO().edit(report);

        String sql = "UPDATE REPORTS " +
                "SET STATUS = ? " +
                "WHERE REPORT_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, STARTED.name());
        st.setInt(2, report.getId());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);

/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public boolean finish(int reportId, Blob content) throws Exception {
        logger.info("start");

        boolean result = false;
//        try {

        String sql = "UPDATE REPORTS " +
                "SET FINISH_DATETIME = ?, " +
                "STATUS = ?, " +
                "CONTENT = ?, " +
                "WHERE REPORT_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, new Timestamp(new java.util.Date().getTime()).toString());
        st.setString(2, FINISH.name());
        st.setBlob(4, content);
        st.setInt(5, reportId);
        result = st.executeUpdate() > 0;

/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

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

    public Blob getContent(int reportId) throws SQLException {
        logger.info("start");

        Blob content = null;
//        try {
        String sql = "SELECT CONTENT  " +
                "FROM REPORTS r " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, reportId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            content = rs.getBlob("CONTENT");
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return content;
    }

    public ArrayList<Report> getReports(Date startDateTime, Date finishDateTime,
                                        ReportStatusType reportStatusType) throws SQLException {
        logger.info("start");

        ArrayList<Report> reports = new ArrayList<>();
//        try {
        String sql = "SELECT " +
                "r.ID, r.TYPE, r.START_DATETIME, r.FINISH_DATETIME, r.STATUS, r.PARAMETERS, r.USER_ACCOUNT_ID " +
                "FROM REPORTS r " +
                "WHERE 1=1 " +
                (startDateTime != null ? " AND r.START_DATETIME >= " + "'" + startDateTime + "'" : "") +
                (finishDateTime != null ? " AND r.FINISH_DATETIME <= " + "'" + startDateTime + "'" : "") +
                (reportStatusType != null ? " AND r.STATUS = " + "'" + reportStatusType.name() + "'" : "");

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Report report = new Report();
            report.setId(rs.getInt("id"));
            report.setType(ReportType.valueOf(rs.getNString("type")));
            report.setStartDateTime(Timestamp.valueOf(rs.getNString("START_DATETIME")));
            report.setFinishDateTime(rs.getNString("FINISH_DATETIME") != null ?
                    Timestamp.valueOf(rs.getNString("FINISH_DATETIME")) : null);
            report.setStatus(ReportStatusType.valueOf(rs.getNString("status")));
            report.setParameters(rs.getNString("PARAMETERS"));
            report.setUserAccountId(rs.getInt("USER_ACCOUNT_ID"));
            reports.add(report);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return reports;
    }
}
