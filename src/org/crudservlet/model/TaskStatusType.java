package org.crudservlet.model;

public enum TaskStatusType {

    CREATED("Создано"),
    STARTED("Начато"),
    FINISH("Завершено");

    private final String Description;

    TaskStatusType(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
