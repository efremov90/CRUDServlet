package org.crudservlet.utils;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.integration.requests.CancelRequestRqType;
import org.crudservlet.model.ATMTypeType;
import org.crudservlet.model.ClientATM;
import org.crudservlet.model.ReportType;
import org.crudservlet.model.Request;
import org.crudservlet.service.ClientATMService;
import org.crudservlet.service.ReportRequestsDetailedService;
import org.crudservlet.service.ReportService;
import org.crudservlet.service.RequestService;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
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

        /*Map<String, Object> parameters = new HashMap<String, Object>();
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
                ));*/

        CancelRequestRqType cancelRequestRq = new CancelRequestRqType();
        cancelRequestRq.setRequestUUID(UUID.randomUUID().toString());
        cancelRequestRq.setComment("Comment");

        JAXBContext context = JAXBContext.newInstance(CancelRequestRqType.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        m.marshal(cancelRequestRq, baos);

        System.out.println(baos.toString());

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        Unmarshaller um = context.createUnmarshaller();
        CancelRequestRqType cancelRequestRqRead = (CancelRequestRqType) um.unmarshal(bais);
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns2:CancelRequestRq xmlns:ns2=\"http://ru.crud.requests\">\n" +
                "    <RequestUUID>d6faad1f-bdb1-4738-a548-90fc18d745201</RequestUUID>\n" +
                "    <Comment>Comment</Comment>\n" +
                "</ns2:CancelRequestRq>";

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("src\\org\\crudservlet\\integration\\requests\\schema\\CRUDRequests" +
                ".xsd"));

        JAXBContext jc = JAXBContext.newInstance(CancelRequestRqType.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schema);
        cancelRequestRqRead = (CancelRequestRqType) unmarshaller.unmarshal(new ByteArrayInputStream(str.getBytes()));
        System.out.println("");
    }
}
