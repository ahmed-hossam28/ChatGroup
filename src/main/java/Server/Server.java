package Server;

import CustomerData.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ArrayList<User> users = new ArrayList<>();//double users have the same name? it's okay now :D!!
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private final ServerSocket server;
    User lastUser(){
        return users.get(users.size()-1);
    }
    Server() throws IOException {
        this.server = new ServerSocket(1234);
    }
    String getUserName(BufferedReader client) throws IOException {
      return client.readLine();
    }

    String connectClient() throws IOException {
        Socket socket = server.accept();
        InputStreamReader in   = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        BufferedReader br = new BufferedReader(in);
        BufferedWriter wr = new BufferedWriter(out);
        String userName = getUserName(br);
        User user = new User(userName,socket,in,out,br,wr);
        users.add(user);
        happyChatting(user);//  :)
        return userName;
    }
    void notifying(String userName) throws IOException {
        for(User user: users){
            if(userName.equals(user.getName()))continue;
            //what if the user is disconnected?
            // problem is solved finally :D   23/10/2023
            user.send("\nServer :"+userName+" has connected!");
        }
    }
    void notification(String userName){//do we need to run this func in separated thread?
         executor.execute(()-> {
             try {
                 notifying(userName);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         });
    }

    void usersMessages() {//deprecated
      for(User sender: users){
          //each user will work on his own thread
         executor.execute(()->{//de we need to start new thread for the same user every time??

             //this while true was the solution for the crashed messaging
           while(true)  {
                 BufferedReader br = sender.getBufferedReader();
                 String msg = null;
                 try {
                     msg = br.readLine();
                     if(msg.equals(""))continue;
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 for (User receiver : users) {
                     if (sender.equals(receiver)) continue;
                     try {
                      receiver.send( "@" + sender + ": " + msg + "\n");
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }
                 System.out.printf("Message from %s has been sent\n", sender);
             }
          });

      }
    }
    void happyChatting(User sender){
        executor.execute(()->{//de we need to start new thread for the same user every time??
            //well now we don't need to, just start the thread whenever a new user come
            //this while true was the solution for the crashed messaging
            while(true)  {
                boolean isClosed = false;
              //  BufferedReader br = sender.getBufferedReader();
                String msg = null;
                try {
                    msg = sender.receive();
                    if(msg.equals("close")){
                        sender.send("close");
                        sender.close();
                        isClosed=true;
                        System.out.println(sender.getName()+" has disconnected!");
                    }
                    else if(msg.equals(""))continue;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (User receiver : users) {
                    if (sender.equals(receiver)) continue;
                    if(isClosed){
                        try {
                            receiver.send("Server :"+sender.getName()+" has disconnected!\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        try {
                            receiver.send("@" + sender.getName() + ": " + msg + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if(!isClosed)
                  System.out.printf("Message from %s has been sent\n", sender);
                else break;
            }
        });
    }
    void startThreadConnection(){
        //deprecated
        executor.execute(()->{
        System.out.println("waiting for clients..");
        String newUser;
            try {
                 newUser  =  connectClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%s has connected!\n",newUser);
            notification(newUser);

        });
    }
    void startSyncConnection() throws IOException {
        System.out.println("waiting for clients..");
        String newUser  =  connectClient();//the problem is that it blocks the server from receiving messages
        // it is solved by run the chat messaging in a separated thread
        System.out.printf("%s has connected!\n",newUser);
        notification(newUser);
    }

    void startMessaging(){
        executor.execute(()->usersMessages());
    }//deprecated
    void run() throws IOException {
        while(!server.isClosed()){
            startSyncConnection();
            //startMessaging();
        }
    }
    boolean isRunning(){
        return !server.isClosed();
    }
    void stop() throws IOException {
        server.close();
    }

}
