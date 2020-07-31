package org.crudservlet.dao;

import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Permission;
import org.crudservlet.model.Permissions;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class PermissionDAO {

    private Connection conn;
    private Logger logger = Logger.getLogger(PermissionDAO.class.getName());

    public PermissionDAO() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public HashSet<Permission> getPermissionsByUserAccountId(int id) throws SQLException {
        logger.info("start");

        HashSet<Permission> permissions = new HashSet<>();
//        try {
        String sql = "SELECT " +
                "p.* " +
                "FROM PERMISSIONS p " +
                "INNER JOIN PERMISSIONS_XREF_ROLES pxr ON p.ID = pxr.PERMISSION_ID " +
                "INNER JOIN ROLES_XREF_USER_ACCOUNT rxua ON pxr.ROLE_ID = rxua.ROLE_ID " +
                "WHERE rxua.USER_ACCOUNT_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Permission permission = new Permission();
            permission.setCode(Permissions.valueOf(rs.getNString("Code")));
            permissions.add(permission);
        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return permissions;
    }

}
