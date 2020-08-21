package org.crudservlet.model;

public enum TaskType {

    REPORT("Формирование отчета");

    private String Description;

    TaskType(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
