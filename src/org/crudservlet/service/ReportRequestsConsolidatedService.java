package org.crudservlet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.dto.ReportRequestsConsolidatedDTO;
import org.crudservlet.model.*;
import org.crudservlet.reportbean.ReportRequestsDetailedBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED;

public class ReportRequestsConsolidatedService extends ReportService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ReportRequestsConsolidatedService.class.getName());

    public ReportRequestsConsolidatedService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Map<String, Object> getParametersJR(ReportRequestsConsolidatedDTO parameters) {
        Map<String, Object> parametersJR = new HashMap<String, Object>();
        parametersJR.put("fromCreateDate",
                (parameters.getFromCreateDate() != null && parameters.getFromCreateDate() != "") ?
                        Date.valueOf(parameters.getFromCreateDate()) : "");
        parametersJR.put("toCreateDate",
                (parameters.getToCreateDate() != null && parameters.getToCreateDate() != "") ?
                        Date.valueOf(parameters.getToCreateDate()) : "");
        return parametersJR;
    }

    public Integer create(ReportRequestsConsolidatedDTO parameters, FormatReportType formatReportType,
                          int userAccountId) throws Exception {

        logger.info("start");

        Integer result = null;

        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED.name()));

        Report report = new Report();
        report.setType(ReportType.REPORT_REQUESTS_CONSOLIDATED);
        report.setFormat(formatReportType);
        report.setFromPeriodDate((parameters.getFromCreateDate() != null && parameters.getFromCreateDate() != "") ?
                Date.valueOf(parameters.getFromCreateDate()) : null);
        report.setToPeriodDate((parameters.getToCreateDate() != null && parameters.getToCreateDate() != "") ?
                Date.valueOf(parameters.getToCreateDate()) : null);
        report.setParameters(new ObjectMapper().writeValueAsString(parameters));
        result = super.create(report, userAccountId);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ReportTaskService(result));
        executorService.shutdown();

        logger.info(":" + result);
        return result;
    }

    public ArrayList<ReportRequestsDetailedBean> getData(java.util.Date fromCreateDate, java.util.Date toCreateDate) throws Exception {
        logger.info("start");

        ArrayList<ReportRequestsDetailedBean> result = new ArrayList<>();

        String sql = "SELECT r.client_code, c.client_name, ct.type, r.create_date, r.status, r.comment " +
                "FROM REQUESTS r " +
                "LEFT JOIN CLIENTS c on r.client_code = c.client_code " +
                "LEFT JOIN client_type ct on c.client_type_id = ct.id " +
                "WHERE 1=1 " +
                (fromCreateDate != null ? " AND r.CREATE_DATE >= " + "'" + fromCreateDate + "'" : "") +
                (toCreateDate != null ? " AND r.CREATE_DATE <= " + "'" + toCreateDate + "'" : "") +
                "ORDER BY ct.type, c.client_name, c.client_code, r.create_date, r.id";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            ReportRequestsDetailedBean reportRequestsDetailedBean = new ReportRequestsDetailedBean();
            reportRequestsDetailedBean.setClientCode(rs.getNString("client_code"));
            reportRequestsDetailedBean.setClientName(rs.getNString("client_name"));
            reportRequestsDetailedBean.setClientType(ClientTypeType.valueOf(rs.getNString("type")).getDescription());
            reportRequestsDetailedBean.setCreateDate(new java.sql.Date(rs.getDate("create_date").getTime()).toString());
            reportRequestsDetailedBean.setStatus(RequestStatusType.valueOf(rs.getNString("status")).getDescription());
            reportRequestsDetailedBean.setComment(rs.getNString("comment"));
            reportRequestsDetailedBean.setRankSorted(0);
            result.add(reportRequestsDetailedBean);
        }

//        Thread.sleep(10000);

        logger.info("finish");
        return result;
    }

}
