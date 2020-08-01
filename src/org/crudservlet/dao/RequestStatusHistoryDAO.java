package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.RequestStatusHistory;

import java.sql.*;
import java.util.logging.Logger;

public class RequestStatusHistoryDAO {
    private Connection conn;
    private Logger logger = Logger.getLogger(RequestStatusHistoryDAO.class.getName());

    public RequestStatusHistoryDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(RequestStatusHistory requestStatusHistory) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE REQUESTS_STATUS_HISTORY " +
                "SET IS_LAST_STATUS = 0 " +
                "WHERE REQUEST_ID = ? AND IS_LAST_STATUS = 1";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, requestStatusHistory.getRequestId());
        st.executeUpdate();

        sql = "INSERT REQUESTS_STATUS_HISTORY (REQUEST_ID, STATUS, COMMENT, IS_LAST_STATUS, EVENT_DATETIME, USER_ACCOUNT_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?); ";
        logger.info(requestStatusHistory.toString());
        st = conn.prepareStatement(sql);

        st.setInt(1, requestStatusHistory.getRequestId());
        st.setString(2, requestStatusHistory.getStatus().name());
        st.setString(3, requestStatusHistory.getComment());
        st.setInt(4, 1);
        st.setString(5, new Timestamp(requestStatusHistory.getEventDateTime().getTime()).toString());
        st.setInt(6, requestStatusHistory.getUserId());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
