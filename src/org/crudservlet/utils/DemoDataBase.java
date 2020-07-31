package org.crudservlet.utils;

import org.crudservlet.dao.ConfigureDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.model.Configure;
import org.crudservlet.model.UserAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DemoDataBase {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        ConfigureDAO configureDAO = new ConfigureDAO();
        ArrayList<Configure> configures = configureDAO.getConfigureAll();
        System.out.println(configures.toString());
        UserAccount userAccount = userAccountDAO.getUserAccountById(1);
        System.out.println(userAccount);
        /*userAccount.setSessionId("BF495658D7CDA7933D3A154C1CFA9243");
        userAccount.setLastEventSessionDateTime(new Date());
        userAccountDAO.updateLastSession(userAccount);*/
        System.out.println(userAccountDAO.getUserAccountById(1));
    }
}
