package UserDate;

import java.io.*;
import java.net.Socket;

public class User {
   private String name;
   private Socket socket;
   private InputStreamReader inputStreamReader;
   private OutputStreamWriter outputStreamWriter;
   private BufferedReader bufferedReader;
   private BufferedWriter bufferedWriter;
   public User(){
       name = null;
       socket = null;
       inputStreamReader = null;
       outputStreamWriter = null;
       bufferedReader = null;
       bufferedWriter = null;
   }

    public User(String name,
                Socket socket,
                InputStreamReader inputStreamReader,
                OutputStreamWriter outputStreamWriter,
                BufferedReader bufferedReader,
                BufferedWriter bufferedWriter) {
        this.name = name;
        this.socket = socket;
        this.inputStreamReader = inputStreamReader;
        this.outputStreamWriter = outputStreamWriter;
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setInputStreamReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    public void setOutputStreamWriter(OutputStreamWriter outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }

    public OutputStreamWriter getOutputStreamWriter() {
        return outputStreamWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
   public void send(String msg) throws IOException {
       if(socket.isClosed())return;

        bufferedWriter.write(msg);
        bufferedWriter.newLine();;
        bufferedWriter.flush();
    }
   public String receive() throws IOException {
      return  bufferedReader.readLine();
    }
    public void close() throws IOException {
       socket.close();
    }
}
