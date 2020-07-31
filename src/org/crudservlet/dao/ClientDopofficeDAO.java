package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.ClientDopoffice;

import java.sql.*;
import java.util.logging.Logger;

public class ClientDopofficeDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(ClientDopofficeDAO.class.getName());

    public ClientDopofficeDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(ClientDopoffice client) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "INSERT CLIENTS (CLIENT_CODE, CLIENT_NAME, CLIENT_TYPE_ID, ADDRESS, CLOSE_DATE) " +
                "VALUES (?, ?, ?, ?, ?)";
        logger.info(client.toString());
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, client.getClientCode());
        st.setString(2, client.getClientName());
        st.setInt(3, 1);
        st.setString(4, client.getAddress());
        st.setDate(5, client.getCloseDate() != null ? new Date(client.getCloseDate().getTime()) : null);
        result = st.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }

    public boolean edit(ClientDopoffice client) throws SQLException {
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
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }

    public ClientDopoffice getClientByCode(String clientCode) throws SQLException {
        logger.info("start");

        ClientDopoffice client = null;
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM CLIENTS " +
                "WHERE CLIENT_CODE = ? AND CLIENT_TYPE_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, clientCode);
        st.setInt(2, 1);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            client = new ClientDopoffice(
                    rs.getInt("id"),
                    rs.getNString("client_code"),
                    rs.getNString("client_name"),
                    rs.getNString("address"),
                    rs.getDate("close_date") != null ?
                            new java.util.Date(rs.getDate("close_date").getTime()) : null
            );
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return client;
    }
}
