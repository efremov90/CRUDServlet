package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.ReportDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.ReportStatusType;
import org.crudservlet.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/checkReport"})
public class CheckReportServlet extends HttpServlet {

    private static final long serialVersionUID = 7340943866323974159L;

    private Logger logger = Logger.getLogger(CheckReportServlet.class.getName());

    public CheckReportServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("start");

        try {
            ObjectMapper mapper = new ObjectMapper();
            CheckReportRequestDTO checkReportRequestDTO = mapper.readValue(req.getReader(), CheckReportRequestDTO.class);

//            ReportStatusType reportStatus = new ReportDAO().getReportStatus(checkReportRequestDTO.getReportId());
            ReportStatusType reportStatus;
            if (Math.random() > 0.5) {
                reportStatus = ReportStatusType.CREATED;
            } else {
                reportStatus = ReportStatusType.FINISH;
            }

            CheckReportResponseDTO checkReportResponseDTO = new CheckReportResponseDTO();
            checkReportResponseDTO.setStatus(reportStatus.name());

            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(mapper.writeValueAsString(checkReportResponseDTO));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
