package org.crudservlet.model;

public enum Permissions {

    HOME_VIEW,
    REQUESTS_VIEW,
    REQUESTS_VIEW_REQUEST,
    REQUESTS_CREATE,
    REQUESTS_CANCEL,
    CLIENTS_VIEW,
    CLIENTS_CREATE,
    CLIENTS_EDIT,
    REPORTS_VIEW,
    REPORT_GENERATE_REQUESTS_DETAILED,
    REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED;

    /*HOME_VIEW("HOME_VIEW"),
    REQUESTS_VIEW("REQUESTS_VIEW"),
    REQUESTS_CREATE("REQUESTS_CREATE"),
    REQUESTS_CANCEL("REQUESTS_CANCEL"),
    CLIENTS_VIEW("CLIENTS_VIEW"),
    CLIENT_CREATE("CLIENT_CREATE"),
    CLIENT_EDIT("CLIENT_EDIT");

    String Code;

    Permissions(String code) {
        Code = code;
    }*/
}
