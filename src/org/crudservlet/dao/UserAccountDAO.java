package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.AccountSession;
import org.crudservlet.model.UserAccount;

import java.sql.*;
import java.util.logging.Logger;

public class UserAccountDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(UserAccountDAO.class.getName());

    public UserAccountDAO() throws ClassNotFoundException {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    protected UserAccount getUser(ResultSet rs) throws SQLException {
        logger.info("start");

        UserAccount userAccount = new UserAccount(
                rs.getInt("id"),
                rs.getNString("first_name"),
                rs.getNString("last_name"),
                rs.getNString("account"),
                rs.getNString("password")
        );
        /*
                rs.getNString("session_id"),
                Timestamp.valueOf(rs.getNString("last_event_session_datetime"));*/

        logger.info(":" + userAccount.toString());

        return userAccount;
    }

    public UserAccount getUserAccountById(int id) throws SQLException {
        logger.info("start");

        UserAccount userAccount = null;
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM USER_ACCOUNT " +
                "WHERE id = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            userAccount = getUser(rs);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return userAccount;
    }

    public UserAccount getUserAccountByAccount(String account) throws SQLException {
        logger.info("start");

        UserAccount userAccount = null;
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM USER_ACCOUNT " +
                "WHERE account = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, account);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            userAccount = getUser(rs);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return userAccount;
    }

    public UserAccount getUserAccountBySessionId(String sessionId) throws SQLException {
        logger.info("start");

        UserAccount userAccount = null;
//        try {
        AccountSessionDAO accountSessionDAO = new AccountSessionDAO();
        AccountSession accountSession = accountSessionDAO.getAccountSessionBySessionId(sessionId);
        if (accountSession != null) {
            userAccount = this.getUserAccountById(accountSession.getUserAccountId());
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return userAccount;
    }
}
