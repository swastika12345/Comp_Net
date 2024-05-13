import java.util.Scanner;

public class CRCErrorDetection {
    
    // Function to perform CRC error detection
    public static String performCRCDetection(String data, String divisor) {
        int dataLen = data.length();
        int divisorLen = divisor.length();
        
        // Append zeros to data
        StringBuilder newData = new StringBuilder(data);
        for (int i = 0; i < divisorLen - 1; i++) {
            newData.append('0');
        }
        
        // Perform division
        for (int i = 0; i < dataLen; i++) {
            if (newData.charAt(i) == '1') {
                for (int j = 0; j < divisorLen; j++) {
                    newData.setCharAt(i + j, (char) (((newData.charAt(i + j) - '0') ^ (divisor.charAt(j) - '0')) + '0'));
                }
            }
        }
        
        // Extract CRC code
        String crcCode = newData.substring(dataLen);
        return crcCode;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter data (in binary): ");
        String data = scanner.nextLine();
        
        System.out.println("Enter divisor (in binary): ");
        String divisor = scanner.nextLine();
        
        // Perform CRC error detection
        String crcCode = performCRCDetection(data, divisor);
        System.out.println("CRC code: " + crcCode);
	String sentMsg=data+crcCode;
        System.out.println("Received Message : "+sentMsg);

        System.out.print("Enter received message (in binary): ");
        String receivedMsg = scanner.nextLine();
        
        // Check for errors
        String receivedCRC = performCRCDetection(receivedMsg, divisor);
        if (receivedCRC.equals("000")) {
            System.out.println("No error detected. Data received successfully.");
            //System.out.println("Received message: " + receivedMsg);
        } else {
            System.out.println("Error detected in received message.");
        }
        
        scanner.close();
    }
}
