package org.crudservlet.model;

public class AuditOper {
    private int Id;
    private String OperCode;
    private String Description;

    public AuditOper() {
    }

    public AuditOper(int id, String operCode, String description) {
        Id = id;
        OperCode = operCode;
        Description = description;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOperCode() {
        return OperCode;
    }

    public void setOperCode(String operCode) {
        OperCode = operCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
