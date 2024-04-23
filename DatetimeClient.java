import java.io.*;  
import java.net.*; 
import java.text.SimpleDateFormat;
import java.util.Date; 
public class DatetimeClient {  
public static void main(String[] args) {  
try{      
Socket s=new Socket("localhost",6666);  
DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
Date date=new Date(); 
dout.writeUTF(format1.format(date));  
dout.flush();  
dout.close();  
s.close();  
}catch(Exception e)
	{System.out.println(e);}  
}  
}  