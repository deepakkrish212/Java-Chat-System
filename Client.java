import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

import augie.edu.finalProgram.Belgovi.Encrpytion;
import augie.edu.finalProgram.Belgovi.FileStorage;
import augie.edu.finalProgram.Belgovi.MyArrayList;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;
    private FileStorage fileStorage;
    private Encrpytion encrpytion;

    // Constructor
    public Client(Socket socket, String clientName) {
        try {
            this.socket = socket;
            this.clientName = clientName;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            this.fileStorage = new FileStorage(clientName + ".csv");
            this.encrpytion = new Encrpytion(1234);

            // Check if history of previous conversation exists, and load history
            Path path  = Paths.get(clientName + ".csv");
            if (Files.exists(path)) {
                MyArrayList<String[]> history = fileStorage.readfromFile();
                Iterator<String[]> iterator = history.iterator();
                while (iterator.hasNext()) {
                    String[] singleLine = iterator.next();
                    System.out.println(singleLine[0] + ": " + encrpytion.decrpytText(singleLine[2]));
                }

            }
        } catch (Exception e) {
            // Disconnect everything
            disconnect();
        }
    }

    
    // Send a message to the server
    public void sendMessage() {
        try {
            bufferedWriter.write(clientName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // TODO: Make sure that the client can send messages to the server using GUI
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                // Get datetime to store to file database
                LocalDateTime now = LocalDateTime.now();

                String message = scanner.nextLine();
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                // Store into file
                // Encrpyt Message before file
                message = encrpytion.encrpytText(message);
                fileStorage.writeToFile(new String[] {clientName, now.toString(), message});
            }

        } catch (Exception e) {
            // Disconnect everything
            e.printStackTrace();
            disconnect();
        }
    }

    // Listen for messages from the server
    // Using a thread to listen for messages
    // This uses concurrency
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()) {
                        // Get datetime to store to file database
                        LocalDateTime now = LocalDateTime.now();

                        String message = bufferedReader.readLine();
                        
                        System.out.println(message);

                        // Decrpyt Message before storing
                        String[] messagePart = message.split(": ");
                        messagePart[1] = encrpytion.encrpytText(messagePart[1]);

                        // Store message from incoming chat
                        fileStorage.writeToFile(new String[] {messagePart[0], now.toString(), messagePart[1]});
                    }
                } catch (Exception e) {
                    // Disconnect everything
                    disconnect();
                }
            }
        }).start();
    }

    private void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to run the client
    public static void main(String[] args) throws UnknownHostException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String clientName = scanner.nextLine();
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
