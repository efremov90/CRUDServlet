package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.ATMTypeType;
import org.crudservlet.model.ClientATM;

import java.sql.*;
import java.util.logging.Logger;

public class ClientATMDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(ClientATMDAO.class.getName());

    public ClientATMDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(ClientATM client) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "INSERT CLIENTS (CLIENT_CODE, CLIENT_NAME, CLIENT_TYPE_ID, ADDRESS, CLOSE_DATE) " +
                "VALUES (?, ?, ?, ?, ?); ";
        logger.info(client.toString());
        PreparedStatement st = conn.prepareStatement(sql);

        st.setString(1, client.getClientCode());
        st.setString(2, client.getClientName());
        st.setInt(3, 2);
        st.setString(4, client.getAddress());
        st.setDate(5, client.getCloseDate() != null ? new Date(client.getCloseDate().getTime()) : null);
        st.executeUpdate();

        sql = "INSERT ATMS (CLIENT_ID, ATM_TYPE) " +
                "VALUES (last_insert_id(), ?); ";
        st = conn.prepareStatement(sql);

        st.setString(1, client.getAtmType().name());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public boolean edit(ClientATM client) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE CLIENTS c, ATMS a " +
                "SET c.CLIENT_NAME = ? , " +
                "c.ADDRESS = ? , " +
                "c.CLOSE_DATE = ? , " +
                "a.ATM_TYPE = ? " +
                "WHERE c.ID=a.CLIENT_ID AND c.CLIENT_CODE = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, client.getClientName());
        st.setString(2, client.getAddress());
        st.setDate(3, client.getCloseDate() != null ? new Date(client.getCloseDate().getTime()) : null);
        st.setString(4, client.getAtmType().name());
        st.setString(5, client.getClientCode());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
