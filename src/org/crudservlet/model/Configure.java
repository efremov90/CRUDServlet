package org.crudservlet.model;

public class Configure {
    private String name;
    private String value;

    public Configure() {
    }

    public Configure(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Configure{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
