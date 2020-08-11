package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Client;
import org.crudservlet.model.ClientATM;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ClientDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(ClientDAO.class.getName());

    public ClientDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(Client client) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "INSERT CLIENTS (CLIENT_CODE, CLIENT_NAME, CLIENT_TYPE_ID, ADDRESS, CLOSE_DATE) " +
                "VALUES (?, ?, ?, ?, ?); ";
        logger.info(client.toString());

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, client.getClientCode());
        st.setString(2, client.getClientName());
        st.setInt(3, client.getClientType().getCode());
        st.setString(4, client.getAddress());
        st.setDate(5, client.getCloseDate() != null ? new Date(client.getCloseDate().getTime()) : null);
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public boolean edit(Client client) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE CLIENTS " +
                "SET CLIENT_NAME = ? , " +
                "ADDRESS = ? , " +
                "CLOSE_DATE = ? " +
                "WHERE CLIENT_CODE = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, client.getClientName());
        st.setString(2, client.getAddress());
        st.setDate(3, client.getCloseDate() != null ? new Date(client.getCloseDate().getTime()) : null);
        st.setString(4, client.getClientCode());
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
