package org.crudservlet.service;

import org.crudservlet.dao.ClientATMDAO;
import org.crudservlet.dao.RequestDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.CLIENTS_CREATE;
import static org.crudservlet.model.Permissions.REQUESTS_CREATE;
import static org.crudservlet.model.RequestStatusType.CREATED;

public class RequestService {

    private Connection conn;
    private Logger logger = Logger.getLogger(RequestService.class.getName());

    public RequestService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public void validateExistsRequest(String requestUUID) throws Exception {
        logger.info("start");
//        try {
        if (getRequestByUUID(requestUUID) != null)
            throw new Exception(String.format("Уже есть заявка с UUID %s.",
                    requestUUID));
/*        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public Request getRequestById(int id) throws SQLException {
        return getRequest(id, null);
    }

    public Request getRequestByUUID(String requestUUID) throws SQLException {
        return getRequest(-1, requestUUID);
    }

    private Request getRequest(int requestId, String requestUUID) throws SQLException {
        logger.info("start");

        Request request = null;
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM REQUESTS " +
                "WHERE 1=1 " +
                (requestId != -1 ? " AND ID = " + requestId : "") +
                (requestUUID != null ? " AND REQUEST_UUID = " + "'" + requestUUID + "'" : "");

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            request = new Request();
            request.setId(rs.getInt("id"));
            request.setRequestUUID(rs.getNString("request_uuid"));
            request.setCreateDate(new java.util.Date(rs.getDate("create_date").getTime()));
            request.setCreateDateTime(Timestamp.valueOf(rs.getNString("create_datetime")));
            request.setClientCode(rs.getNString("client_code"));
            request.setComment(rs.getNString("comment"));
            request.setRequestStatus(RequestStatusType.valueOf(rs.getNString("status")));
            request.setLastDateTimeChangeRequestStatus(Timestamp.valueOf(rs.getNString("last_datetime_change_request_status")));
            request.setLastUserAccountIdChangeRequestStatus(rs.getInt("last_user_account_id_change_request_status"));
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return request;
    }

    public boolean create(Request request, int userAccountId) throws Exception {
        logger.info("start");

        boolean result = false;
//        try {
        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, REQUESTS_CREATE))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    REQUESTS_CREATE.name()));
        if (getRequestByUUID(request.getRequestUUID()) != null)
            throw new Exception(String.format("Уже есть заявка с UUID %s.",
                    request.getRequestUUID()));
        request.setRequestStatus(CREATED);
        result = new RequestDAO().create(request);
/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public boolean cancel(String requestUUID, int userAccountId) throws Exception {
        logger.info("start");

        boolean result = false;
//        try {
        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, REQUESTS_CREATE))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    REQUESTS_CREATE.name()));
        if (getRequestByUUID(requestUUID) == null)
            throw new Exception(String.format("Заявка с UUID %s отсутствует.",
                    requestUUID));
        //result = new RequestDAO().create(request);
/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public ArrayList<Request> getRequests(Date fromCreateDate, Date toCreateDate, String clientCode,
                                          RequestStatusType requestStatus) throws SQLException {
        logger.info("start");

        ArrayList<Request> requests = new ArrayList<>();
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM REQUESTS " +
                "WHERE 1=1 " +
                (fromCreateDate != null ? " AND CREATE_DATE >= " + fromCreateDate : "") +
                (toCreateDate != null ? " AND CREATE_DATE <= " + "'" + toCreateDate + "'" : "") +
                (clientCode != null ? " AND CLIENT_CODE = " + "'" + clientCode + "'" : "") +
                (requestStatus != null ? " AND STATUS = " + "'" + requestStatus.name() + "'" : "");

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Request request = new Request();
            request.setId(rs.getInt("id"));
            request.setRequestUUID(rs.getNString("request_uuid"));
            request.setCreateDate(new java.util.Date(rs.getDate("create_date").getTime()));
            request.setCreateDateTime(Timestamp.valueOf(rs.getNString("create_datetime")));
            request.setClientCode(rs.getNString("client_code"));
            request.setComment(rs.getNString("comment"));
            request.setRequestStatus(RequestStatusType.valueOf(rs.getNString("status")));
            request.setLastDateTimeChangeRequestStatus(Timestamp.valueOf(rs.getNString("last_datetime_change_request_status")));
            request.setLastUserAccountIdChangeRequestStatus(rs.getInt("last_user_account_id_change_request_status"));
            requests.add(request);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return requests;
    }
}
