import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;

public class ChatTest {
    public static void main(String[] args) throws UnknownHostException, IOException {
        // Scanner scanner = new Scanner(System.in);
        String clientName = args[0];

        System.out.println("Enter your name: ");

        Socket socket = new Socket("localhost", 8080);
        Client client = new Client(socket, clientName);
        // These are blocking methods
        // The client will not be able to send messages until it receives a message from
        // the server
        // Since they are sperate threads, the client can send messages and listen for
        // messages at the same time
        client.listenForMessage();
        client.sendMessage();
    }
}
