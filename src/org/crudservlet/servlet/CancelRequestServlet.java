package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.Request;
import org.crudservlet.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/cancelRequest"})
public class CancelRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 8123644727381918721L;

    private Logger logger = Logger.getLogger(CancelRequestServlet.class.getName());

    public CancelRequestServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("start");

        try {
            ObjectMapper mapper = new ObjectMapper();
            CancelRequestRequestDTO cancelRequestRequestDTO = mapper.readValue(req.getReader(), CancelRequestRequestDTO.class);
            Request request = new RequestService().getRequestById(cancelRequestRequestDTO.getRequestId());
            new RequestService().cancel(
                    request.getRequestUUID(),
                    new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId(),
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }
}
