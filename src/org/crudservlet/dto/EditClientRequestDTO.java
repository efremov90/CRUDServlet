package org.crudservlet.dto;

public class EditClientRequestDTO {

    private ClientDopofficeDTO Dopoffice;
    private ClientATMDTO Selfservice;

    public EditClientRequestDTO() {
    }

    public ClientDopofficeDTO getDopoffice() {
        return Dopoffice;
    }

    public void setDopoffice(ClientDopofficeDTO dopoffice) {
        Dopoffice = dopoffice;
    }

    public ClientATMDTO getSelfservice() {
        return Selfservice;
    }

    public void setSelfservice(ClientATMDTO selfservice) {
        Selfservice = selfservice;
    }
}
