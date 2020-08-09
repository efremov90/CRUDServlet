package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.UserAccount;
import org.crudservlet.service.ErrorDTOService;
import org.crudservlet.service.PermissionService;
import org.crudservlet.service.RequestService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.crudservlet.model.Permissions.REQUESTS_VIEW;

@WebServlet(urlPatterns = {"/getRequestAudits"})
public class GetRequestAuditsServlet extends HttpServlet {

    private static final long serialVersionUID = 1298529002996149088L;

    private Logger logger = Logger.getLogger(GetRequestAuditsServlet.class.getName());

    public GetRequestAuditsServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("start");

        try {
            ObjectMapper mapper = new ObjectMapper();
            GetRequestAuditsRequestDTO getRequestAuditsRequestDTO = mapper.readValue(req.getReader(),
                    GetRequestAuditsRequestDTO.class);

            UserAccount userAccount = new UserAccountDAO().getUserAccountBySessionId(req.getRequestedSessionId());
            if (!new PermissionService().isPermission(userAccount.getId(), REQUESTS_VIEW))
                throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                        userAccount.getAccount(),
                        REQUESTS_VIEW.name()));

            ArrayList<AuditDTO> audits = new RequestService().getAudits(getRequestAuditsRequestDTO.getRequestId()).stream()
                    .map(x -> {
                        AuditDTO auditDTO = new AuditDTO();
                        auditDTO.setId(x.getId());
                        auditDTO.setEvent(x.getAuditOperType().getName());
                        auditDTO.setUser(x.getUserName());
                        auditDTO.setEventDateTime(new Date(x.getEventDateTime().getTime()).toString());
                        auditDTO.setDescription(x.getDescription());
                        auditDTO.setContent(x.getContent());
                                return auditDTO;
                            }
                    ).collect(Collectors.toCollection(() -> new ArrayList<AuditDTO>()));
            AuditsResponseDTO auditsResponseDTO = new AuditsResponseDTO();
            auditsResponseDTO.setAudits(audits);
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(mapper.writeValueAsString(auditsResponseDTO));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
