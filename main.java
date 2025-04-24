import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class main {
    public static void main(String[] args){
        String next = "";
        try (ServerSocket serverSocket = new ServerSocket(8080)){
            do {
                Socket socket = serverSocket.accept();
                Scanner inputData = new Scanner(socket.getInputStream());
                String getOutput = (String) inputData.nextLine();
                System.out.println("Output: " + getOutput);
                next = getOutput;
            } while (next == "close");
            serverSocket.close();
        }
        catch (Exception e) {
            System.out.println("Some shit went terribly wrong");
            throw new Error();
        }
    }
}
