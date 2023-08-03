import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Ser {
    private static final int PORT = 3001;
    private static final String IMAGE_PATH = "Java.jpg"; 
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                BufferedReader request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream response = clientSocket.getOutputStream();

                String line = request.readLine();
                if (line != null) {
                    String[] requestParts = line.split(" ");
                    if (requestParts.length >= 3 && requestParts[0].equals("GET")) {
                        String path = requestParts[1];
                        if (path.equals("/image")) {
                            serveImage(response);
                        } else {
                            serve404NotFound(response);
                        }
                    }
                }

                clientSocket.close();
                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serveImage(OutputStream response) throws IOException {
        File imageFile = new File(IMAGE_PATH);
        if (imageFile.exists()) {
            byte[] imageBytes = Files.readAllBytes(Path.of(IMAGE_PATH));

            PrintWriter pw = new PrintWriter(response);
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: image/jpeg");
            pw.println("Content-Length: " + imageBytes.length);
            pw.println();
            pw.flush();

            response.write(imageBytes);
            response.flush();
        } else {
            serve404NotFound(response);
        }
    }

    private static void serve404NotFound(OutputStream response) throws IOException {
        PrintWriter pw = new PrintWriter(response);
        pw.println("HTTP/1.1 404 Not Found");
        pw.println("Content-Type: text/html");
        pw.println();
        pw.println("<h1>404 Not Found</h1>");
        pw.flush();
    }
}
