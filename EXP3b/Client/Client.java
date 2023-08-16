

import java.io.*;
import java.net.*;

public class Client {
    public static DatagramSocket ds;
    public static int clientport = 789, serverport = 790;

    public static void main(String args[]) throws Exception {
        ds = new DatagramSocket(clientport);
        byte buffer[] = new byte[1024];
        BufferedReader dis = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("client ready");
        InetAddress ia = InetAddress.getLocalHost();
        while (true) {
            System.out.print("Client: ");
            String str = dis.readLine();
            if (str.equals("end")) break;
            buffer = str.getBytes();
            ds.send(new DatagramPacket(buffer, str.length(), ia, serverport));
            
            DatagramPacket p = new DatagramPacket(buffer, buffer.length);
            ds.receive(p);
            String psx = new String(p.getData(), 0, p.getLength());
            System.out.println("Server: " + psx);
        }
    }
}
