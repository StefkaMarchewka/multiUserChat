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
                    try {
                        InputStream inputStream = socket.getInputStream();
                        ObjectInputStream serializedIn = new ObjectInputStream(inputStream);


                        while (true) {
                            try {
                                Message receivedMessage;
                                Object receivedObj;

                                while ((receivedObj = serializedIn.readObject()) != null) {
                                    receivedMessage = (Message) receivedObj;
                                    ioProvider.printMessage(receivedMessage);
                                };
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (IOException e) {
                    e.printStackTrace();
                }

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

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            sendMessage.start();
            readMessage.start();


        } catch (Exception ex) {
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
