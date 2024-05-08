import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9999);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server..."); // Print connection message

            String userInputLine;
            while ((userInputLine = userInput.readLine()) != null) {
                System.out.println("Sending echo string: " + userInputLine); // Print message before sending
                writer.println(userInputLine);
                String response = reader.readLine();
                System.out.println("Received: " + response); // Print received echo
                if (response.equals("exit")) // Terminate if server sends "exit"
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
