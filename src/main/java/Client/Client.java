package Client;

import UserData.User;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
     User user ;
     private final ExecutorService executor = Executors.newFixedThreadPool(5);
     private final  BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
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
        user.getSocket().close();
    }
    void receiveServerMsg(){
        executor.execute(()-> {
            try {
                String serverMsg;
                while(true) {
                    serverMsg = getServerMsg();
                    if(serverMsg.equals("close"))break;
                    System.out.print("\n"+serverMsg);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void userChat(){
        executor.execute(()-> {
            while (true) {
                System.out.printf("@%s: ", user.getName());
                String msg = null;
                try {
                    msg = console.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(msg.equalsIgnoreCase("close")){
                        System.out.println("Are you sure you want to close the chat?");
                        System.out.println("Type yes Or no");
                        String validate = console.readLine();
                        if(validate.equals("no"))continue;
                    }
                    sendMsg(msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(msg.equalsIgnoreCase("close")){
                    System.out.println("Thank you for using our application :)\nCome back again please!");
                    executor.shutdown();
                    executor.close();
                    break;
                }
            }
        });
    }


}
