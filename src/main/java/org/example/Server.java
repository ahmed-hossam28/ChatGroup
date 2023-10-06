package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<Socket>socket = new ArrayList<>();
    private ArrayList<InputStreamReader> in = new ArrayList<>();
    private ArrayList<OutputStreamWriter> out = new ArrayList<>() ;
    private ArrayList<BufferedReader> readFromClient  = new ArrayList<>();
    private ArrayList<BufferedWriter> outToClient  = new ArrayList<>();
    private ServerSocket server;
    Server() throws IOException {
        this.server = new ServerSocket(1234);

    }
    Socket lastClient(){
        return socket.get(socket.size()-1);
    }
    void getClient() throws IOException {
        socket.add(server.accept());
        in.add(new InputStreamReader(lastClient().getInputStream()));
        out.add(new OutputStreamWriter(lastClient().getOutputStream()));
    }
    void run() throws IOException {
        while(!server.isClosed()){
            System.out.println("Waiting for Clients...");
             getClient();
            System.out.println("new client has connected!");

        }
    }
    boolean isRunning(){
        return !server.isClosed();
    }
    void stop() throws IOException {
        server.close();
    }
    void getMessage(){

    }

}
