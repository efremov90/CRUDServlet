package org.crudservlet.filter;

import org.crudservlet.service.AuthHeader;
import org.crudservlet.service.SecuritySevice;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    public static final String AUTHENTICATION_HEADER = "Authorization";
    private Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        String sessionId = request.getRequestedSessionId();
        AuthHeader authHeader = new AuthHeader(request.getHeader(AUTHENTICATION_HEADER));

        logger.info("start");
        logger.info("getRequestURL:" + request.getRequestURL());
        logger.info("getRequestURI:" + request.getRequestURI());
        logger.info("getContextPath:" + request.getContextPath());
        logger.info("getServletPath:" + request.getServletPath());
        logger.info("getRemoteAddr:" + request.getRemoteAddr());
        logger.info("getRemoteHost:" + request.getRemoteHost());
        logger.info("getRemotePort:" + request.getRemotePort());
        logger.info("getServerName:" + request.getServerName());
        logger.info("getLocalName:" + request.getLocalName());
        logger.info("getServerPort:" + request.getServerPort());
        logger.info("host:" + request.getHeader("host"));
        logger.info("getRequestedSessionId:" + request.getRequestedSessionId());
        logger.info("getAuthType:" + request.getAuthType());
        logger.info("getRemoteUser:" + request.getRemoteUser());
        logger.info("getUserPrincipal:" + request.getUserPrincipal());
        //Arrays.stream(request.getCookies().clone()).forEach(x->logger.info("getCookies:"+x.getName()+";"+x.getValue
        // ()));
        request.getHeaderNames().asIterator().forEachRemaining(x -> logger.info("getHeaderNames:" + x + ";" + request.getHeader(x)));
        logger.info(authHeader.toString());

        try {
            if (servletPath.indexOf(".") > -1) {
                logger.info("servletPath.indexOf(\".\")>-1");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else if (servletPath.equals("/login")) {
                logger.info("servletPath.equals(\"/login\")");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else if (sessionId != null
                    /*&& authHeader.getAccount()!=null*/
                    && new SecuritySevice().isActiveSession(/*authHeader.getAccount(),*/
                    sessionId)
            ) {
                logger.info("isActiveSession=true");
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.info("isActiveSession=false");
                //response.sendRedirect(request.getContextPath()+"/login");
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/view/login.html");
                response.setStatus(403);
                dispatcher.forward(request, response);
                return;
            }
        } catch (Exception e) {
            /*logger.throwing(SecurityFilter.class.getName(),
                    SecurityFilter.class.getEnclosingMethod().getName(),
                    e);*/
            e.printStackTrace();
        }
    }
}
