package org.crudservlet.dbConnection;

import org.crudservlet.service.RequestService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class MySQLConnection {

    private static Connection conn = null;
    private static Logger logger = Logger.getLogger(RequestService.class.getName());

    public static Connection getConnection() {
        String DB = "crud";
        String HOST = "localhost";
        String USER = "root";
        String PASSWORD = "admin";
        return getConnection(HOST, DB, USER, PASSWORD);
    }

    public static Connection getConnection(String host, String db,
                                           String user, String password) {
        String DRIVER = "com.mysql.jdbc.Driver";
        String connectionURL = "jdbc:mysql://"
                + host
                + ":3306/"
                + db
                + "?"
                + "serverTimezone=Europe/Moscow";
        try {
            Class.forName(DRIVER);
            if (conn != null) {
                return conn;
            } else {
                try {
                    conn = DriverManager.getConnection(connectionURL, user,
                            password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Integer getLastInsertId() throws SQLException {

        Integer id = null;
//        try {
        String sql = "SELECT LAST_INSERT_ID() id FROM DUAL";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            id = rs.getInt("id");
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return id;
    }
}
