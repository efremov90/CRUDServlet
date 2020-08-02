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
        logger.info(client.toString());

        new ClientDAO().create(client);
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
        new ClientDAO().edit(client);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }
}
