package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.PermissionDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dto.GetPermissionsResponseDTO;
import org.crudservlet.model.UserAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/getPermissions"})
public class GetPermissionsServlet extends HttpServlet {

    private static final long serialVersionUID = 6772942449328261368L;

    private Logger logger = Logger.getLogger(GetPermissionsServlet.class.getName());

    public GetPermissionsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        logger.info("start");

        try {
            UserAccount userAccount = new UserAccountDAO().getUserAccountBySessionId(req.getRequestedSessionId());
            Set<String> permissions = new PermissionDAO().getPermissionsByUserAccountId(userAccount.getId()).stream()
                    .map(x -> x.getCode().name())
                    .collect(Collectors.toSet());
            GetPermissionsResponseDTO getPermissionsResponseDTO = new GetPermissionsResponseDTO(
                    permissions
            );
            ObjectMapper mapper = new ObjectMapper();
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(mapper.writeValueAsString(getPermissionsResponseDTO));
            out.close();
            return;
        } catch (Exception e) {
            /*logger.throwing(GetPermissionsServlet.class.getName(),
                    GetPermissionsServlet.class.getEnclosingMethod().getName(),
                    e);*/
            e.printStackTrace();
        }
        return;
    }
}
