
import java.io.*;
import java.net.*;

public class server {
    private static int indexOf(String[] array, String str) {
        str = str.trim();
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(str))
                return i;
        }
        return -1;
    }

    public static void main(String arg[]) {
        String[] hosts = { "yahoo.com", "gmail.com", "cricinfo.com", "facebook.com" };
        String[] ip = { "68.180.206.184", "209.85.148.19", "80.168.92.140", "69.63.189.16" };
        System.out.println("Press Ctrl + C to Quit");
        while (true) {
            try {
                DatagramSocket serversocket = new DatagramSocket(1362);
                byte[] receivedata = new byte[1021];
                DatagramPacket recvpack = new DatagramPacket(receivedata, receivedata.length);
                serversocket.receive(recvpack);

                String sen = new String(recvpack.getData()).trim();
                InetAddress ipaddress = recvpack.getAddress();
                int port = recvpack.getPort();
                String capsent;

                System.out.println("Request for host " + sen);
                int index = indexOf(hosts, sen);
                if (index != -1) {
                    capsent = ip[index];
                } else {
                    capsent = "Host Not Found";
                }

                byte[] senddata = capsent.getBytes();
                DatagramPacket pack = new DatagramPacket(senddata, senddata.length, ipaddress, port);
                serversocket.send(pack);
                serversocket.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
