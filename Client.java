import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost"; // Server address
        final int PORT = 12345; // Server port number
        
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            
            // Get the input stream to receive data from the server
            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            
            // Read the response from the server
            String response = in.readLine();
            System.out.println("Server response: " + response);
            
            // Close the streams and the socket
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}