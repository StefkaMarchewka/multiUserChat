import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    private final static int PORT  = 8080;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is waiting for client");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                InputStream inputStream = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                ObjectOutputStream serializedOut = new ObjectOutputStream(output);
                ObjectInputStream serializedIn = new ObjectInputStream(inputStream);

                PrintWriter writer = new PrintWriter(output, true);
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
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

}
