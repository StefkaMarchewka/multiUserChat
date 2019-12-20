import Commons.ConsoleInputProvider;
import Commons.IOProvider;

public class IOFactory {

    public IOProvider getIOProvider(String type){
        if (type.equals("console"))
            return new ConsoleInputProvider();
        return null;
    }
}
