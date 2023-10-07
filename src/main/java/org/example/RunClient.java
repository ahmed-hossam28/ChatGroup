package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunClient {
     static void menu(){
         System.out.println("Select Your Option..");
         System.out.println("1)add user\n2)Quite\nEnter the Number of Your Option");

     }
   static   BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
     static  Client client = null;
    public static void main(String[] args) throws IOException {
       int op;

            menu();
         op = Integer.parseInt(in.readLine());
         if(op==1){
             System.out.println("Enter User Name");
             String name = in.readLine();
           client  =  new Client(name);
             new Thread(()-> {
                 try {
                     while(true) {
                         System.out.println("\n"+client.getServerMsg());
                         //System.out.println();
                         //System.out.printf("@%s: ",name);//just to look like usual in terminal because the server message will spoil the format
                     }
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             }).start();
             while(true){
                 System.out.printf("@%s: ",name);
                 String msg = in.readLine();
                 client.sendMsg(msg);
               //  client.getServerMsg();
                 if(msg.equalsIgnoreCase("close")) {
                     client.close();
                     break;
                 }
             }
         }
    }



}
