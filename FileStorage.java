import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import augie.edu.AbemelechDeepak.MyArrayList;

public class FileStorage {

    private BufferedWriter writer;
    private BufferedReader reader;
    String fileName;

    public FileStorage(String fileName) {
        // Set file name
        this.fileName = fileName;
    }

    public void writeToFile(String[] messageStream) {
        // The input should be Username, Datetime, Message
        // Collect the messageStream into a csv formate
        String singleLine = Stream.of(messageStream)
            .collect(Collectors.joining(","));

        try {
            // Write the messageStream
            this.writer = new BufferedWriter(new FileWriter(fileName,true));
            this.writer.write(singleLine + "\n");
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public MyArrayList<String[]> readfromFile() {

        MyArrayList<String[]> allMessageStream = new MyArrayList<String[]>();

        try {
            this.reader = new BufferedReader(new FileReader(fileName));
            String singleLine;
            while((singleLine = reader.readLine()) != null) {
                String[] messageStream = singleLine.split(",");
                allMessageStream.add(messageStream);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allMessageStream;
    }

    public static void main(String[] args) {
          FileStorage chatHistory = new FileStorage("Abemelech+Deepak+History.csv");
          chatHistory.writeToFile(new String[] {"Abemelech", "Dec 13", "Hello World"});
          chatHistory.writeToFile(new String[] {"Deepak", "Dec 13", "Hello World"});
    }
}
