import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 12345; // Port number for the server to listen on
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and listening on port " + PORT);
            
            while (true) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                
                // Create a new thread to handle each client request
                new Thread(() -> {
                    try {
                        // Get the output stream to send data to the client
                        OutputStream outputStream = clientSocket.getOutputStream();
                        PrintWriter out = new PrintWriter(outputStream, true);
                        
                        // Get the current date and time
                        Date currentDate = new Date();
                        
                        // Send the current date and time back to the client
                        out.println("Current Date and Time: " + currentDate);
                        
                        // Close the streams and the socket
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}