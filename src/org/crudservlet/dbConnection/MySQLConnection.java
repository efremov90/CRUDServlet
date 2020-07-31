package org.crudservlet.dbConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static Connection conn = null;

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
}
