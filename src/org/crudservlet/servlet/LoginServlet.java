package org.crudservlet.servlet;

import org.crudservlet.service.AuthHeader;
import org.crudservlet.service.ErrorDTOService;
import org.crudservlet.service.SecurityContext;
import org.crudservlet.service.SecuritySevice;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;

import static org.crudservlet.filter.SecurityFilter.AUTHENTICATION_HEADER;
import static org.crudservlet.model.SecurityContextStatusCodeType.S00000;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 8044646618257579732L;

    private Logger logger = Logger.getLogger(LoginServlet.class.getName());

    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("start");
        /*logger.info("getRequestURL:"+request.getRequestURL());
        logger.info("getRequestURI:"+request.getRequestURI());
        logger.info("getContextPath:"+request.getContextPath());
        logger.info("getServletPath:"+request.getServletPath());*/

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/view/login.html");

        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        logger.info("start");
        /*logger.info("getRequestURL:"+request.getRequestURL());
        logger.info("getRequestURI:"+request.getRequestURI());
        logger.info("getContextPath:"+request.getContextPath());
        logger.info("getServletPath:"+request.getServletPath());*/

        try {
            AuthHeader authHeader = new AuthHeader(req.getHeader(AUTHENTICATION_HEADER));
            SecurityContext securityContext = new SecuritySevice().login(authHeader.getAccount(),
                    authHeader.getPassword(), req);
            Cookie cookieAuthStatusCode = new Cookie("AUTH_STATUS_CODE", securityContext.getStatusCode().toString());
            cookieAuthStatusCode.setMaxAge(-1);
            cookieAuthStatusCode.setHttpOnly(false);
            cookieAuthStatusCode.setDomain(req.getServerName());
            cookieAuthStatusCode.setPath("/CRUDServlet");
            resp.addCookie(cookieAuthStatusCode);
            if (securityContext.getStatusCode().equals(S00000)) {
                logger.info("true");
                Cookie cookieSessionId = new Cookie("JSESSIONID", securityContext.getSessionId());
                cookieSessionId.setMaxAge(-1);
                cookieSessionId.setHttpOnly(false);
                cookieSessionId.setDomain(req.getServerName());
                cookieSessionId.setPath("/CRUDServlet");
                resp.addCookie(cookieSessionId);
                resp.addHeader(AUTHENTICATION_HEADER, req.getHeader(AUTHENTICATION_HEADER));
                resp.setContentType("text/plain;charset=UTF-8");
                return;
            } else {
                logger.info("false");
                resp.setStatus(401);
                doGet(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}