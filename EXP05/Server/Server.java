import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception {
        // Initialize ServerSocket
        ServerSocket ssock = new ServerSocket(5000);
        Socket socket = ssock.accept();

        // Specify the file
        File file = new File("index.html");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = socket.getOutputStream();

        byte[] contents;
        long fileLength = file.length();
        long current = 0;

        while (current != fileLength) {
            int size = 10000;
            if (fileLength - current >= size)
                current += size;
            else {
                size = (int) (fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.print("Sending file... " + (current * 100) / fileLength + "% complete!\n");
        }

        os.flush();
        socket.close();
        ssock.close();
        bis.close();
        System.out.println("File sent successfully!");
    }
}
