import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ClientApp {
    private static Client client;
    private static ConsoleInputProvider ioProvider = new ConsoleInputProvider();


    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[1]);
        createUser();

        try (Socket socket = new Socket("localhost", portNumber)) {

            OutputStream output = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String userInput;
            ObjectOutputStream serializedOut = new ObjectOutputStream(output);
            ObjectInputStream serializedIn = new ObjectInputStream(inputStream);

            do {
                //get input from user
                userInput = ioProvider.getUserInput();
                Message messageToSend = new Message(userInput, client.getClientName(), client.getClientId());

                serializedOut.reset();
                serializedOut.writeObject(messageToSend);
                writer.print(serializedOut);

                Message receivedMessage = (Message) serializedIn.readObject();
                ioProvider.printResult(receivedMessage);

            } while (!userInput.equals("bye"));


        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("I/O error: " + ex.getMessage());
        } catch (ClassNotFoundException classEx){
            System.out.println("class not found exception");
            classEx.printStackTrace();
        }
    }

    private static void createUser() {
        System.out.println("what's your name?");
        String clientName = ioProvider.getUserInput();

        UUID uuid = UUID.randomUUID();
        String randomUserId = uuid.toString();
        client = new Client(clientName, randomUserId);
    }
}
