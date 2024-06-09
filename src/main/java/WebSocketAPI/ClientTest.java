package WebSocketAPI;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    @Test
    public void testClient() throws IOException {
        Server server = new Server(8007);
        Thread serverThread = new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        Client client = new Client("localhost", 8007);

        client.sendMessage("increment");
        assertEquals("Counter incremented. Current value: 1", getResponse(client));

        client.sendMessage("query");
        assertEquals("Current counter value: 1", getResponse(client));

        client.sendMessage("increment");
        assertEquals("Counter incremented. Current value: 2", getResponse(client));

        client.sendMessage("query");
        assertEquals("Current counter value: 2", getResponse(client));

        client.sendMessage("unknown");
        assertEquals("Unknown command", getResponse(client));

        serverThread.interrupt();
    }

    private String getResponse(Client client) throws IOException {
        Socket socket = new Socket("localhost", 8007);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }
}
