package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class HandleFrameRequest implements Runnable {
    Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public HandleFrameRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            setupStreams();

            String request = receiveRequest();
            if(request!=null){
                String response = handleRequest(request);
                sendResponse(response);
            }

            closeStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupStreams() throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void closeStreams() throws IOException {
        in.close();
        out.close();
        clientSocket.close();

    }

    public String receiveRequest() throws IOException {
        String request=null;
        try {
            while ((request = in.readLine()) != null) {
                return request;
            }
        } catch (SocketException e) {
            System.out.println("Connessione chiusa forzatamente dal client.");
        }
        return request;
    }


/*
    private String processRequest(String request) {
        // Elabora la richiesta e restituisci la risposta desiderata
        // Aggiungi qui la tua logica di gestione delle richieste
        return "Response to " + request;
    }*/
    private void sendResponse(String response) {
        out.println(response);
        out.flush();
    }

    private String handleRequest(String request) {
        switch (request) {
            case "ALL":
                return handleAllRequest();
            case "MORE_EXPENSIVE":
                return handleMoreExpensiveRequest();
            case "ALL_SORTED":
                return handleSortedByNameRequest();
            case "EXIT":
                try {
                    clientSocket.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            default:
                return "Invalid request";
        }
    }

    private String handleAllRequest() {
        List<Car> carList = WareHouse.getInstance().getCarList();
        return transformToJson(carList);
    }

    private String handleMoreExpensiveRequest() {
        List<Car> carList = WareHouse.getInstance().mostExpensiveCar();
        return transformToJson(carList);
    }

    private String handleSortedByNameRequest() {
        List<Car> carList = WareHouse.getInstance().allSorted();
        return transformToJson(carList);
    }

    private String transformToJson(List<Car> carList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(carList);
    }
}
