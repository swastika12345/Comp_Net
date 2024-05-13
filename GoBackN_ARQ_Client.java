import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
 
public class GoBackN_ARQ_Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
 
        int frameLossChance = 10, ackLossChance = 10, waitInSec = 2;
 
        System.out.print("Automatic mode? (y/n) : ");
        boolean autoMode = (sc.nextLine().equals("y"));
        if(autoMode)
            System.out.printf("Automode is selected\nChance of frame loss : %d%%\nChance of acknowledgement loss : %d%%\n\n",frameLossChance,ackLossChance);
 
        String data = "";
        int frameSize, currentFrame, count, nackSent = -1;
        int time = 0, droppedFrames = 0, lostAck = 0, lostFrames = 0;
        try{
            Socket s = new Socket("localhost", 6666);
            System.out.println("Connected to server");
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
 
            System.out.println("Getting no of frames...");
            frameSize = dis.readInt();
            currentFrame = count = 0;
 
            System.out.printf("Received no. of frames : %d\n",frameSize);
 
            TimeUnit.SECONDS.sleep(waitInSec);
 
            while(true){
 
                System.out.println("\n------------" + time +"-------------\n");
 
                try{
                    if(dis.available()>0){
                        int frameNo = dis.readInt();
                        if(frameNo == -9999)
                            break;
                        char dataChar = dis.readChar();
                        System.out.printf("Received data : %d %c\n",frameNo,dataChar);
                        if(frameNo == currentFrame){
                            String choice;
                            if(!autoMode){
                                System.out.print("Matching frame found\n"+
                                    "1) Emulate lost frame(frame lost in transmission)\n"+
                                    "2) Emulate lost acknowledgedment(accept frame but acknowledgement is lost in transmission)\n"+
                                    "Anything else) Acknowledge frame\n"+
                                    "Enter choice : ");
                                choice = sc.nextLine();
                            }
                            else{
                                int chance = (int)(Math.random()*100);
                                choice = (chance<=(ackLossChance+frameLossChance))?((chance<=frameLossChance)?"1":"2"):"3";
                            }
 
                            switch(choice){
                                case "1" : lostFrames++;
                                    System.out.println("Frame Lost");
                                    break;
                                case "2" : data+=dataChar;
                                    currentFrame = (currentFrame+1)%frameSize;
                                    nackSent = -1;
                                    lostAck++;
                                    System.out.println("Acknowledgement Lost");
                                    break;
                                default : data+=dataChar;
                                    currentFrame = (currentFrame+1)%frameSize;
                                    nackSent = -1;
                                    dos.writeInt(currentFrame);
                                    System.out.println("Acknowledged");
                            }
                        }
                        else{
                            System.out.println("Frame mismatch : Required Frame = "+currentFrame+
                                "\nFrame Dropped");
                            if(nackSent == -1 || nackSent == frameNo){
                                System.out.println("Sending negative acknowledgement");
                                dos.writeInt(-currentFrame);
                                nackSent = currentFrame;
                            }
                            droppedFrames++;
                        }
                        count++;
                    }
                    else
                        System.out.println("No data received");
                    System.out.println("Data : " + data);
                }catch(IOException e){
                    System.out.println("Connection closed by server");
                    break;
                }
                TimeUnit.SECONDS.sleep(waitInSec);
                time++;
            }
            System.out.printf("Data received in %d frames : %s\n",count,data);
            System.out.printf("Lost Frames : %d\nLost Acknowledgements : %d\nDropped Frames : %d\n",lostFrames,lostAck,droppedFrames);
            s.close();
        }catch(Exception e){
            System.out.println(e);
        }
        sc.close();
    }    
}
 