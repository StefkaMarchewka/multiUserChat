import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket socket;
    InputStream inputStream;
    OutputStream output;
    ObjectOutputStream serializedOut;
    ObjectInputStream serializedIn;

    public ClientHandler(Socket socket, InputStream inStream, OutputStream outStream){
        this.socket = socket;
        this.output = outStream;
        this.inputStream = inStream;
        try {

            serializedOut = new ObjectOutputStream(output);
            serializedIn = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            PrintWriter writer = new PrintWriter(output, true);
            String receivedText = "";

            do {
                Message receivedMessage = (Message) serializedIn.readObject();
                receivedText = receivedMessage.getContent();

                //reset previously written object
                serializedOut.reset();

                ServerApp.messages.add(receivedMessage);
                int lastMessage = ServerApp.messages.size()-1;
                System.out.println("number of messages: " + lastMessage);

                //try send message to each client in list
                for (ClientHandler clientHandler: ServerApp.clients) {
                    clientHandler.serializedOut.writeObject(ServerApp.messages.get(lastMessage));
                    writer.print(clientHandler.serializedOut);
                    //todo change print into println

                }

            } while (!receivedText.equals("bye"));

             socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
