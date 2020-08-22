package org.crudservlet.service;

import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.AccountSession;
import org.crudservlet.model.AuditOperType;
import org.crudservlet.model.SecurityContextStatusCodeType;
import org.crudservlet.model.UserAccount;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import static org.crudservlet.model.Configures.MAX_INACTIVE_SESSION_INTERVAL;
import static org.crudservlet.model.SecurityContextStatusCodeType.S00000;
import static org.crudservlet.model.SecurityContextStatusCodeType.S00001;

public class SecuritySevice {

    private Connection conn;
    private Logger logger = Logger.getLogger(SecuritySevice.class.getName());

    public SecuritySevice() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean isActiveSession(String sessionId) throws SQLException, ClassNotFoundException {
        logger.info("start");

        boolean result = true;

//        try {
        AccountSessionDAO accountSessionDAO = new AccountSessionDAO();
        AccountSession accountSession = accountSessionDAO.getAccountSessionBySessionId(sessionId);
        if (accountSession != null) {
            logger.info("differentTime:" + (new Date().getTime() - accountSession.getLastEventDateTime().getTime()) / 1000);
        }
        if (sessionId == null) {
            logger.info("session.getId() == null");
            result = false;
        } else if (accountSession == null) {
            logger.info("accountSession == null");
            result = false;
        } else if (
                Integer.valueOf(MAX_INACTIVE_SESSION_INTERVAL.getValue())
                        < ((new Date().getTime() - accountSession.getLastEventDateTime().getTime()) / 1000)
        ) {
            logger.info("MAX_INACTIVE_SESSION_INTERVAL <");
            result = false;
        } else {
            accountSession.setLastEventDateTime(new Date());
            accountSessionDAO.updateLastEventDate(accountSession);
        }
//        } catch (Exception e) {
//            result = false;
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }

    public SecurityContext login(String account, String password) throws Exception {
        logger.info("start");

        String sessionId = null;
        SecurityContextStatusCodeType statusCode;
        SecurityContext securityContext = new SecurityContext();
//        try {
        UserAccount userAccount = new UserAccountDAO().getUserAccountByAccount(account);

        if (userAccount != null && userAccount.getPassword().equals(password)) {
            sessionId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            AccountSessionDAO accountSessionDAO = new AccountSessionDAO();

            conn.setAutoCommit(false);

            AccountSession accountSession = new AccountSession();

            accountSession.setId(0);
            accountSession.setSessionId(sessionId);
            accountSession.setCreateDateTime(new Date());
            accountSession.setLastEventDateTime(accountSession.getCreateDateTime());
            accountSession.setUserAccountId(userAccount.getId());

            accountSessionDAO.create(accountSession);

            new AuditService().create(
                    AuditOperType.LOGIN,
                    userAccount.getId(),
                    accountSession.getCreateDateTime(),
                    "",
                    userAccount.getId()
            );

            statusCode = S00000;

            conn.commit();
            conn.setAutoCommit(true);
        } else {
            sessionId = null;
            statusCode = S00001;
        }
        securityContext.setAccount(account);
        securityContext.setPassword(password);
        securityContext.setSessionId(sessionId);
        securityContext.setStatusCode(statusCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        logger.info("securityContext: " + securityContext);
        return securityContext;
    }

    public boolean logout(String sessionId) throws Exception {
        logger.info("start");

        boolean result = false;

//        try {

        AccountSession accountSession = new AccountSessionDAO().getAccountSessionBySessionId(sessionId);

        conn.setAutoCommit(false);

        new AccountSessionDAO().delete(sessionId);

        new AuditService().create(
                AuditOperType.LOGIN,
                accountSession.getUserAccountId(),
                new Date(),
                "",
                accountSession.getUserAccountId()
        );

        conn.commit();
        conn.setAutoCommit(true);
//        } catch (Exception e) {
//            result=false;
//            e.printStackTrace();
//        }

        return result;
    }
}
