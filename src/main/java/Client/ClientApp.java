package Client;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

import Commons.IOProvider;
import Commons.Message;

public class ClientApp {
    private Client client;
    private IOProvider ioProvider;

    public ClientApp(IOProvider ioProvider){
        this.ioProvider = ioProvider;
    }

    public void run(String[] args) {
        int portNumber = Integer.parseInt(args[0]);
        createUser();

        try{
            Socket socket = new Socket("localhost", portNumber);
            Thread readMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isRunning = true;

                    try {
                        InputStream inputStream = socket.getInputStream();
                        ObjectInputStream serializedIn = new ObjectInputStream(inputStream);

                        while (isRunning) {
                            try {
                                Message receivedMessage;
                                Object receivedObj;

                                while ((receivedObj = serializedIn.readObject()) != null) {
                                    receivedMessage = (Message) receivedObj;
                                    ioProvider.printMessage(receivedMessage);
                                };
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            if(Thread.interrupted()){
                                isRunning = false;
                            }
                        }
                        socket.close();

                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    System.out.println("read message stopped");
                }
            });

            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OutputStream outputStream = socket.getOutputStream();
                        ObjectOutputStream serializedOut = new ObjectOutputStream(outputStream);

                        String userInput = "";
                        do {
                            try {
                                userInput = ioProvider.getUserInput();
                                Message messageToSend = new Message(userInput, client.getClientName(), client.getClientId());

                                serializedOut.reset();
                                serializedOut.writeObject(messageToSend);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } while (!userInput.equals("bye"));
                        readMessage.interrupt();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("send message stopped");
                }
            });

            sendMessage.start();
            readMessage.start();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void createUser() {
        System.out.println("what's your name?");
        String clientName = ioProvider.getUserInput();

        UUID uuid = UUID.randomUUID();
        String randomUserId = uuid.toString();
        client = new Client(clientName, randomUserId);
    }
}
