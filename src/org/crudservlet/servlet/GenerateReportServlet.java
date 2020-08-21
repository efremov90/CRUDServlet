package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.ATMTypeType;
import org.crudservlet.model.ClientATM;
import org.crudservlet.model.ClientDopoffice;
import org.crudservlet.service.ClientATMService;
import org.crudservlet.service.ClientDopofficeService;
import org.crudservlet.service.ErrorDTOService;
import org.crudservlet.service.ResultDTOService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/generateReport"})
public class GenerateReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1746775186141528314L;

    private Logger logger = Logger.getLogger(GenerateReportServlet.class.getName());

    public GenerateReportServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        logger.info("start");
        try {
            ObjectMapper mapper = new ObjectMapper();
            GenerateReportRequestDTO generateReportRequestDTO = mapper.readValue(req.getReader(), GenerateReportRequestDTO.class);
            if (generateReportRequestDTO.getReportRequestsDetailed() != null) {
                ReportRequestsDetailedDTO reportRequestsDetailedDTO = generateReportRequestDTO.getReportRequestsDetailed();
                ClientDopoffice client = new ClientDopoffice();
                /*client.setId(clientDTO.getId());
                client.setClientCode(clientDTO.getClientCode());
                client.setClientName(clientDTO.getClientName());
                client.setAddress(clientDTO.getAddress());
                client.setCloseDate((clientDTO.getCloseDate() != null && clientDTO.getCloseDate() != "") ?
                        Date.valueOf(clientDTO.getCloseDate()) : null);*/
                new ClientDopofficeService().create(client,
                        new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId());
                ResultDTOService.writer(resp, "0", null);
            } else if (generateReportRequestDTO.getReportRequestsConsolidated() != null) {
                /*ClientATMDTO clientDTO = createClientDTO.getSelfservice();
                ClientATM client = new ClientATM();
                client.setId(clientDTO.getId());
                client.setClientCode(clientDTO.getClientCode());
                client.setClientName(clientDTO.getClientName());
                client.setAtmType(ATMTypeType.valueOf(clientDTO.getATMType()));
                client.setAddress(clientDTO.getAddress());
                client.setCloseDate((clientDTO.getCloseDate() != null && clientDTO.getCloseDate() != "") ?
                        Date.valueOf(clientDTO.getCloseDate()) : null);
                new ClientATMService().create(client,
                        new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId());
                ResultDTOService.writer(resp, "0", null);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
