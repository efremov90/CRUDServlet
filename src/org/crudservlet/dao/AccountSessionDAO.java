package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.AccountSession;

import java.sql.*;
import java.util.logging.Logger;

public class AccountSessionDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(AccountSessionDAO.class.getName());

    public AccountSessionDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(AccountSession accountSession) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "INSERT ACCOUNT_SESSIONS (SESSION_ID, CREATE_DATETIME, LAST_EVENT_DATETIME, USER_ACCOUNT_ID)" +
                " " +
                "VALUES (?, ?, ?, ?)";
        logger.info(accountSession.toString());
        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, accountSession.getSessionId());
        st.setString(2, new Timestamp(accountSession.getCreateDateTime().getTime()).toString());
        st.setString(3, new Timestamp(accountSession.getLastEventDateTime().getTime()).toString());
        st.setInt(4, accountSession.getUserAccountId());
        result = st.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }

    public boolean updateLastEventDate(AccountSession accountSession) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "UPDATE ACCOUNT_SESSIONS " +
                "SET last_event_datetime = ? " +
                "WHERE session_id = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, new Timestamp(accountSession.getLastEventDateTime().getTime()).toString());
        st.setString(2, accountSession.getSessionId());
        result = st.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }

    public boolean delete(String sessionId) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "DELETE FROM ACCOUNT_SESSIONS " +
                "WHERE session_id = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, sessionId);
        result = st.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }

    public AccountSession getAccountSessionBySessionId(String sessionId) throws SQLException {
        logger.info("start");

        AccountSession accountSession = null;
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM ACCOUNT_SESSIONS " +
                "WHERE session_id = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, sessionId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            accountSession = new AccountSession(
                    rs.getInt("id"),
                    rs.getNString("session_id"),
                    Timestamp.valueOf(rs.getNString("create_datetime")),
                    Timestamp.valueOf(rs.getNString("last_event_datetime")),
                    rs.getInt("user_account_id")
            );
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return accountSession;
    }
}
