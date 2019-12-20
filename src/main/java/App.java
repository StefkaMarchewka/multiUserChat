import Client.ClientApp;
import Commons.IOProvider;


public class App {
    public static void main(String[] args) {
        IOFactory factory = new IOFactory();
        IOProvider io = factory.getIOProvider("console");
        ClientApp client = new ClientApp(io);
        client.run(args);
    }
}
