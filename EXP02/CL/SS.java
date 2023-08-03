import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SS {
    private static final String SERVER_URL = "http://localhost:3001/image"; 

    public static void main(String[] args) {
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    int contentLength = connection.getContentLength();

                    // Read the image data
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream imageData = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        imageData.write(buffer, 0, bytesRead);
                    }

                    Path filePath = Path.of("./image.jpg");
                    Files.write(filePath, imageData.toByteArray(), StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING);
                    System.out.println("Image downloaded and saved to: " + filePath.toAbsolutePath());
                } else {
                    System.out.println("The requested URL is not an image.");
                }
            } else {
                System.out.println("Failed to download image. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
