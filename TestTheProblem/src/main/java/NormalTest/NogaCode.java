package NormalTest;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import javax.xml.xpath.*;

public class NogaCode {
    static int True,False;
    public static void main(String[] args) throws IOException, ParseException, XPathExpressionException {

        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\sample.json"));
        System.out.println(o);// printing the entire json
        System.out.println();
        JXPathContext ctx = JXPathContext.newContext(o);
        String items[]={"networks","id","description","productTypes","products","minAmount"};
        for(String item:items){
        Iterator<Pointer> iter = ctx.iteratePointers("//"+item);
        while ( iter.hasNext() ){
            //Finding the xpath and xpath value in json file
         try {
             Pointer p = iter.next();
             String path = PointerToPath(p);
             System.out.println("myPathReasonable : "+path);

             //Condition to remove the last letter because it throws error (i.e) it is not able to find the xpath when it has extra '/' at the end
             path=removeLastLetter(path);
             System.out.println("Altered path : "+path);

             //Checking whether the xpath value is found or not
             NodePointer rp = (NodePointer) ctx.getPointer(path);
             System.out.println("Node pointer value : " + rp);
             System.out.println(rp.isActual());


             //With the xpath, printing the value
             Object selectedValue = ctx.getValue(path);
             System.out.println(selectedValue); //The selected value in Json
             System.out.println();

            // Counting the number of xpath that can be found and cannot be found in json file
             if(rp.isActual())True++;else False++;
             // pointer generated from already produced xpath for validation
             Pointer p2=PathToPointer(ctx,path);
             //Verifying the first pointer and returned pointer are same
             verifyPointers(rp,p2);
         }
         catch (Exception e){
             e.printStackTrace();
         }
        }
        System.out.println("The items which can be found by xpath : "+True);
        System.out.println("The items which cannot be found by xpath : "+False);
        True=0;
        False=0;
        }


    }

    //external layer instead of using asPath directly
    public static String PointerToPath( Pointer p){
        if ( p == null ) return "" ;
        if ( !(p instanceof NodePointer) ) return p.asPath();
        NodePointer np = (NodePointer) p;
        NodePointer pp = np.getImmediateParentPointer();
        if ( pp == null ){
            return "/" ;
        }
        final StringBuilder buffer = new StringBuilder();
        final String parentPath = PointerToPath(pp);
        buffer.append(parentPath);
        if ( buffer.length() == 0  || buffer.charAt(buffer.length() - 1) != '/') {
            buffer.append('/');
        }
        if ( pp.getValue().equals( np.getValue()) ){
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
        if(input.charAt(input.length()-1)=='/' && input.length()>1){
            return input.substring(0, input.length() - 1);
        }
        return input;
    }

    public static NodePointer PathToPointer(JXPathContext ctx,String path) {
        //Trying to get the pointer with the help of xpath and JXPathContext
        //To put simply, takes path returns path and returns NodePointer
        NodePointer pointer= (NodePointer) ctx.getPointer(path);
        System.out.println("Checking the Node pointer : "+pointer.isActual());
        System.out.println("NodePointer : " + pointer.toString());
        return pointer;
    }
    public static  void verifyPointers(Pointer rp,Pointer p2){
        if(p2.toString().equals(rp.toString())){
            System.out.println("Both pointers are same : "+rp);
        }
        else {
            System.out.println(p2);
            System.out.println(rp);
            System.out.println("Different pointers");
        }
    }


}

//need to create a function that returns xpath by taking node value
//xpath to node

//p() -> function
//x() -> function

/*
p = "whatever" in our case it is json file
n = X(p) -> function that takes path and returns node[pointer] (PathToPointer)
pr = P(n) -> function that takes node[pointer] and return path (myPathReasonable)
//need to het the op of myPathReasonable and convert it into pointer
nr = X(pr) -> Takes the path generated and returns node (not done)
assertEquals(n,nr)
*/

// to be clear he does not want to validate using output such as a or b,
// he needs to validate the output value to the node and check the first node and last node referred are same
/*
p = "whatever" // p is a path generated
        n = X(p) // needs to generate nodes
        pr = P(n)
        nr = X(pr)
        assertEquals(n,nr)
*/
