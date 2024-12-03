package com.ximert.electronicMoney.models;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Clients {

    private static DataClients clients;
    private static final String path = "data\\Clients.txt";
    private static Map<String, Integer> map;

    private Clients() {}

    public static void initialization() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            clients = new Gson().fromJson(br, DataClients.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map = new HashMap<>();
        for (String login: clients.getClients().keySet()) {
            map.put(login, 3);
        }
    }

    public static void add(String login, Client client) {
        clients.getClients().put(login, client);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            new Gson().toJson(clients, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Client get(String login) {
        return clients.getClients().get(login);
    }

    public static Integer getCount(String login) {
        return map.get(login);
    }

    public static void inc(String login) {
        map.put(login, map.get(login) - 1);
    }

    public static void defaultCount(String login) {
        map.put(login, 3);
    }

}
