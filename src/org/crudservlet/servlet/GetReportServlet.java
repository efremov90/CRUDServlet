package org.crudservlet.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dao.UserAccountDAO;
import org.crudservlet.dto.*;
import org.crudservlet.model.UserAccount;
import org.crudservlet.service.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
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

            GetReportResponseDTO getReportResponseDTO = new GetReportResponseDTO();

//            getReportResponseDTO.setReport(
//                    new ReportService().getContent(
//                            getReportRequestDTO.getReportId(),
//                            getReportRequestDTO.getFormat(),
//                            userAccount.getId()
//                    )
//            );

//            getReportResponseDTO.setReport(new SerialBlob(text.getBytes()));
//            getReportResponseDTO.setReport(new SerialBlob(text.getBytes()));

//            resp.setContentType("application/json;charset=UTF-8");
            resp.setContentType("application/vnd.oasis.opendocument.text;charset=UTF-8");
            resp.setHeader("Content-Disposition", "attachment; filename=\"file.txt\"");
            resp.setContentLength(text.getBytes().length);
//            PrintWriter out = resp.getWriter();
//            out.println(mapper.writeValueAsString(getReportResponseDTO));
//            out.close();
            resp.getOutputStream().write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            ErrorDTOService.writer(resp, e);
        }
        return;
    }

}
