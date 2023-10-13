package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class Server {
    private ArrayList<String>users = new ArrayList<>();
    private HashMap<String,Socket>getSocket = new HashMap<>();
    private HashMap<String,InputStreamReader>getInputStream = new HashMap<>();
    private HashMap<String,OutputStreamWriter>getOutputStream = new HashMap<>();
    private HashMap<String,BufferedReader>getBufferReader = new HashMap<>();
    private HashMap<String,BufferedWriter>getBufferWriter = new HashMap<>();

    private ServerSocket server;
    String lastUser(){
        return users.get(users.size()-1);
    }
    Server() throws IOException {
        this.server = new ServerSocket(1234);
    }
    String getUser(BufferedReader client) throws IOException {
      return client.readLine();
    }

    String connectClient() throws IOException {
        Socket socket = server.accept();
        InputStreamReader in   = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        BufferedReader br = new BufferedReader(in);
        BufferedWriter wr = new BufferedWriter(out);
        String user =getUser(br);//getting username
        users.add(user);
        getSocket.put(user,socket);
        getInputStream.put(user,in);
        getOutputStream.put(user,out);
        getBufferReader.put(user,br);
        getBufferWriter.put(user,wr);
        return user;
    }
    void notifying(String userName) throws IOException {
        for(String user: users){
            BufferedWriter send = getBufferWriter.get(user);
            //what if the user id disconnected?
            send.write("\nServer :"+userName+" has connected!");
            send.newLine();
            send.flush();
        }

    }
    void notification(String userName){
        new Thread(()->{
            try {
                notifying(userName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    void usersMessages() {
      for(var user:users){
          new Thread(()->{
              BufferedReader br = getBufferReader.get(user);
              String msg = null;
              try {
                  msg = br.readLine();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
              for(var user2:users){
                  if(user.equals(user2))continue;
                  BufferedWriter wr = getBufferWriter.get(user2);
                  try {
                      sendMsg(wr,"@"+user+": "+msg+"\n");
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
              }
              System.out.printf("Message from %s has been sent\n",user);
          }).start();

      }
    }
    void sendMsg(BufferedWriter wr,String msg) throws IOException {
        wr.write(msg);
        wr.newLine();;
        wr.flush();
    }
    void run() throws IOException {
        while(!server.isClosed()){
            System.out.println("...");
            String newUser  =  connectClient();
            System.out.printf("%s has connected!\n",newUser);
            notification(newUser);
            usersMessages();
        }
    }
    boolean isRunning(){
        return !server.isClosed();
    }
    void stop() throws IOException {
        server.close();
    }

}
