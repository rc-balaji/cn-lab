import java.io.*;
import java.net.*;

public class Server {
    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(1390);
            Socket clientSocket = serverSocket.accept();

            while (true) {
                DataInputStream din = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

                String str = din.readLine();

                String ip[] = { "165.165.80.80", "165.165.79.1" };
                String mac[] = { "6A:08:AA:C2", "8A:BC:E3:FA" };

                boolean found = false;
                for (int i = 0; i < ip.length; i++) {
                    if (str.equals(ip[i])) {
                        dout.writeBytes(mac[i] + '\n');
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    dout.writeBytes("IP Address not found." + '\n');
                }
            }
        } catch (Exception e) {
            System.out.println("Sent Successfully!!!");
        }
    }
}
