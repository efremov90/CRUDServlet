package org.crudservlet.model;

public enum RequestStatusType {
    CREATED("Создана"),
    CANCELED("Отменена"),
    CLOSE("Закрыта");

    private final String Description;

    RequestStatusType(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
