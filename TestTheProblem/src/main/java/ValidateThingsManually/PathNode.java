package ValidateThingsManually;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonPointer;
import org.apache.commons.lang3.StringUtils;

public class PathNode {

    public static void main(String[] args) {

        // JSON file path
        String filePath = "C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\ValidateThingsManually\\small.json";

        String xpathExpression = "/keys[1]";

        // Convert XPath expression to JSON Pointer
        String jsonPointer = xpathExpression.replaceAll("/", "~1").replaceAll("~", "~0").substring(1).replace("text()", "");
        System.out.println(xpathExpression);
        System.out.println(jsonPointer);
        // JSON Pointer to the desired node
       // JsonPointer pointer = JsonPointer.valueOf(jsonPointer);

        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON file and convert to JsonNode object
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Retrieve the desired node using the JSON Pointer
            JsonNode desiredNode = rootNode.at(xpathExpression);

            // Print the value of the desired node
            System.out.println("Value of node at " + xpathExpression + " : " + desiredNode.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
