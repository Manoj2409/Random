package NormalTest;

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

public class Hierarchy {
    static int True,False;
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\sample.json"));
        System.out.println(o);// printing the entire json for reference
        System.out.println();
        JXPathContext ctx = JXPathContext.newContext(o);
        String Xpath = "//products";
        List<String> p = new ArrayList<>(PathFinder(ctx, Xpath));//p = "whatever"
        //Checking those paths are valid by printing its object
        ObjectsFromPath(ctx,p);

        //n = X(p)
        List<NodePointer> n=new ArrayList<>(NodesFromPointer(ctx,p));
        for(int i=0;i<n.size();i++){
            System.out.println("n : "+n.get(i));
        }

        //pr = P(n) NodePointer to path
        //Converting those node pointer to path and save path
        List<String> pr=new ArrayList<>();
        for(int i=0;i<n.size();i++)
        {
            pr.add(removeLastLetter(PointerToPath(n.get(i))));
        }
        for(int i=0;i<pr.size();i++)
        {
            System.out.println("pr : "+pr.get(i));
        }

        // nr = X(pr) convert pr to node pointer
        List<NodePointer> nr=new ArrayList<>(NodesFromPointer(ctx,pr));
        for(int i=0;i<nr.size();i++){
            System.out.println("nr : "+nr.get(i));
        }

        //Assert nr=n
        for(int i=0;i<nr.size();i++){
            System.out.println((nr.get(i).equals(n.get(i))));
            if((nr.get(i).equals(n.get(i)))){
                True++;
            }
            else {
                //return the false xpath of n and nr
                System.out.println("nr : "+removeLastLetter(PointerToPath(nr.get(i))));
                System.out.println("n : "+removeLastLetter(PointerToPath(n.get(i))));
                False++;
            }
        }
        System.out.println("True count : "+True);
        System.out.println("False count : "+False);

    }

    public static List<String> PathFinder(JXPathContext ctx, String xpath) {
        List<String> pathList = new ArrayList<>();
        Iterator<Pointer> iter = ctx.iteratePointers(xpath);
        while (iter.hasNext()) {
            //Finding the xpath and xpath value in json file
            try {
                Pointer p = iter.next();
                String path = PointerToPath(p);
                System.out.println("myPathReasonable : " + path);
                //Condition to remove the last letter because it throws error (i.e) it is not able to find the xpath when it has extra '/' at the end
                path = removeLastLetter(path);
                pathList.add(path);
                System.out.println("Altered path : " + path);
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

    public static List<NodePointer> NodesFromPointer(JXPathContext ctx,List<String> path){
        List<NodePointer> nodePointerList1=new ArrayList<>();
        for(int i=0;i< path.size();i++){
            NodePointer rp = (NodePointer) ctx.getPointer(path.get(i));
            nodePointerList1.add(rp);
        }
    return nodePointerList1;
    }
}

// relative xpath to xpath in proper form
// path to Node pointer and also save the node pointer for testing

//using those node pointer find abs xpath
//using those xpath find the node pointer again
//validate 2 and 4
/*

p = "whatever"
        n = X(p)
        pr = P(n)
        nr = X(pr)
        assertEquals(n,nr)
*/
/* a "node" is an object,
while a "node pointer" refers specifically to the reference to the next node in the list.*/
