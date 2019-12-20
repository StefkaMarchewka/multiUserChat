import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ConfigResolver {

    private static final String PATH = "config.json";

    public ConfigResolver(){
        System.out.println("config resolver created");

    }

    public String getType(){
        JSONObject json = null;
        try{
            json = getJsonObjectFromConfigFile();
        }catch (IOException ioEx){
            ioEx.printStackTrace();
        }catch (ParseException parseEx){
            parseEx.printStackTrace();
        }
        return  (String) json.get("type");
    }

    private JSONObject getJsonObjectFromConfigFile() throws IOException, ParseException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("config.json");
        FileReader fileReader = new FileReader(new File(resource.getFile()));
        JSONParser jsonParser = new JSONParser();

        Object object = jsonParser.parse(fileReader);

        return (JSONObject) object;
    }
}
