package org.crudservlet.service;

import org.crudservlet.dao.AuditDAO;
import org.crudservlet.dao.ClientATMDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.model.Audit;
import org.crudservlet.model.AuditOperType;
import org.crudservlet.model.ClientATM;
import org.crudservlet.model.UserAccount;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.CLIENTS_CREATE;

public class AuditService {

    private Connection conn;
    private Logger logger = Logger.getLogger(ClientATMService.class.getName());

    private String getDescription(AuditOperType type, Object[] args) {
        return new MessageFormat(type.getDescription()).format(args);
    }

    public boolean create(AuditOperType type, int userAccountId, Date eventDatetime, String content, Object... args) throws Exception {
        logger.info("start");

        boolean result = false;

//        try {
        Audit audit = new Audit();
        audit.setAuditOperId(new AuditOperService().getAuditOperByCode(type).getId());
        audit.setUserAccountId(userAccountId);
        audit.setEventDateTime(eventDatetime);
        audit.setDescription(getDescription(type, args));
        audit.setContent(content);
        result = new AuditDAO().create(audit);
/*        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }*/

        return result;
    }
}
