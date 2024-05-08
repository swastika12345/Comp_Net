import java.io.*;
import java.net.*;

public class Client_Case {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter a string in lowercase: ");
            String message = userInput.readLine();
            out.println(message);

            String response = in.readLine();
            System.out.println("Server responded: " + response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}