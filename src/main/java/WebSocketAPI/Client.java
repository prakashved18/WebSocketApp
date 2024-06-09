package WebSocketAPI;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message) throws IOException {
        out.println(message);
        String response = in.readLine();
        System.out.println("Response from server: " + response);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 8005);
        client.sendMessage("increment");
        client.sendMessage("query");
        client.sendMessage("increment");
        client.sendMessage("query");
        client.sendMessage("Ved");
    }
}