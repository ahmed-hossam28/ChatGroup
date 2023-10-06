package org.example;
import java.io.IOException;
import java.util.Scanner;

public class Main {
   static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Runnable run = () -> sc.next();
        Thread thread = new Thread(run);
        thread.start();
        System.out.println("Thread is Running");
    }
}