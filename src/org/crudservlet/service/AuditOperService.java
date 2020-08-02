package org.crudservlet.service;

import org.crudservlet.dao.AuditDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.AuditOper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AuditOperService {

    private Connection conn;
    private Logger logger = Logger.getLogger(AuditDAO.class.getName());

    public AuditOperService() throws ClassNotFoundException {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public AuditOper getAuditOperByCode(String operCode) throws SQLException {
        logger.info("start");

        AuditOper auditOper = new AuditOper();
//        try {
        String sql = "SELECT " +
                "* " +
                "FROM AUDIT_OPER " +
                "WHERE OPER_CODE = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, operCode);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            auditOper.setId(rs.getInt("Id"));
            auditOper.setOperCode(rs.getNString("OperCode"));
            auditOper.setDescription(rs.getNString("Description"));
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return auditOper;
    }
}
