package Server;

import Commons.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerApp {
    private final static int PORT = 8080;
    static Vector<ClientHandler> clients = new Vector<>();
    static Vector<Message> messages = new Vector<>();

    public static void main(String[] args) {

        try  {
            System.out.println("Server is waiting for client");
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                ClientHandler client = new ClientHandler(clientSocket, inputStream, outputStream);
                Thread thread = new Thread(client);
                clients.add(client);
                System.out.println("New client connected");
                System.out.println("Number of clients " + clients.size());

                thread.start();
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

        }
    }
}
