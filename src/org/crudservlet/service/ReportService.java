package org.crudservlet.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.crudservlet.dao.*;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED;
import static org.crudservlet.model.Permissions.REPORT_GENERATE_REQUESTS_DETAILED;
import static org.crudservlet.model.ReportStatusType.*;
import static org.crudservlet.model.TaskType.REPORT;

public class ReportService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ReportService.class.getName());

    public ReportService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(Report report, int userAccountId) throws Exception {
        logger.info("start");

        Integer result = null;

        conn.setAutoCommit(false);

        report.setStatus(CREATED);
        report.setUserAccountId(userAccountId);

        Integer reportId = new ReportDAO().create(report);

        result = reportId;

        new AuditService().create(
                AuditOperType.RUN_REPORT,
                userAccountId,
                new java.util.Date(),
                String.format("Тип отчета: %s \n" +
                                "Параметры формирования: %s \n",
                        report.getType().getDescription(),
                        report.getParameters()),
                reportId
        );

        Task task = new Task();
        task.setType(REPORT);
        task.setCreateDateTime(new java.util.Date());
        task.setPlannedStartDateTime(task.getCreateDateTime());
        task.setStatus(TaskStatusType.CREATED);
        task.setUserAccountId(userAccountId);

        Integer taskId = new TaskService().create(task, userAccountId, reportId);

        new ReportTasksDAO().create(reportId, taskId);

        conn.commit();
        conn.setAutoCommit(true);

        logger.info(":" + result);

        return result;
    }

    public boolean start(int reportId) throws Exception {
        logger.info("start");

        boolean result = false;

        int taskId = new ReportTasksDAO().getTaskByReport(reportId);

        conn.setAutoCommit(false);

        String sql = "UPDATE REPORTS " +
                "SET STATUS = ? " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, STARTED.name());
        st.setInt(2, reportId);
        result = st.executeUpdate() > 0;

        result = new TaskService().start(taskId);

        conn.commit();
        conn.setAutoCommit(true);

        logger.info(":" + result);

        return result;
    }

    public boolean finish(int reportId, Blob content) throws Exception {
        logger.info("start");

        boolean result = false;

        int taskId = new ReportTasksDAO().getTaskByReport(reportId);

        conn.setAutoCommit(false);

        String sql = "UPDATE REPORTS " +
                "SET STATUS = ?, " +
                "CONTENT = ? " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, FINISH.name());
        st.setBlob(2, content);
        st.setInt(3, reportId);
        result = st.executeUpdate() > 0;

        result = new TaskService().finish(taskId);

        conn.commit();
        conn.setAutoCommit(true);

        logger.info(":" + result);

        return result;
    }

    public Blob getContent(int reportId, String type, int userAccountId) throws Exception {
        logger.info("start");

        Blob content = null;

        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);

        ReportType reportType = new ReportDAO().getType(reportId);

        switch (reportType) {
            case REPORT_REQUESTS_DETAILED:
                if (!new PermissionService().isPermission(userAccountId, REPORT_GENERATE_REQUESTS_DETAILED))
                    throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                            userAccount.getAccount(),
                            REPORT_GENERATE_REQUESTS_DETAILED.name()));
                break;
            case REPORT_REQUESTS_CONSOLIDATED:
                if (!new PermissionService().isPermission(userAccountId, REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED))
                    throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                            userAccount.getAccount(),
                            REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED.name()));
                break;
        }

        String sql = "SELECT CONTENT  " +
                "FROM REPORTS r " +
                "WHERE ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, reportId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            content = rs.getBlob("CONTENT");
        }

        return content;
    }

    public ArrayList<Report> getReports(Date fromCreateDatetime, Date toCreateDatetime,
                                        ReportStatusType reportStatusType) throws SQLException {
        logger.info("start");

        ArrayList<Report> reports = new ArrayList<>();

        String sql = "SELECT " +
                "r.ID, r.TYPE, r.CREATE_DATETIME, r.START_DATETIME, r.FINISH_DATETIME, r.STATUS, r.PARAMETERS, " +
                "r.USER_ACCOUNT_ID " +
                "FROM REPORTS r " +
                "LEFT JOIN REPORT_XREF_TASK rxt ON r.ID = rxt.REPORT_ID " +
                "LEFT JOIN TASKS t ON rxt.TASK_ID = t.ID " +
                "WHERE 1=1 " +
                (fromCreateDatetime != null ? " AND r.CREATE_DATETIME >= " + "'" + fromCreateDatetime + "'" : "") +
                (toCreateDatetime != null ? " AND r.CREATE_DATETIME <= " + "'" + toCreateDatetime + "'" : "") +
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

        return reports;
    }

    public ByteArrayOutputStream generate(ReportType reportType, Map<String, Object> parameters,
                                          JRBeanCollectionDataSource data) throws JRException, IOException {

        String PROJECT_PATH = "C:\\Users\\NEO\\IdeaProjects\\CRUDServlet";
        String FILE_NAME = null;
        String REPORT_pattern = "\\jrxml";
        String FILE_EXTENSION_PATTERN = ".jrxml";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        File reportPattern = new File(PROJECT_PATH + REPORT_pattern + "\\" + reportType.name() + FILE_EXTENSION_PATTERN);
        JasperDesign jasperDesign = JRXmlLoader.load(reportPattern);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, data);
//        JasperPrint jasperPrint = JasperFillManager.fillReportToFile(, parameters, data);
//        JasperExportManager.exportReportToPdfFile(jasperPrint, PROJECT_PATH + "\\" + FILE_NAME + ".pdf");
//        baos.write(JasperExportManager.exportReportToPdf(jasperPrint));


        // Make sure the output directory exists.
        File outDir = new File("C:/jasperoutput");
        outDir.mkdirs();

        // Export to PDF.
        JasperExportManager.exportReportToHtmlFile(jasperPrint,
                "C:/jasperoutput/StyledTextReport.html");

//        JRXlsExporter exporterXLS = new JRXlsExporter();
//        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "C:/jasperoutput/StyledTextReport.xls");
//        exporterXLS.exportReport();

        JRXlsExporter xlsExporter = new JRXlsExporter();
        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput("C:/jasperoutput/StyledTextReport.xls"));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsExporter.setConfiguration(xlsReportConfiguration);
        xlsExporter.exportReport();
//        baos.writeTo(new SimpleOutputStreamExporterOutput("C:/jasperoutput/StyledTextReport.xls").getOutputStream());

        JRXlsxExporter exporterXLSX = new JRXlsxExporter();
        exporterXLSX.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXLSX.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "C:/jasperoutput/StyledTextReport.xlsx");
        exporterXLSX.exportReport();

//        JasperExportManager.exportReportToPdfFile(jasperPrint,
//                "C:/jasperoutput/StyledTextReport.pdf");

        return baos;
    }
}
