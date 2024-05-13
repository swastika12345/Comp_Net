import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
 
public class SelectiveRepeatClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
 
        int frameLossChance = 10, ackLossChance = 10, waitInSec = 2;
 
        System.out.print("Automatic mode? (y/n) : ");
        boolean autoMode = (sc.nextLine().equals("y"));
        if(autoMode)
            System.out.printf("Automode is selected\nChance of frame loss : %d%%\nChance of acknowledgement loss : %d%%\n\n",frameLossChance,ackLossChance);
 
        String data = "";
        int frameSize, windowSize, currentFrame, count, nackSent = -1;
        int time = 0, droppedFrames = 0, lostAck = 0, lostFrames = 0;
        try{
            StringBuilder buffer = new StringBuilder();
            Socket s = new Socket("localhost", 6666);
            System.out.println("Connected to server");
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
 
            System.out.println("Getting no of frames...");
            frameSize = dis.readInt();
            windowSize = frameSize/2;
            currentFrame = count = 0;
            for(int i = 0; i < windowSize; i++)
                buffer.append('_');
 
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
 
                        int sendAck = 0;
                        boolean acceptFrame = false;
                        for(int i=0;i< windowSize ; i++){
                            if(frameNo == (currentFrame + i)%frameSize){
                                if(buffer.charAt(i) == '_'){
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
                                    switch (choice) {
                                        case "1" : System.out.println("Frame Lost");
                                            sendAck = 0;
                                            lostFrames++;
                                            break;
                                        case "2" : System.out.println("Acknowledgement Lost");
                                            buffer.setCharAt(i, dataChar);
                                            sendAck = 0;
                                            lostAck++;
                                            break;
                                        default:System.out.println("Frame Accepted");
                                            buffer.setCharAt(i, dataChar);
                                            sendAck = (i==0)?1:2;
                                            //currentFrame++;
                                            break;
                                    }
                                }
                                else{
                                    System.out.println("Duplicate frame\nFrame Dropped");
                                    droppedFrames++;
                                    sendAck = 2;
                                }
                                acceptFrame = true;
                                break;
                            }
                        }
 
                        //clear buffer if necessary
                        while(buffer.charAt(0)!='_'){
                            data += buffer.charAt(0);
                            if(currentFrame%frameSize == nackSent)
                                nackSent = -1;
                            currentFrame++;
                            buffer.deleteCharAt(0);
                            buffer.append('_');
                        }
 
                        //send ack/nak accordingly
                        if(!acceptFrame){
                            System.out.println("Invalid Frame : Out of Window\nFrame Dropped");
                            droppedFrames++;
                        }
                        else if(sendAck!=0){
                            int ackN = currentFrame%frameSize;
                            if(sendAck==1)
                                dos.writeInt(ackN);
                            else if(nackSent == -1){
                                dos.writeInt(-ackN);
                                nackSent = ackN;
                            }
                        }
                        count++;
                    }
                    else
                        System.out.println("No data received");
                    System.out.println("Buffer state : " + buffer.toString());
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