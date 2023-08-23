import java.io.*;
import java.net.*;

public class Client {
    public static void main(String args[]) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket("127.0.0.1", 1390);
            DataInputStream din = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("Enter the Logical address (IP):");
            String str1 = in.readLine();
            dout.writeBytes(str1 + '\n');

            String str = din.readLine();
            System.out.println("The Physical Address is: " + str);

            clientSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
