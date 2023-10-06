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
    String getUser() throws IOException {

      return readFromClient.get(readFromClient.size()-1).readLine();
    }
    Socket lastClient(){
        return socket.get(socket.size()-1);
    }
    void getClient() throws IOException {
        socket.add(server.accept());
        in.add(new InputStreamReader(lastClient().getInputStream()));
        out.add(new OutputStreamWriter(lastClient().getOutputStream()));
        readFromClient.add(new BufferedReader(in.get(in.size()-1)));
        outToClient.add(new BufferedWriter(out.get(out.size()-1)));
    }
    void Notify(BufferedWriter client,String user) throws IOException {
        client.write(user+" has connected!");
        client.newLine();
        client.flush();
    }
    void run() throws IOException {
        while(!server.isClosed()){
            System.out.println("Waiting for Clients...");
             getClient();
             String user = getUser();
            System.out.printf("%s has connected!\n",user);
            for(var client:outToClient){
                if(client.equals(outToClient.get(outToClient.size()-1)))break;
                Notify(client,user);
            }

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
