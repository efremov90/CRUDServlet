package org.crudservlet.utils;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.model.ATMTypeType;
import org.crudservlet.model.ClientATM;
import org.crudservlet.model.ReportType;
import org.crudservlet.model.Request;
import org.crudservlet.service.ClientATMService;
import org.crudservlet.service.ReportRequestsDetailedService;
import org.crudservlet.service.ReportService;
import org.crudservlet.service.RequestService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DemoTest {
    public static void main(String[] args) throws Exception {

        //Заявка
        /*Request request = new Request();
        String uuid = UUID.randomUUID().toString();
        request.setRequestUUID(uuid);
        request.setCreateDate(Date.valueOf(LocalDate.now()));
        request.setCreateDateTime(new java.util.Date());
        request.setClientCode("1001");
        request.setComment("Комментарий");
        new RequestService().create(request, 2);
        new RequestService().cancel(uuid, 2, "Причина отмены");*/

        //Клиент
        /*ClientATM client = new ClientATM();
        client.setClientCode("2005");
        client.setClientName("N 2005");
        client.setAtmType(ATMTypeType.valueOf("ATM"));
        client.setAddress("A 2005");
        client.setCloseDate(null);
        new ClientATMService().edit(client, 2);*/

//        Request r = ;

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fromCreateDate", Date.valueOf("2020-08-01").toString());
        parameters.put("toCreateDate", Date.valueOf("2020-09-01").toString());

        new ReportService().generate(
                ReportType.REPORT_REQUESTS_DETAILED,
                parameters,
                new JRBeanCollectionDataSource(
                        new ReportRequestsDetailedService().getData(
                                Date.valueOf("2020-08-01"),
                                Date.valueOf("2020-09-01"),
                                "1001"
                        )
                ));
    }
}
