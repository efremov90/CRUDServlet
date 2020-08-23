package org.crudservlet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.dto.ReportRequestsDetailedDTO;
import org.crudservlet.model.Report;
import org.crudservlet.model.ReportType;
import org.crudservlet.model.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.*;

public class ReportRequestsDetailedService extends ReportService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ReportRequestsDetailedService.class.getName());

    public ReportRequestsDetailedService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(ReportRequestsDetailedDTO parameters, int userAccountId) throws Exception {
        logger.info("start");

        Integer result = null;

        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, REPORT_GENERATE_REQUESTS_DETAILED))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    REPORT_GENERATE_REQUESTS_DETAILED.name()));

        Report report = new Report();
        report.setType(ReportType.REPORT_REQUESTS_DETAILED);
        report.setParameters(new ObjectMapper().writeValueAsString(parameters));
        result = new ReportService().create(report, userAccountId);
        super.create(report, userAccountId);

        return result;
    }

    public boolean start(int userAccountId) throws Exception {
        logger.info("start");

        boolean result = false;

        String sql = "SELECT r.client_code, c.client_name, ct.type, r.create_date, r.status, r.comment " +
                "FROM REQUESTS r " +
                "LEFT JOIN CLIENTS c on r.client_code = c.client_code " +
                "LEFT JOIN client_type ct on c.client_type_id = ct.id " +
                "WHERE r.create_date BETWEEN ? AND ? " +
                "AND r.CLIENT_CODE = ? " +
                "ORDER BY ct.type, c.client_name, c.client_code, r.create_date, r.id";

        PreparedStatement st = conn.prepareStatement(sql);
//        st.setDate(1, (java.sql.Date) fromCreateDate);
//        st.setDate(2, (java.sql.Date) toCreateDate);
//        st.setString(3, clientCode);
        st.executeQuery();

        return result;
    }

}