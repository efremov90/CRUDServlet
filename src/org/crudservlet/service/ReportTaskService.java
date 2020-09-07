package org.crudservlet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crudservlet.dao.ReportDAO;
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
                    if (bean.isEmpty()) bean.add(null);
                    parametersJR = new ReportRequestsDetailedService().getParametersJR(parameters);
                }
                break;
                case REPORT_REQUESTS_CONSOLIDATED: {
                    ReportRequestsConsolidatedDTO parameters = mapper.readValue(parametersDTO,
                            ReportRequestsConsolidatedDTO.class);
                    ArrayList<ReportRequestsDetailedBean> requests = new ReportRequestsConsolidatedService().getData(
                            (parameters.getFromCreateDate() != null && parameters.getFromCreateDate() != "") ?
                                    Date.valueOf(parameters.getFromCreateDate()) : null,
                            (parameters.getToCreateDate() != null && parameters.getToCreateDate() != "") ?
                                    Date.valueOf(parameters.getToCreateDate()) : null
                    );
                    Integer all_count = requests.size();
                    if (!requests.isEmpty()) {
                        Map<Map<String, Object>, Long> countByGenderAndLastName = requests.stream()
                                .collect(Collectors.groupingBy(r -> {
                                            Map<String, Object> x = new HashMap<>();
                                            x.put("clientCode", r.getClientCode());
                                            x.put("createDate", r.getCreateDate());
                                            return x;
                                        },
                                        Collectors.counting()));
                        countByGenderAndLastName.entrySet().stream()
                                .forEach(x -> {
                                    ReportRequestsDetailedBean reportRequestsDetailedBean = new ReportRequestsDetailedBean();
                                    reportRequestsDetailedBean.setRankSorted(1);
                                    reportRequestsDetailedBean.setClientCode(x.getKey().get("clientCode").toString());
                                    reportRequestsDetailedBean.setCreateDate(x.getKey().get("createDate").toString());
                                    reportRequestsDetailedBean.setStatus("Итого:");
                                    reportRequestsDetailedBean.setComment(x.getValue().toString());
                                    requests.add(reportRequestsDetailedBean);
                                });
                        Collections.sort(requests,
                                Comparator.comparing(ReportRequestsDetailedBean::getClientCode)
                                        .thenComparing(ReportRequestsDetailedBean::getCreateDate)
                                        .thenComparing(ReportRequestsDetailedBean::getRankSorted)
                        );
                        requests.stream()
                                .filter(x -> ((Integer) x.getRankSorted()).equals(1))
                                .map(x -> {
                                    x.setClientCode("");
                                    x.setCreateDate("");
                                    return x;
                                })
                                .collect(Collectors.toList());
                    }
                    ReportRequestsDetailedBean reportRequestsDetailedBean = new ReportRequestsDetailedBean();
                    reportRequestsDetailedBean.setRankSorted(2);
                    reportRequestsDetailedBean.setStatus("Всего:");
                    reportRequestsDetailedBean.setComment(all_count.toString());
                    requests.add(reportRequestsDetailedBean);
                    bean = requests;
                    parametersJR = new ReportRequestsConsolidatedService().getParametersJR(parameters);
                    break;
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = new ReportService().generate(
                    reportType,
                    formatType,
                    parametersJR,
                    new JRBeanCollectionDataSource(
                            bean
                    ));
            new ReportService().finish(reportId, baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            try {
                new ReportService().error(reportId,
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
