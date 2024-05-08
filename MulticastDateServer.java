
import java.io.IOException;
import java.net.*;
import java.util.Date;

public class MulticastDateServer {
    public static void main(String args[]) throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName("230.0.0.1");
        socket.setSoTimeout(1000); // Set a timeout for receiving packets

        for (int i = 0; i < 10; i++) {
            byte[] buf = new Date().toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 1313);
            socket.send(packet);
            Thread.sleep(1000); // Wait for 1 second before sending the next packet
        }
        socket.close();
    }
}

