import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

import augie.edu.AbemelechDeepak.ArrayList;

public class ClientHandler implements Runnable {

    // To keep track of all the clients and allows us to broadcast messages to all clients
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    // Socket object to handle the client
    private Socket socket;

    // Buffer Reader and Writer to read and write to the client
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    // Client name
    private String clientName;

    // Constructor
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            // Create a new buffer reader and writer
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // Client name
            this.clientName = bufferedReader.readLine();
            // Add the client to the list of clients
            clientHandlers.add(this);
            // Print the client name
            System.out.println(clientName + " has joined the chat");
            // Send a message to all the clients that a new client has joined
            broadcastMessage(clientName + " has joined the chat");
        }

        catch(Exception e){
            // Disconnect everything
            disconnect();
        }



    }


    public void broadcastMessage(String string) {
        // Create a iterator to loop through the list of clients
        Iterator<ClientHandler> iterator = clientHandlers.iterator();
        // Loop through the list of clients
        while(iterator.hasNext()){
            try{
                // Get the next client
                ClientHandler clientHandler = iterator.next();
                if(!clientHandler.clientName.equals(clientName)){
                    clientHandler.bufferedWriter.write(clientName + ": " + string);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
            catch(Exception e){
                // Disconnect everything
                disconnect();
            }
        }
    }

    // Remove the client from the list of clients
    public void removeClient(){
        clientHandlers.remove(this);
        // broadcast a message to all the clients that a client has left
        broadcastMessage(clientName + " has left the chat");
    }

    // Disconnect everything
    public void disconnect(){
        try{
            // Remove the client from the list of clients
            removeClient();
            // check if the socket is not null
            if(socket != null){
                // Close the socket
                socket.close();
            }
            // Close the buffer reader and writer
            // check if the buffer reader and writer are not null
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        // While the client is connected
        while(!socket.isClosed()){
            try{
                // Read the message from the client
                String message = bufferedReader.readLine();
                // If the message is not null
                if(message != null){
                    // Print the message
                    System.out.println(clientName + ": " + message);
                    // Send the message to all the clients
                    broadcastMessage(message);
                }
            }catch(Exception e){
                // Disconnect everything
                disconnect();
                break;
            }
        }
    }
}
