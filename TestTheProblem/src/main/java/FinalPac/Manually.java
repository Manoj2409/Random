package FinalPac;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Manually {
    //put the xpaths of sample file in a string and print the correct xpath and its objects

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\sample.json"));
        System.out.println(o);// printing the entire json for reference
        System.out.println();
        JXPathContext ctx = JXPathContext.newContext(o);
        String Xpath[]={"//networks","//id","//description","//productTypes","//products",
                "/networks[2]/productTypes[2]/description","/networks[2]/productTypes[1]/description",
                "/networks[1]/productTypes[2]/id","/networks[2]/productTypes[2]/products[2]/minAmount"};

              for(int i=0;i<Xpath.length;i++){
                  System.out.println();
                  System.out.println((i+1)+" : ");
                  List<String> p = new ArrayList<>(PathFinder(ctx, Xpath[i]));//p = "whatever"
                  //Checking those paths are valid by printing its object
                  System.out.println("Total Items : "+PathFinder(ctx, Xpath[i]).size());
                  ObjectsFromPath(ctx,p);
              }
    }

    public static List<String> PathFinder(JXPathContext ctx, String xpath) {
        List<String> pathList = new ArrayList<>();
        Iterator<Pointer> iter = ctx.iteratePointers(xpath);
        while (iter.hasNext()) {
            //Finding the xpath and xpath value in json file
            try {
                Pointer p = iter.next();
                String path = PointerToPath(p);
                //Condition to remove the last letter because it throws error (i.e) it is not able to find the xpath when it has extra '/' at the end
                path = removeLastLetter(path);
                pathList.add(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pathList;
    }

    public static String PointerToPath(Pointer p) {
        if (p == null) return "";
        if (!(p instanceof NodePointer)) return p.asPath();
        NodePointer np = (NodePointer) p;
        NodePointer pp = np.getImmediateParentPointer();
        if (pp == null) {
            return "/";
        }
        final StringBuilder buffer = new StringBuilder();
        final String parentPath = PointerToPath(pp);
        buffer.append(parentPath);
        if (buffer.length() == 0 || buffer.charAt(buffer.length() - 1) != '/') {
            buffer.append('/');
        }
        if (pp.getValue().equals(np.getValue())) {
            return buffer.toString();
        }
        String name = np.getName().toString();
        buffer.append(name);
        final int index = np.getIndex();
        if (index != NodePointer.WHOLE_COLLECTION && np.isCollection()) {
            buffer.append('[').append(index + 1).append(']');
        }
        return buffer.toString();
    }

    public static String removeLastLetter(String input) {
        //removing the last char '/' only if it is necessary
        if (input == null || input.isEmpty()) {
            return input;
        }
        if (input.charAt(input.length() - 1) == '/' && input.length() > 1) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }

    public static void ObjectsFromPath(JXPathContext ctx, List<String> path) {
        for (int i = 0; i < path.size(); i++) {
            {
                Object selectedValue = ctx.getValue(path.get(i));
                System.out.println();
                System.out.println(selectedValue); //The selected value in Json
            }

        }
    }


}
