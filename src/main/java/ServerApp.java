import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {
    private final static int PORT  = 8080;
    private static List<ClientHandler> clients = new ArrayList<>();
    //try this solution https://www.geeksforgeeks.org/multi-threaded-chat-application-set-2/

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is waiting for client");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

//                ClientHandler clientHandler = new ClientHandler(clientSocket, inputStream, outputStream);
//                clients.add(clientHandler);

                ObjectOutputStream serializedOut = new ObjectOutputStream(outputStream);
                ObjectInputStream serializedIn = new ObjectInputStream(inputStream);

                PrintWriter writer = new PrintWriter(outputStream, true);
                String receivedText = "";

                do {
                    Message receivedMessage = (Message) serializedIn.readObject();
                    receivedText = receivedMessage.getContent();

                    //reset previously written object
                    serializedOut.reset();
                    serializedOut.writeObject(receivedMessage);
                    writer.print(serializedOut);

                } while (!receivedText.equals("bye"));

                clientSocket.close();

//                ClientHandler clientHandler = new ClientHandler(clientSocket, inputStream, output);
//                Thread newThread = new Thread(clientHandler);
//                newThread.start();

            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
