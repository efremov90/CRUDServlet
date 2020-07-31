package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Audit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;

public class AuditDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(AuditDAO.class.getName());

    public AuditDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean insert(Audit audit) throws SQLException {
        boolean result = false;
//        try {
        String sql = "INSERT (AUDIT_OPER_ID, EVENT_DATETIME, CONTENT) " +
                "VALUES (?, ?, ?)";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, audit.getAuditOperId());
        st.setString(2, new Timestamp(audit.getEventDateTime().getTime()).toString());
        st.setString(3, audit.getContent());
        result = st.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        logger.info(":" + result);
        return result;
    }
}
