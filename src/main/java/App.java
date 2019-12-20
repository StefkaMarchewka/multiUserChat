import Client.ClientApp;
import Commons.IOProvider;


public class App {
    public static void main(String[] args) {
        ConfigResolver config = new ConfigResolver();
        IOFactory factory = new IOFactory();
        String appType = config.getType();

        IOProvider io = factory.getIOProvider(appType);
        ClientApp client = new ClientApp(io);
        client.run(args);
    }
}
