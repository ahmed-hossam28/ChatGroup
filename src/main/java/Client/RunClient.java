package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class RunClient {
    static String name;
    static  BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    static  Client client = null;
    static void menu(){
        System.out.println("Note!!!!");
        System.out.println("when the chatting start if you want to close the chat type \"close\" like you are typing a message without the double quotes!! ");
        System.out.println("Select Your Option..");
        System.out.println("1)log in\n2)Quite\nEnter the Number of Your Option");
    }

    public static void main(String[] args) throws IOException {
        int op;
        menu();
        op = Integer.parseInt(console.readLine());
        if(op==1){
            System.out.println("Enter User Name");
            name = console.readLine();
            client  =  new Client(name);
            System.out.println("you have logged in successfully!");
            client.receiveServerMsg();
            client.userChat();
        }
    }



}
