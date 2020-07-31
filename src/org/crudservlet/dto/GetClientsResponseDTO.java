package org.crudservlet.dto;

import java.util.ArrayList;

public class GetClientsResponseDTO {

    private ArrayList<ClientDTO> Clients;

    public GetClientsResponseDTO() {
    }

    public GetClientsResponseDTO(ArrayList<ClientDTO> clients) {
        Clients = clients;
    }

    public ArrayList<ClientDTO> getClients() {
        return Clients;
    }
}
