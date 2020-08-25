package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Request;

import java.sql.*;
import java.util.logging.Logger;

public class RequestDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(RequestDAO.class.getName());

    public RequestDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public Integer create(Request request) throws SQLException {
        logger.info("start");

        Integer result = null;
//        try {
        String sql = "INSERT REQUESTS (REQUEST_UUID, CREATE_DATE, CREATE_DATETIME, CLIENT_CODE, STATUS, COMMENT) " +
                "VALUES (?, ?, ?, ?, ?, ?); ";
        logger.info(request.toString());
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, request.getRequestUUID());
        st.setDate(2, new Date(request.getCreateDate().getTime()));
        st.setString(3, new Timestamp(request.getCreateDateTime().getTime()).toString());
        st.setString(4, request.getClientCode());
        st.setString(5, request.getRequestStatus().name());
        st.setString(6, request.getComment());
        st.executeUpdate();

        result = MySQLConnection.getLastInsertId();
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public boolean edit(Request request) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE REQUESTS " +
                "SET STATUS = ? " +
                "WHERE REQUEST_UUID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, request.getRequestStatus().name());
        st.setString(2, request.getRequestUUID());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
