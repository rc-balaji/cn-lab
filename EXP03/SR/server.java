package SR;
import java.net.*;
import java.io.*;

public class server {
    public static void main(String args[]) {
        ServerSocket s = null;
        String line;
        DataInputStream is = null;
        PrintStream ps = null;
        Socket c = null;

        try {
            s = new ServerSocket(9000);
            c = s.accept();
            is = new DataInputStream(c.getInputStream());
            ps = new PrintStream(c.getOutputStream());

            while (true) {
                line = is.readLine();
                if (line == null) {
                    break;
                }
                ps.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (is != null) is.close();
                if (ps != null) ps.close();
                if (c != null) c.close();
                if (s != null) s.close();
            } catch (IOException ex) {
                System.out.println("Error while closing resources: " + ex.getMessage());
            }
        }
    }
}
