package WebSocketAPI;

import java.io.*;
import java.net.*;

public class Server {
    private int counter = 0;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started. Listening for incoming connections...");
    }

    public void start() throws IOException {
        Socket socket = serverSocket.accept();
        System.out.println("Incoming connection from " + socket.getInetAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String message = in.readLine();
            if (message == null) {
                break;
            }

            System.out.println("Received message: " + message);

            if (message.equals("increment")) {
                counter++;
                out.println("Counter incremented. Current value: " + counter);
            } else if (message.equals("query")) {
                out.println("Current counter value: " + counter);
            } else {
                out.println("Unknown command");
            }
        }

        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8005);
        server.start();
    }
}