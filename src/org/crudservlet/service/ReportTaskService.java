package org.crudservlet.service;

import org.crudservlet.dao.ReportDAO;
import org.crudservlet.model.ReportType;
import org.crudservlet.servlet.CheckReportServlet;

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
            new ReportService().start(reportId);
            switch (reportType) {
                case REPORT_REQUESTS_DETAILED:
//                    new ReportRequestsDetailedService().generate();
                    break;
                case REPORT_REQUESTS_CONSOLIDATED:
                    new ReportRequestsConsolidatedService().generate();
                    break;
            }
            new ReportService().finish(reportId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("finish");
    }
}
