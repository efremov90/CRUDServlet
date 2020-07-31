package org.crudservlet.model;

public enum ClientTypeType {
    SELFSERVICE("УС"),
    DOPOFFICE("ВСП");

    private final String Description;

    ClientTypeType(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
