package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunClient {
     static void menu(){
         System.out.println("Select Your Option..");
         System.out.println("1)add user\n2)Quite");
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

             Runnable run = ()-> {
                 try {
                     while(true) {
                         System.out.println(client.getMsg());
                         System.out.println();
                         System.out.printf("@%s: ",name);
                     }
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             };
             Thread thread = new Thread(run);
              thread.start();
             while(true){
                 System.out.printf("@%s: ",name);
                 String msg = in.readLine();

             }
         }
    }



}
