package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
   public String name;
   public static int id = 0;
    private Socket socket;
   private InputStreamReader in;
   private OutputStreamWriter out;
  private  BufferedReader readFromServer;
   private BufferedWriter outToServer;
   private Scanner console;
    Client(String name) throws IOException {
        this.socket = new Socket("localhost",1234);
        this.in = new InputStreamReader(socket.getInputStream());
        this.out = new OutputStreamWriter(socket.getOutputStream());
        this.readFromServer  = new BufferedReader(this.in);
        this.outToServer = new BufferedWriter(this.out);
        this.console = new Scanner(System.in);
        this.name = name;
       sendMsg(name);
    }
    String getServerMsg() throws IOException {
       return readFromServer.readLine();
    }
    void sendMsg() throws IOException {
        String msg  = console.nextLine();
        outToServer.write(msg);
        outToServer.newLine();
        outToServer.flush();
    }
    void sendMsg(String msg)throws IOException{
        outToServer.write(msg);
        outToServer.newLine();
        outToServer.flush();
    }
    void close() throws IOException {
        socket.close();
        in.close();
        out.close();
        readFromServer.close();
        outToServer.close();
    }




}
