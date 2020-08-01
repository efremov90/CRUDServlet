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
        String uuid = UUID.randomUUID().toString();
        request.setRequestUUID(uuid);
        request.setCreateDate(Date.valueOf(LocalDate.now()));
        request.setCreateDateTime(new java.util.Date());
        request.setClientCode("1001");
        request.setComment("Комментарий");
        new RequestService().create(request, 2);
        new RequestService().cancel(uuid, 2, "Причина отмены");
    }
}
