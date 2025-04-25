import java.io.*;
import java.net.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String line;
                List<String> headers = new ArrayList<>();
                int contentLength = 0;

                // Read headers
                while (!(line = reader.readLine()).isEmpty()) {
                    headers.add(line);
                    if (line.toLowerCase().startsWith("content-length:")) {
                        contentLength = Integer.parseInt(line.split(":")[1].trim());
                    }
                }

                String firstLine = headers.getFirst();
                System.out.println("Output: " + firstLine);

                if (firstLine.startsWith("OPTIONS")) {
                    // Respond to preflight request
                    writer.write("HTTP/1.1 204 No Content\r\n");
                    writer.write("Access-Control-Allow-Origin: *\r\n");
                    writer.write("Access-Control-Allow-Methods: POST, GET, OPTIONS\r\n");
                    writer.write("Access-Control-Allow-Headers: Content-Type\r\n");
                    writer.write("\r\n");
                    writer.flush();
                } else if (firstLine.startsWith("POST")) {
                    // Read JSON body
                    char[] body = new char[contentLength];
                    reader.read(body, 0, contentLength);
                    String jsonBody = new String(body);
                    System.out.println("Received JSON:");
                    System.out.println(jsonBody);

                    // Send response
                    writer.write("HTTP/1.1 200 OK\r\n");
                    writer.write("Access-Control-Allow-Origin: *\r\n");
                    writer.write("Content-Type: text/plain\r\n");
                    writer.write("\r\n");
                    writer.write("Data received\n");
                    writer.flush();
                }

                socket.close();
            }
        } catch (Exception e) {
            System.out.println("Some shit went terribly wrong");
            throw new Error(e);
        }
    }
}