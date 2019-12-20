package Commons;

import java.util.Scanner;

public class ConsoleInputProvider implements IOProvider {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void printMessage(Message message){
        System.out.println(message.getClientName() + "> " + message.getContent());
    }
}
