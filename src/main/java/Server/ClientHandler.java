package Server;

import Commons.Message;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    InputStream inputStream;
    OutputStream output;
    ObjectOutputStream serializedOut;
    ObjectInputStream serializedIn;

    public ClientHandler(Socket socket, InputStream inStream, OutputStream outStream){
        this.clientSocket = socket;
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

                serializedOut.reset();
                ServerApp.messages.add(receivedMessage);
                int lastMessage = ServerApp.messages.size()-1;
                System.out.println("number of messages: " + ServerApp.messages.size());

                for (ClientHandler clientHandler: ServerApp.clients) {
                    clientHandler.serializedOut.writeObject(ServerApp.messages.get(lastMessage));
                    writer.print(clientHandler.serializedOut);
                }

            } while (!receivedText.equals("bye"));

             clientSocket.close();
             ServerApp.clients.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
