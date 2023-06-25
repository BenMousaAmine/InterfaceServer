package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server started. Waiting for client connections...");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread clientThread = new Thread( new HandleFrameRequest(clientSocket));
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();

    }
}