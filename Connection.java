import java.io.*;
import java.net.*;
import java.util.*;

public class Connection {
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
                }
                else if (firstLine.startsWith("POST")) {
                    // Read JSON body
                    char[] body = new char[contentLength];
                    reader.read(body, 0, contentLength);
                    Map<String, String> map = getStringStringMap(body);

                    //Cheks if any of the map is empty
                    String[] fields = {"vezeteknev", "keresztnev", "email", "szuletes", "telefon", "varos", "utca", "haz_szam"};
                    String[] Empty = new String[map.size()];

                    for (int i = 0; i < Empty.length; i++) {
                        if (i == 5) {
                            Empty[i] = "\"Right\""; // Special case cus its not mandatory
                        } else {
                            int fieldIndex = i < 5 ? i : i - 1;
                            Empty[i] = map.get(fields[fieldIndex]).isEmpty() ? "\"Wrong\"" : "\"Right\"";
                        }
                    }

                    //TODO: If everything is right send it to the database (ALSO MAKE IT)

                    //Create a file and put the map inside of it
                    try {
                        File file = new File("posts.json");
                        FileWriter fileWriter = new FileWriter("posts.json");
                        fileWriter.write(Arrays.toString(Empty));
                        fileWriter.close();
                        if (Arrays.toString(body).equals("[D, E, L, E, T, E]")) {
                            if (file.delete()) {
                                System.out.println("File is deleted");
                            }
                            else {
                                throw new IOException("The file hasn't been able to be deleted");
                            }
                        }
                    }
                    catch (IOException e) {
                        System.out.println("Some shit went wrong");
                        throw new Error(e);
                    }

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


    @org.jetbrains.annotations.NotNull
    private static Map<String, String> getStringStringMap(char[] body) {
        String jsonBody = new String(body);

        int substring1 = 0, substring2 = 0, substring3 = 0, substring4 = 0;
        Map<String,String> map = new HashMap<>();

        for (int i = 0; i < body.length; i++) {
            if (body[i] == '"' && substring1 == 0) {
                substring1 = i;
            }
            else if (body[i] == '"' && substring1 != 0 && substring2 == 0) {
                substring2 = i;
            }
            else if (body[i] == '"' && substring1 != 0 && substring2 != 0 && substring3 == 0) {
                substring3 = i;
            }
            else if (body[i] == '"' && substring1 != 0 && substring2 != 0 && substring3 != 0) {
                substring4 = i;
            }

            if (substring1 != 0 && substring2 != 0 && substring3 != 0 && substring4 != 0) {
                map.put(
                        jsonBody.substring(substring1 + 1,substring2),
                        jsonBody.substring(substring3 + 1,substring4));
                substring1 = 0;
                substring2 = 0;
                substring3 = 0;
                substring4 = 0;
            }
        }
        return map;
    }
}