package org.crudservlet.service;

import org.crudservlet.dao.PermissionDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.Permission;
import org.crudservlet.model.Permissions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Logger;

public class PermissionService {

    private Connection conn;
    private Logger logger = Logger.getLogger(PermissionService.class.getName());

    public PermissionService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean isPermission(int userAccountId, Permissions permission) {

        logger.info("start");

        boolean result = false;

        switch (userAccountId) {
            case -1: //system
                return result = true;
        }

        try {
            String sql = "SELECT " +
                    "p.* " +
                    "FROM PERMISSIONS p " +
                    "INNER JOIN PERMISSIONS_XREF_ROLES pxr ON p.ID = pxr.PERMISSION_ID " +
                    "INNER JOIN ROLES_XREF_USER_ACCOUNT rxua ON pxr.ROLE_ID = rxua.ROLE_ID " +
                    "WHERE rxua.USER_ACCOUNT_ID = ? AND p.CODE = ?";

            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userAccountId);
            st.setString(2, permission.name());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
