package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.AuditOper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditOperDAO {

    private Connection conn;

    public AuditOperDAO() throws ClassNotFoundException {
        conn = MySQLConnection.getConnection();
    }

    public AuditOper getAuditOperByCode(String operCode) throws SQLException {
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
