public class App {
    public static void main(String[] args) {
        ConfigResolver config = new ConfigResolver();
        IOFactory factory = new IOFactory();
        String appType = config.getType();

        factory.getIOProvider(appType);


    }
}
