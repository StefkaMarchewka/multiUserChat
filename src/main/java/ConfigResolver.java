import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ConfigResolver {

    private static final String PATH = "/src/main/resources/config.json";

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

    private static JSONObject getJsonObjectFromConfigFile() throws IOException, ParseException {
        FileReader fileReader = new FileReader(PATH);
        JSONParser jsonParser = new JSONParser();

        Object object = jsonParser.parse(fileReader);

        return (JSONObject) object;
    }
}
