import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
 
public class SelectiveRepeatServer {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        try {
            String data;
            int frameSize, windowSize, sendPos = 0, frameTimer[], count = 0, maxFrame = 10;
            int time = 0, waitInSec = 2;
 
            System.out.print("Enter data : ");
            data = sc.nextLine();
            System.out.print("Enter no. of frames : ");
            frameSize = Integer.parseInt(sc.nextLine());
            windowSize = frameSize/2;
            frameTimer = new int[windowSize];
            for (int i = 0; i < frameTimer.length; i++)
                frameTimer[i] = i;
 
            System.out.println("Waiting for client to connect...");
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            System.out.println("Client has connected");
 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
 
            System.out.printf("Sending no. of frames(%d)\n",frameSize);
            dos.writeInt(frameSize);
 
            TimeUnit.SECONDS.sleep(waitInSec);
 
            while (sendPos < data.length()) {
 
                System.out.println("\n------------" + time +"-------------\n");
 
                // check timers
                for (int i = 0; i < frameTimer.length; i++) {
                    if (count == frameTimer[i]) {
 
                        if((sendPos+i) >= data.length())
                            break;
 
                        int frameNo = (sendPos + i) % frameSize;
                        String dataToSend = "" + frameNo + data.charAt(sendPos + i);
                        System.out.println("Frame timer over for frame  " + frameNo + ", resending : " + dataToSend);
                        dos.writeInt(frameNo);
                        dos.writeChar(data.charAt(sendPos+i));
                    }
                }
                // check for acknowledgements
                try {
                    if(dis.available()>0){
                        int ack = dis.readInt(), diff = 0;
                        System.out.println("Received acknowledgement : " + ack);
                        ack = Math.abs(ack);
 
                        int currentFrameStart = sendPos % frameSize;
                        if (ack > currentFrameStart)
                            diff = ack - currentFrameStart;
                        else if (ack < currentFrameStart)
                            diff = ack - currentFrameStart + frameSize;
                        sendPos += diff;
 
                        frameTimer[0] = (count + 1) % maxFrame;
                        for (int i = 1; i + diff < frameTimer.length; i++){
                            frameTimer[i] = frameTimer[i+diff];
                            frameTimer[i+diff] = (count + i + 1) % maxFrame;
                        } 
                    }
                    else
                        System.out.println("No Acknowledgements");
                } catch(IOException e){
                    System.out.println("Connection ended");
                    break;
                }
                count = (count + 1) % maxFrame;
                //System.out.println("Press Enter to continue...");
                //sc.nextLine();
                TimeUnit.SECONDS.sleep(waitInSec);
                time++;
            }
            System.out.println("Complete data sent, closing server socket...");
            dos.writeInt(-9999);
            dos.close();
            dis.close();
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        sc.close();
    }
}
 
