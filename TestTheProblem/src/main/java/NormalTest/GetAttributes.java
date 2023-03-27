package NormalTest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAttributes {
    public static void main(String args[]) throws IOException {
        // Create ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();
        String FileName="C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\small.json";
        // Read JSON file into a JsonNode object
        JsonNode rootNode = mapper.readTree(new File(FileName));

        // Get the first child node of the root node
        JsonNode childNode = rootNode.elements().next();

        // Get the attribute names of the child node
        System.out.println("Attribute names of the child node:");
        for (Iterator<String> it = childNode.fieldNames(); it.hasNext(); ) {
            String attributeName = it.next();
            System.out.println(attributeName);
        }
    }
}

