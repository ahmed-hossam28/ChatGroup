package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
   public String name;
   public int id;
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
    }
    void read() throws IOException {
        readFromServer.readLine();
    }
    void write() throws IOException {
        String msg  = console.nextLine();
        outToServer.write(msg);
        outToServer.newLine();
        outToServer.flush();
    }
    void write(String msg)throws IOException{
        outToServer.write(msg);
        outToServer.newLine();
        outToServer.flush();
    }




}
