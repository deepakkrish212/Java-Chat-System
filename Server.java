import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import augie.edu.finalProgram.Belgovi.ClientHandler;

public class Server {
    // Important to listen to incoming traffic
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Start server method to keep the server running
    // Accpet incoming connections
    public void startServer() {
        while (!serverSocket.isClosed()) {
            try {
                // Accept incoming traffic
                Socket socket = serverSocket.accept();
                // Printin the address of the client
                System.out.println("New connection from " + socket.getInetAddress().getHostAddress());
                // ClientHandler object to handle multiple clients which implements interface
                // Runnable
                ClientHandler clientHandler = new ClientHandler(socket);

                // Thread object to run the clientHandler
                Thread thread = new Thread(clientHandler);
                thread.start();

            } catch (IOException e) {
                // Close the server if there is an error
                System.out.println("Server closed");
                closeServer();
            }
        }
    }

    // Close the server
    public void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        {
            ServerSocket serverSocket = new ServerSocket(8080);
            Server server = new Server(serverSocket);

            // Start the server
            server.startServer();
        }
    }
}