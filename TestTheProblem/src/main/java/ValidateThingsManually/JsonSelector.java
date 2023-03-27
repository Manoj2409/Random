package ValidateThingsManually;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class JsonSelector {
    public static void main(String[] args) {
        try {
            // Create a JSONParser instance to read the JSON file
            String filePath = "C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\ValidateThingsManually\\small.json";

            JSONParser parser = new JSONParser();

            // Parse the JSON file into a JSONObject
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObj = (JSONObject) obj;

            // Select a value from the JSONObject
            String selectedValue = (String) jsonObj.get('a');

            // Print the selected value
            System.out.println(selectedValue);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
