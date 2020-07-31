package org.crudservlet.model;

public enum RequestStatusType {
    CREATED("Создана"),
    CANCELED("Отменена");

    private final String Description;

    RequestStatusType(String description) {
        Description = description;
    }
}
