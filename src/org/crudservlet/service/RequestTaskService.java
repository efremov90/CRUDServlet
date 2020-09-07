package org.crudservlet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crudservlet.dao.ReportDAO;
import org.crudservlet.dao.ReportTasksDAO;
import org.crudservlet.dao.RequestDAO;
import org.crudservlet.dao.RequestTasksDAO;
import org.crudservlet.dto.ReportRequestsConsolidatedDTO;
import org.crudservlet.dto.ReportRequestsDetailedDTO;
import org.crudservlet.model.FormatReportType;
import org.crudservlet.model.ReportType;
import org.crudservlet.reportbean.ReportRequestsDetailedBean;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RequestTaskService implements Runnable {

    private Logger logger = Logger.getLogger(RequestTaskService.class.getName());

    private int requestId;

    public RequestTaskService(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public void run() {
        logger.info("start");
        try {
            int taskId = new RequestTasksDAO().getTaskByRequest(requestId);
            new TaskService().start(taskId);
            new RequestService().cancel(
                    new RequestService().getRequestById(requestId).getRequestUUID(),
                    -1,
                    "Отменено автоматичеки"
            );
            new TaskService().finish(taskId);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                new TaskService().error(1,
                        e.getMessage() + "\n" +
                                e.toString() + "\n" +
                                Arrays.stream(e.getStackTrace()).map(x -> x.toString()).collect(Collectors.joining("\n"))
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        logger.info("finish");
    }
}
