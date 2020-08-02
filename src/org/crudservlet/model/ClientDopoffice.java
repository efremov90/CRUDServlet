package org.crudservlet.model;

import java.util.Date;

import static org.crudservlet.model.ClientTypeType.DOPOFFICE;

public class ClientDopoffice extends Client {

    public ClientDopoffice() {
        setClientType(DOPOFFICE);
    }

}
