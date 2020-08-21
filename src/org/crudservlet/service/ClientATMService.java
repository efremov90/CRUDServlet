package org.crudservlet.service;

import org.crudservlet.dao.ClientATMDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dbConnection.MySQLConnection;
import org.crudservlet.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.CLIENTS_CREATE;
import static org.crudservlet.model.Permissions.CLIENTS_EDIT;

public class ClientATMService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ClientATMService.class.getName());

    public ClientATMService() {
        logger.info("start");

        conn = MySQLConnection.getConnection();
    }

    public boolean create(ClientATM client, int userAccountId) throws Exception {
        logger.info("start");

        boolean result = false;

//        try {
        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, CLIENTS_CREATE))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    CLIENTS_CREATE.name()));
        new ClientService().validateExistsClient(client.getClientCode());

        conn.setAutoCommit(false);

        result = new ClientATMDAO().create(client);

        new AuditService().create(
                AuditOperType.CREATE_CLIENT,
                userAccountId,
                new java.util.Date(),
                String.format("Наименование клиента: %s \n" +
                                "Адрес: %s \n" +
                                "Тип банкомата: %s \n" +
                                "Дата закрытия: %s",
                        client.getClientName(),
                        client.getAddress(),
                        client.getAtmType().getDescrition(),
                        client.getCloseDate()),
                client.getClientCode()
        );

        conn.commit();
        conn.setAutoCommit(true);
/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public boolean edit(ClientATM client, int userAccountId) throws Exception {
        logger.info("start");

        boolean result = false;

//        try {
        UserAccount userAccount = new UserAccountDAO().getUserAccountById(userAccountId);
        if (!new PermissionService().isPermission(userAccountId, CLIENTS_EDIT))
            throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                    userAccount.getAccount(),
                    CLIENTS_CREATE.name()));

        ClientATM currentClient = new ClientATMService().getClientByCode(client.getClientCode());

        conn.setAutoCommit(false);

        result = new ClientATMDAO().edit(client);

        new AuditService().create(
                AuditOperType.EDIT_CLIENT,
                userAccountId,
                new java.util.Date(),
                String.format("Предыдущее состояние:" +
                                "Наименование клиента: %s \n" +
                                "Адрес: %s \n" +
                                "Тип банкомата: %s \n" +
                                "Дата закрытия: %s",
                        client.getClientName(),
                        client.getAddress(),
                        client.getAtmType().getDescrition(),
                        client.getCloseDate()) + "\n" +
                        String.format("Новое состояние:" +
                                        "Наименование клиента: %s \n" +
                                        "Адрес: %s \n" +
                                        "Тип банкомата: %s \n" +
                                        "Дата закрытия: %s",
                                currentClient.getClientName(),
                                currentClient.getAddress(),
                                currentClient.getAtmType().getDescrition(),
                                currentClient.getCloseDate()),
                client.getClientCode()
        );

        conn.commit();
        conn.setAutoCommit(true);
        /*} catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }

    public ClientATM getClientByCode(String clientCode) throws SQLException {

        logger.info("start");

        ClientATM client = null;
//        try {
        String sql = "SELECT " +
                "c.*, a.ATM_TYPE " +
                "FROM CLIENTS c " +
                "INNER JOIN ATMS a ON c.ID = a.CLIENT_ID " +
                "WHERE CLIENT_CODE = ? AND CLIENT_TYPE_ID = ?";

        PreparedStatement st = conn.prepareStatement(sql);
        st.setString(1, clientCode);
        st.setInt(2, 2);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            client = new ClientATM();
            client.setId(rs.getInt("id"));
            client.setClientCode(rs.getNString("client_code"));
            client.setClientName(rs.getNString("client_name"));
            client.setAtmType(ATMTypeType.valueOf(rs.getNString("atm_type")));
            client.setAddress(rs.getNString("address"));
            client.setCloseDate(rs.getDate("close_date") != null ?
                    new java.util.Date(rs.getDate("close_date").getTime()) : null);
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return client;
    }
}
