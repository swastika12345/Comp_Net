import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MulticastDateClient {
    public static void main(String args[]) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(1313));
        InetAddress group = InetAddress.getByName("230.0.0.1");
        channel.join(group, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        // create a multicast channel and join a group
        for (int i = 0; i < 10; i++) {
            ByteBuffer buf = ByteBuffer.allocate(256);
            channel.receive(buf);
            buf.flip();
            // empty packet to receive group data
            String received = new String(buf.array()).trim();
            System.out.println("Current server time: " + received);
            // print the answer
        }
        channel.close();
    }
}
