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

    public Integer create(ClientATM client) throws Exception {
        logger.info("start");

        Integer result = null;
//        try {
        conn.setAutoCommit(false);

        Integer clientId = new ClientDAO().create(client);

        String sql = "INSERT ATMS (CLIENT_ID, ATM_TYPE) " +
                "VALUES (?, ?); ";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, clientId);
        st.setString(2, client.getAtmType().name());
        st.executeUpdate();

        result = clientId;

        conn.commit();
        conn.setAutoCommit(true);
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
        conn.setAutoCommit(false);

        new ClientDAO().edit(client);

        String sql = "UPDATE CLIENTS c, ATMS a " +
                "SET a.ATM_TYPE = ? " +
                "WHERE c.ID=a.CLIENT_ID AND c.CLIENT_CODE = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, client.getAtmType().name());
        st.setString(2, client.getClientCode());
        result = st.executeUpdate() > 0;

        conn.commit();
        conn.setAutoCommit(true);
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
