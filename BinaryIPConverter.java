import java.util.Scanner;

public class BinaryIPConverter {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Input binary IP address
        System.out.print("Enter the IP address in binary form (e.g., 11000000101010000000000100000001): ");
        String binaryIPAddress = scanner.nextLine();
        
        // Ensure the input is a valid binary IP address
        if (!isValidBinaryIPAddress(binaryIPAddress)) {
            System.out.println("Invalid binary IP address format.");
            return;
        }
        
        // Separate network and host bits
        String networkBits = binaryIPAddress.substring(0, 24);
        String hostBits = binaryIPAddress.substring(24);
        
        // Convert binary strings to decimal
        int networkDecimal1 = Integer.parseInt(networkBits.substring(0, 8), 2);
        int networkDecimal2 = Integer.parseInt(networkBits.substring(8, 16), 2);
        int networkDecimal3 = Integer.parseInt(networkBits.substring(16, 24), 2);
        int hostDecimal = Integer.parseInt(hostBits, 2);
        
        // Print network address and host ID
        System.out.println("Network Address: " + networkDecimal1 + "." + networkDecimal2 + "." + networkDecimal3 + ".0");
        System.out.println("Host ID: " + hostDecimal);
        
        scanner.close();
    }
    
    // Function to check if the input is a valid binary IP address
    public static boolean isValidBinaryIPAddress(String binaryIPAddress) {
        // IP address length should be 32 bits
        if (binaryIPAddress.length() != 32)
            return false;
        
        // IP address should contain only 0s and 1s
        for (char c : binaryIPAddress.toCharArray()) {
            if (c != '0' && c != '1')
                return false;
        }
        
        return true;
    }
}
  