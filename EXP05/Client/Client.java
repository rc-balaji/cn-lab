import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        // Initialize socket
        Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);
        byte[] contents = new byte[10000];
        int bytesRead;

        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream("receivedFile.html");
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        while ((bytesRead = is.read(contents)) != -1) {
            bos.write(contents, 0, bytesRead);
        }

        bos.flush();
        socket.close();
        System.out.println("File saved successfully!");
    }
}
