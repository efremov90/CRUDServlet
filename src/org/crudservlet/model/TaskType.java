package org.crudservlet.model;

public enum TaskType {

    REPORT("Формирование отчета"),
    CLOSE_REQUEST("Закрытие заявки"),
    CANCEL_REQUEST("Отмена заявки");

    private String Description;

    TaskType(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
