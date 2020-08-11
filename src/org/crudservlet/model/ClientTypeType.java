package org.crudservlet.model;

public enum ClientTypeType {
    DOPOFFICE(1, "ВСП"),
    SELFSERVICE(2, "УС");

    private final int Code;
    private final String Description;

    ClientTypeType(int code, String description) {
        Code = code;
        Description = description;
    }

    public int getCode() {
        return Code;
    }

    public String getDescription() {
        return Description;
    }
}
