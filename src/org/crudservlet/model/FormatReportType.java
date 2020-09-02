package org.crudservlet.model;

public enum FormatReportType {

    XLS("XLS"),
    XLSX("XLSX");

    private String Description;

    FormatReportType(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }
}
