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
    public static void main(String[] args) throws IOException {
       int op;
        do{
         op = Integer.parseInt(in.readLine());
         if(op==1){
             System.out.println("Enter User Name");
             String name = in.readLine();
             new Client(name);
         }
        }while(op!=2);

    }
}
