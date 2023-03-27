package NormalTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class UsingJSPath {
    public static void main(String args[]) {
        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\sample.json"));
            String id = (String) jsonObject.get("strings[0]");
            System.out.println(id);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}