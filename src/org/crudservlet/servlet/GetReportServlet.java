package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crudservlet.dao.ReportDAO;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.FormatReportType;
import org.crudservlet.model.ReportType;
import org.crudservlet.model.UserAccount;
import org.crudservlet.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/getReport"})
public class GetReportServlet extends HttpServlet {

    private static final long serialVersionUID = 5199753893008181253L;

    private Logger logger = Logger.getLogger(GetReportServlet.class.getName());

    public GetReportServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String text = "Test";

        logger.info("start");

        try {
            ObjectMapper mapper = new ObjectMapper();
            GetReportRequestDTO getReportRequestDTO = mapper.readValue(req.getReader(), GetReportRequestDTO.class);

            UserAccount userAccount = new UserAccountDAO().getUserAccountBySessionId(req.getRequestedSessionId());

            ReportType reportType = new ReportDAO().getType(getReportRequestDTO.getReportId());
            FormatReportType formatReportType = new ReportDAO().getFormat(getReportRequestDTO.getReportId());

//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new ReportService().getContent(
                    getReportRequestDTO.getReportId(),
                    userAccount.getId()
            );

            resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String fileName = URLEncoder.encode(
                    reportType.getDescription() + " ",
                    "UTF-8"
            ) + (new Timestamp(new Date().getTime()).toString()) + "." + formatReportType.name();
            resp.setHeader("Content-Disposition",
                    "attachment; filename=\"" + fileName + "\"");
            resp.setContentLength(b.length);
            resp.getOutputStream().write(b);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
