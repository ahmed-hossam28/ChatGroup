package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class RunClient {
   static String name;
   static ExecutorService executor = Executors.newFixedThreadPool(10);
     static void menu(){
         System.out.println("Select Your Option..");
         System.out.println("1)add user\n2)Quite\nEnter the Number of Your Option");

     }
     static void receiveServerMsg(){
        executor.execute(()-> {
         try {
             while(true) {
                 System.out.println("\n"+client.getServerMsg());
             }
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     });
     }
    static void userChat(){
       executor.execute(()-> {
           while (true) {
               System.out.printf("@%s: ", name);
               String msg = null;
               try {
                   msg = in.readLine();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               try {
                   client.sendMsg(msg);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       });
     }
     static  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
     static  Client client = null;
    public static void main(String[] args) throws IOException {
       int op;
            menu();
         op = Integer.parseInt(in.readLine());
         if(op==1){
             System.out.println("Enter User Name");
              name = in.readLine();
             client  =  new Client(name);
             receiveServerMsg();
             userChat();
         }
    }



}
