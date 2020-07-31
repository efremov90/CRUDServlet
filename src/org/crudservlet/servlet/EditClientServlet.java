package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.dao.ClientATMDAO;
import org.crudservlet.dao.ClientDopofficeDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.ATMTypeType;
import org.crudservlet.model.ClientATM;
import org.crudservlet.model.ClientDopoffice;
import org.crudservlet.service.ClientATMService;
import org.crudservlet.service.ClientDopofficeService;
import org.crudservlet.service.ErrorDTOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/editClient"})
public class EditClientServlet extends HttpServlet {

    private static final long serialVersionUID = 6011853190168045751L;

    private Logger logger = Logger.getLogger(EditClientServlet.class.getName());

    public EditClientServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("start");
        try {
            ObjectMapper mapper = new ObjectMapper();
            EditClientRequestDTO editClientDTO = mapper.readValue(req.getReader(), EditClientRequestDTO.class);
            if (editClientDTO.getDopoffice() != null) {
                ClientDopofficeDTO client = editClientDTO.getDopoffice();
                new ClientDopofficeService().edit(new ClientDopoffice(
                                client.getId(),
                                client.getClientCode(),
                                client.getClientName(),
                                client.getAddress(),
                                (client.getCloseDate() != null && client.getCloseDate() != "") ?
                                        Date.valueOf(client.getCloseDate()) : null
                        ),
                        new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId());
            } else if (editClientDTO.getSelfservice() != null) {
                ClientATMDTO client = editClientDTO.getSelfservice();
                new ClientATMService().edit(new ClientATM(
                                client.getId(),
                                client.getClientCode(),
                                client.getClientName(),
                                ATMTypeType.valueOf(client.getATMType()),
                                client.getAddress(),
                                (client.getCloseDate() != null && client.getCloseDate() != "") ?
                                        Date.valueOf(client.getCloseDate()) : null
                        ),
                        new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
