package Random;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.jxpath.JXPathContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class FindAllKeys {
    //to print the entire json file and reading the first key in json file
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = "C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\Random\\small.json";
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath));
        JXPathContext context = JXPathContext.newContext(jsonObject);
        Iterator<String> keys = context.iteratePointers("//keys");
        System.out.println("Keys in the JSON file:");
        while (keys.hasNext()) {
            System.out.println(keys.next());
        }
    }
 }




/*
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
        }*/
