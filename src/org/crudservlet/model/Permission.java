package org.crudservlet.model;

public class Permission {

    private Permissions Code;

    public Permission() {
    }

    public Permission(Permissions code) {
        Code = code;
    }

    public Permissions getCode() {
        return Code;
    }

    public void setCode(Permissions code) {
        Code = code;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "Code=" + Code +
                '}';
    }
}
