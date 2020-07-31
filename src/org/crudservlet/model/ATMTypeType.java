package org.crudservlet.model;

public enum ATMTypeType {

    ATM("Банкомат"),
    TERMINAL("Терминал");

    private final String Descrition;

    ATMTypeType(String descrition) {
        Descrition = descrition;
    }

    public String getDescrition() {
        return Descrition;
    }
}
