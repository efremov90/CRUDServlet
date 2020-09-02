package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.FormatReportType;
import org.crudservlet.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/generateReport"})
public class GenerateReportServlet extends HttpServlet {

    private static final long serialVersionUID = 3532825651179359130L;

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
            GenerateReportResponseDTO generateReportResponseDTO = new GenerateReportResponseDTO();
            if (generateReportRequestDTO.getReportRequestsDetailed() != null) {
                ReportRequestsDetailedDTO reportRequestsDetailedDTO = generateReportRequestDTO.getReportRequestsDetailed();
                Integer reportId =
                        new ReportRequestsDetailedService().create(
                                reportRequestsDetailedDTO,
                                FormatReportType.valueOf(generateReportRequestDTO.getFormatType()),
                                new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId()
                        );
                generateReportResponseDTO.setReportId(reportId);
            } else if (generateReportRequestDTO.getReportRequestsConsolidated() != null) {
                ReportRequestsConsolidatedDTO reportRequestsConsolidatedDTO = generateReportRequestDTO.getReportRequestsConsolidated();
                Integer reportId =
                        new ReportRequestsConsolidatedService().create(
                                reportRequestsConsolidatedDTO,
                                FormatReportType.valueOf(generateReportRequestDTO.getFormatType()),
                                new AccountSessionDAO().getAccountSessionBySessionId(req.getRequestedSessionId()).getUserAccountId()
                        );
                generateReportResponseDTO.setReportId(reportId);
            }
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println(mapper.writeValueAsString(generateReportResponseDTO));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
