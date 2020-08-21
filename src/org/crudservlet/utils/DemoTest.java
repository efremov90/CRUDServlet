package org.crudservlet.utils;

import org.crudservlet.dao.AccountSessionDAO;
import org.crudservlet.model.ATMTypeType;
import org.crudservlet.model.ClientATM;
import org.crudservlet.model.Request;
import org.crudservlet.service.ClientATMService;
import org.crudservlet.service.RequestService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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

    }
}
