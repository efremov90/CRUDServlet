package org.crudservlet.utils;

import org.crudservlet.model.Request;
import org.crudservlet.service.RequestService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

public class DemoTest {
    public static void main(String[] args) throws Exception {
        Request request = new Request();
        request.setRequestUUID(UUID.randomUUID().toString());
        request.setCreateDate(Date.valueOf(LocalDate.now()));
        request.setCreateDateTime(new java.util.Date());
        request.setClientCode("1001");
        request.setComment("Комментарий");
        new RequestService().create(request, 2);
    }
}
