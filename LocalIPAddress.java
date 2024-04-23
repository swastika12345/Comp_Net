import java.net.InetAddress;
public class LocalIPAddress{
	public static void main(String args[]){
		try{
			InetAddress localhost=InetAddress.getLocalHost();
			System.out.println("Local IP Address:"+localhost.getHostAddress());
			InetAddress localhost1=InetAddress.getByName("www.google.com");
			System.out.println("Website IP Address:"+localhost1.getHostAddress());

		}catch(Exception e){
			System.out.println("Exception caught:"+e);
		}
	}
}