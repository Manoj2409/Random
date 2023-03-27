package Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

public class ReadingJson {
    //to print the entire json file and reading the first key in json file
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\Random\\sample.json";
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);
            Set<String> keys = jsonObject.keySet();
            System.out.println("Keys in the JSON file:");
            for (String key : keys) {
                System.out.println(key);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
