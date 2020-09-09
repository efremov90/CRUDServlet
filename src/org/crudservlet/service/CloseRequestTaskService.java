package org.crudservlet.service;

import org.crudservlet.model.Request;
import org.crudservlet.model.RequestStatusType;
import org.crudservlet.model.Task;
import org.crudservlet.model.TaskStatusType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.crudservlet.model.TaskType.CLOSE_REQUEST;

public class CloseRequestTaskService implements Runnable {

    private Logger logger = Logger.getLogger(CloseRequestTaskService.class.getName());

    @Override
    public void run() {
        logger.info("start");
        try {
            Task task = new Task();
            task.setType(CLOSE_REQUEST);
            task.setCreateDateTime(new java.util.Date());
            task.setCreateDate(task.getCreateDateTime());
            task.setPlannedStartDateTime(task.getCreateDateTime());
            task.setStatus(TaskStatusType.CREATED);
            task.setUserAccountId(-1);

            Integer taskId = new TaskService().create(task, -1, 0);

            new TaskService().start(taskId);

            ArrayList<Request> requests = new RequestService().getRequests(
                    new java.sql.Date(new Date().getTime()),
                    new java.sql.Date(new Date().getTime()),
                    null,
                    RequestStatusType.CANCELED
            );

            final int[] count_requests = {0};

            requests.stream()
                    .forEach(x -> {
                        try {
                            new RequestService().close(
                                    new RequestService().getRequestById(x.getId()).getRequestUUID(),
                                    -1
                            );
                            count_requests[0] = count_requests[0] + 1;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });


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
