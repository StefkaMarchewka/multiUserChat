import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket clientSocket;

    public ServerThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run(){

        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
