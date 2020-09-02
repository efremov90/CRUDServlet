package org.crudservlet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crudservlet.dao.ReportDAO;
import org.crudservlet.dto.ReportRequestsConsolidatedDTO;
import org.crudservlet.dto.ReportRequestsDetailedDTO;
import org.crudservlet.model.FormatReportType;
import org.crudservlet.model.ReportType;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ReportTaskService implements Runnable {

    private Logger logger = Logger.getLogger(ReportTaskService.class.getName());

    private int reportId;

    public ReportTaskService(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public void run() {
        logger.info("start");
        try {
            ReportType reportType = new ReportDAO().getType(reportId);
            FormatReportType formatType = new ReportDAO().getFormat(reportId);
            String parametersDTO = new ReportDAO().getParameters(reportId);
            ObjectMapper mapper = new ObjectMapper();
            Blob blob = null;
            Map<String, Object> parametersJR = new HashMap<String, Object>();
            Collection<?> bean = null;

            new ReportService().start(reportId);
            switch (reportType) {
                case REPORT_REQUESTS_DETAILED: {
                    ReportRequestsDetailedDTO parameters = mapper.readValue(parametersDTO,
                            ReportRequestsDetailedDTO.class);
                    bean = new ReportRequestsDetailedService().getData(
                            (parameters.getFromCreateDate() != null && parameters.getFromCreateDate() != "") ?
                                    Date.valueOf(parameters.getFromCreateDate()) : null,
                            (parameters.getToCreateDate() != null && parameters.getToCreateDate() != "") ?
                                    Date.valueOf(parameters.getToCreateDate()) : null,
                            parameters.getClientCode()
                    );
                    parametersJR = new ReportRequestsDetailedService().getParametersJR(parameters);
                }
                break;
                case REPORT_REQUESTS_CONSOLIDATED: {
                    ReportRequestsConsolidatedDTO parameters = mapper.readValue(parametersDTO,
                            ReportRequestsConsolidatedDTO.class);
                    bean = new ReportRequestsConsolidatedService().getData(
                            (parameters.getFromCreateDate() != null && parameters.getFromCreateDate() != "") ?
                                    Date.valueOf(parameters.getFromCreateDate()) : null,
                            (parameters.getToCreateDate() != null && parameters.getToCreateDate() != "") ?
                                    Date.valueOf(parameters.getToCreateDate()) : null
                    );
                    parametersJR = new ReportRequestsConsolidatedService().getParametersJR(parameters);
                }
                break;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = new ReportService().generate(
                    ReportType.REPORT_REQUESTS_DETAILED,
                    formatType,
                    parametersJR,
                    new JRBeanCollectionDataSource(
                            bean
                    ));
            new ReportService().finish(reportId, baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("finish");
    }
}
