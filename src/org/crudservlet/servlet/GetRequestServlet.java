package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.Client;
import org.crudservlet.model.Request;
import org.crudservlet.model.UserAccount;
import org.crudservlet.service.ClientService;
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
import java.util.logging.Logger;

import static org.crudservlet.model.Permissions.REQUESTS_CREATE;
import static org.crudservlet.model.Permissions.REQUESTS_VIEW_REQUEST;

@WebServlet(urlPatterns = {"/getRequest"})
public class GetRequestServlet extends HttpServlet {

    private static final long serialVersionUID = -1576522104507426405L;

    private Logger logger = Logger.getLogger(GetRequestServlet.class.getName());

    public GetRequestServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("start");

        try {
            ObjectMapper mapper = new ObjectMapper();
            GetRequestRequestDTO getRequestRequestDTO = mapper.readValue(req.getReader(), GetRequestRequestDTO.class);

            UserAccount userAccount = new UserAccountDAO().getUserAccountBySessionId(req.getRequestedSessionId());
            if (!new PermissionService().isPermission(userAccount.getId(), REQUESTS_VIEW_REQUEST))
                throw new Exception(String.format("У пользователя %s отсутствует разрешение %s.",
                        userAccount.getAccount(),
                        REQUESTS_VIEW_REQUEST.name()));

            Request request = new RequestService().getRequestById(getRequestRequestDTO.getId());
            UserAccount lastUserAccount =
                    new UserAccountDAO().getUserAccountById(request.getLastUserAccountIdChangeRequestStatus());
            Client client = new ClientService().getClient(request.getClientCode());
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setRequestId(request.getId());
            requestDTO.setRequestUUID(request.getRequestUUID());
            requestDTO.setCreateDate(new Date(request.getCreateDate().getTime()).toString());
            requestDTO.setCreateDatetime(new Date(request.getCreateDate().getTime()).toString());
            requestDTO.setClientCode(request.getClientCode());
            requestDTO.setClientName(client.getClientName());
            requestDTO.setClientType(client.getClientType().name());
            requestDTO.setClientTypeDescription(client.getClientType().getDescription());
            requestDTO.setComment(request.getComment());
            requestDTO.setRequestStatus(request.getRequestStatus().name());
            requestDTO.setRequestStatusDescription(request.getRequestStatus().getDescription());
            requestDTO.setLastDateTimeChangeRequestStatus(request.getRequestStatus().getDescription());
            requestDTO.setLastUserAccountIdChangeRequestStatus(request.getLastUserAccountIdChangeRequestStatus());
            requestDTO.setLastUserAccountNameChangeRequestStatus(lastUserAccount.getFullName());
            GetRequestResponseDTO getRequestResponseDTO = new GetRequestResponseDTO();
            getRequestResponseDTO.setRequest(requestDTO);
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(mapper.writeValueAsString(getRequestResponseDTO));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
