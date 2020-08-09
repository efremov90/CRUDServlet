package org.crudservlet.model;

public class AuditOper {
    private int Id;
    private String Type;
    private String Description;

    public AuditOper() {
    }

    public AuditOper(int id, String type, String description) {
        Id = id;
        Type = type;
        Description = description;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
