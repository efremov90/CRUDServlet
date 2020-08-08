package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.Request;
import org.crudservlet.service.ErrorDTOService;
import org.crudservlet.service.RequestService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/createRequest"})
public class CreateRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 3948755746919698464L;

    private Logger logger = Logger.getLogger(CreateRequestServlet.class.getName());

    public CreateRequestServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        logger.info("start");
        try {
            ObjectMapper mapper = new ObjectMapper();
            CreateRequestRequestDTO createRequestDTO = mapper.readValue(req.getReader(), CreateRequestRequestDTO.class);

            RequestDTO requestDTO = createRequestDTO.getRequest();
            Request request = new Request();
            request.setCreateDate(new java.util.Date());
            request.setCreateDateTime(new java.util.Date());
            request.setClientCode(requestDTO.getClientCode());
            request.setComment(requestDTO.getComment());
            request.setLastUserAccountIdChangeRequestStatus(new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId());
            new RequestService().create(request,
                    new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId());

        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
