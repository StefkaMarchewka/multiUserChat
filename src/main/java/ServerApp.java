import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    private final static int PORT = 8080;
    private static List<ClientHandler> clients = new ArrayList<>();
    //try this solution https://www.geeksforgeeks.org/multi-threaded-chat-application-set-2/

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is waiting for client");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                new ServerThread(clientSocket).start();


            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

        }

    }
}
