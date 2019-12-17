public class IOFactory {

    public IOProvider getIOProvider(String type){
        if (type.equals("console"))
            return new ConsoleInputProvider();

//        else if (type.equals("web"))
//            return new WebIOProvider;
        return null;
    }
}
