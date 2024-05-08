import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Server started...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected...");

                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        System.out.println("Received: " + inputLine); // Print received string
                        writer.println(inputLine); // Echo back to client
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

