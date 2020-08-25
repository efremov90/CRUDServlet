package org.crudservlet.service;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.CLIENTS_EDIT;

public class ClientService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ClientService.class.getName());

    public ClientService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public void validateExistsClient(String clientCode) throws Exception {
        logger.info("start");
//        try {
        if (getClient(clientCode) != null)
            throw new Exception(String.format("Уже есть объект с кодом %s.",
                    clientCode));
/*        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public Integer create(Client client) throws Exception {
        logger.info("start");

        Integer result = null;
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

        result = MySQLConnection.getLastInsertId();
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }

    public Client getClient(String clientCode) throws SQLException {
        logger.info("start");

        Client client = null;
//        try {
        String sql = "SELECT " +
                "c.*, ct.TYPE, a.ATM_TYPE " +
                "FROM CLIENTS c " +
                "INNER JOIN CLIENT_TYPE ct ON c.CLIENT_TYPE_ID = ct.ID " +
                "LEFT JOIN ATMS a ON c.ID = a.CLIENT_ID " +
                "WHERE c.CLIENT_CODE = ? ";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, clientCode);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            client = new Client();
            client.setId(rs.getInt("id"));
            client.setClientCode(rs.getNString("client_code"));
            client.setClientName(rs.getNString("client_name"));
            client.setClientType(ClientTypeType.valueOf(rs.getNString("type")));
            client.setAddress(rs.getNString("address"));
            client.setCloseDate(rs.getDate("close_date") != null ?
                    new java.util.Date(rs.getDate("close_date").getTime()) : null);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return client;
    }

    public ArrayList<Client> getClients(ClientTypeType clientType) throws SQLException {
        logger.info("start");

        ArrayList<Client> clients = new ArrayList<>();
//        try {
        String sql = "SELECT " +
                "c.*, ct.TYPE, a.ATM_TYPE " +
                "FROM CLIENTS c " +
                "INNER JOIN CLIENT_TYPE ct ON c.CLIENT_TYPE_ID = ct.ID " +
                "LEFT JOIN ATMS a ON c.ID = a.CLIENT_ID " +
                "WHERE 1=1 " +
                (clientType != null ? " AND ct.TYPE = " + "'" + clientType.name() + "'" : "");

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("id"));
            client.setClientCode(rs.getNString("client_code"));
            client.setClientName(rs.getNString("client_name"));
            client.setClientType(ClientTypeType.valueOf(rs.getNString("type")));
            client.setAddress(rs.getNString("address"));
            client.setCloseDate(rs.getDate("close_date") != null ?
                    new java.util.Date(rs.getDate("close_date").getTime()) : null);
            clients.add(client);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return clients;
    }
}
