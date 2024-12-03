package com.ximert.electronicMoney.models;

import java.util.Map;

public class DataClients {

    private Map<String, Client> clients;

    public DataClients(Map<String, Client> clients) {
        this.clients = clients;
    }

    public Map<String, Client> getClients() {
        return clients;
    }
}
