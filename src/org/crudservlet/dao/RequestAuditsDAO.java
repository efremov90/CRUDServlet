package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RequestAuditsDAO {
    private Connection conn;
    private Logger logger = Logger.getLogger(RequestAuditsDAO.class.getName());

    public RequestAuditsDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(int requestId, int auditId) throws SQLException {
        logger.info("start");

        boolean result = false;
//        try {
        String sql = "INSERT REQUEST_XREF_AUDIT (REQUEST_ID, AUDIT_ID) " +
                "VALUES (?, ?); ";

        PreparedStatement st = conn.prepareStatement(sql);

        st.setInt(1, requestId);
        st.setInt(2, auditId);
        result = st.executeUpdate() > 0;
/*        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        logger.info(":" + result);
        return result;
    }
}
