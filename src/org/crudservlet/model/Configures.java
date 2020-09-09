package org.crudservlet.model;

import org.crudservlet.service.ConfigureService;

import java.sql.SQLException;

public enum Configures {

    /*Максимальное время неактивной сессии в секундах*/
    MAX_INACTIVE_SESSION_INTERVAL("maxInactiveSessionInterval", "45"),
    /*Период закрытия заявки в секундах*/
    CLOSE_REQUEST_INTERVAL("closeRequestInterval", "5"),
    /*Период отмены заявки в секундах*/
    CANCEL_REQUEST_INTERVAL("cancelRequestInterval", "5");

    private String name;
    private String value;

    Configures(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() throws SQLException {
        String value = ConfigureService.getConfigureValueByName(this.name);
        if (value != null) {
            return value;
        } else {
            return this.value;
        }
    }
}
