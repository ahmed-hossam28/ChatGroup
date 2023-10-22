package Client;

import CustomerData.User;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
     User user ;
    Client(String name) throws IOException {
        user  = new User();
        user.setName(name);
        user.setSocket(new Socket("localhost",1234));
        user.setInputStreamReader( new InputStreamReader(user.getSocket().getInputStream()));
        user.setOutputStreamWriter(new OutputStreamWriter(user.getSocket().getOutputStream()));
        user.setBufferedReader(new BufferedReader(user.getInputStreamReader()));
        user.setBufferedWriter(new BufferedWriter(user.getOutputStreamWriter()));
        sendMsg(name);
    }
    String getServerMsg() throws IOException {
        return user.receive();
    }
    void sendMsg(String msg)throws IOException{
        user.send(msg);
    }
    void close() throws IOException {
    }




}
