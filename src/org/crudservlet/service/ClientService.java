package org.crudservlet.service;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.CLIENTS_CREATE;

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
            client = new Client(
                    rs.getInt("id"),
                    rs.getNString("client_code"),
                    rs.getNString("client_name"),
                    ClientTypeType.valueOf(rs.getNString("type")),
                    rs.getNString("atm_type") != null ?
                            ATMTypeType.valueOf(rs.getNString("atm_type")) : null,
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
            Client client = new Client(
                    rs.getInt("id"),
                    rs.getNString("client_code"),
                    rs.getNString("client_name"),
                    ClientTypeType.valueOf(rs.getNString("type")),
                    rs.getNString("atm_type") != null ?
                            ATMTypeType.valueOf(rs.getNString("atm_type")) : null,
                    rs.getNString("address"),
                    rs.getDate("close_date") != null ?
                            new java.util.Date(rs.getDate("close_date").getTime()) : null
            );
            clients.add(client);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return clients;
    }
}
