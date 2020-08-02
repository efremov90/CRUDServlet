package org.crudservlet.service;

import org.crudservlet.dao.ClientATMDAO;
import org.crudservlet.dao.RequestDAO;
import org.crudservlet.dao.RequestStatusHistoryDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.CLIENTS_CREATE;
import static org.crudservlet.model.Permissions.REQUESTS_CREATE;
import static org.crudservlet.model.RequestStatusType.CANCELED;
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
        if (getRequestIdByUUID(requestUUID) != null)
            throw new Exception(String.format("Уже есть заявка с UUID %s.",
                    requestUUID));
/*        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public Integer getRequestIdByUUID(String requestUUID) throws SQLException {
        logger.info("start");

        Integer id = null;
//        try {
        String sql = "SELECT ID  " +
                "FROM REQUESTS r " +
                "WHERE REQUEST_UUID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, requestUUID);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id");
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return id;
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
                "r.ID, r.REQUEST_UUID, r.CREATE_DATE, r.CREATE_DATETIME, r.CLIENT_CODE, r.COMMENT, r. STATUS, " +
                "rsh.EVENT_DATETIME, rsh.USER_ACCOUNT_ID " +
                "FROM REQUESTS r " +
                "LEFT JOIN REQUEST_STATUS_HISTORY rsh ON r.ID = rsh.REQUEST_ID AND rsh.IS_LAST_STATUS = 1 " +
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
            request.setLastDateTimeChangeRequestStatus(Timestamp.valueOf(rs.getNString("event_datetime")));
            request.setLastUserAccountIdChangeRequestStatus(rs.getInt("user_account_id"));
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

        conn.setAutoCommit(false);

        request.setRequestStatus(CREATED);
        request.setLastDateTimeChangeRequestStatus(new java.util.Date());
        result = new RequestDAO().create(request);

        Integer requestId = MySQLConnection.getLastInsertId();

        RequestStatusHistory requestStatusHistory = new RequestStatusHistory();
        requestStatusHistory.setRequestId(requestId);
        requestStatusHistory.setStatus(CREATED);
        requestStatusHistory.setEventDateTime(request.getLastDateTimeChangeRequestStatus());
        requestStatusHistory.setUserId(userAccountId);
        new RequestStatusHistoryDAO().create(requestStatusHistory);

        new AuditService().create(
                AuditOperType.CREATE_REQUEST,
                userAccountId,
                request.getCreateDateTime(),
                String.format("UUID заявки: %s \n" +
                                "Дата создания: %s \n" +
                                "Код клиента: %s \n" +
                                "Комментарий: %s",
                        request.getRequestUUID(),
                        request.getCreateDate(),
                        request.getClientCode(),
                        request.getComment()),
                requestId
        );

        conn.commit();
        conn.setAutoCommit(true);
/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public boolean cancel(String requestUUID, int userAccountId, String comment) throws Exception {
        logger.info("start");

        boolean result = false;
//        try {
        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, REQUESTS_CREATE))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    REQUESTS_CREATE.name()));
        if (getRequestIdByUUID(requestUUID) == null)
            throw new Exception(String.format("Заявка с UUID %s отсутствует.",
                    requestUUID));

        Request request = new RequestService().getRequestByUUID(requestUUID);

        conn.setAutoCommit(false);
        request.setRequestStatus(CANCELED);
        request.setLastDateTimeChangeRequestStatus(new java.util.Date());
        result = new RequestDAO().edit(request);

        RequestStatusHistory requestStatusHistory = new RequestStatusHistory();
        requestStatusHistory.setRequestId(request.getId());
        requestStatusHistory.setStatus(CANCELED);
        requestStatusHistory.setComment(comment);
        requestStatusHistory.setEventDateTime(request.getLastDateTimeChangeRequestStatus());
        requestStatusHistory.setUserId(userAccountId);
        new RequestStatusHistoryDAO().create(requestStatusHistory);

        new AuditService().create(
                AuditOperType.CANCEL_REQUEST,
                userAccountId,
                requestStatusHistory.getEventDateTime(),
                String.format("Комментарий: %s",
                        requestStatusHistory.getComment()),
                request.getId()
        );

        conn.commit();
        conn.setAutoCommit(true);
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
