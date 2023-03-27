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
//using JXPath to find the values in sample.json
public class SingleTest {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject object = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\small.json"));

        JXPathContext context = JXPathContext.newContext(object);
        String xpath="/keys[1]/strings[1]";

                //  /.[@name='keys'][1][@name='strings'][1] - not working
                //  /keys[1]/strings[1]  - working and get the value of the element 'a' --NB patch

                //  /.['keys'][1]['strings'][1]  - gets the entire main node
                //  *['keys'][1]['strings'][1] - working op {"strings":["a","b"]}
                //  /.[@name='keys'][1] - {"strings":["a","b"]}
                //  /.[@name='keys'][1]/*[1] - a(correct output)

                //  /.[@name='keys'][1]/strings[1]  -a(correct output)

                //  /.[@name='keys'][1]['strings'][1] - [{"strings":["a","b"]},{"strings":["c","d"]}]

        Object selectedValue = context.getValue(xpath);

        // Print the selected value
        System.out.println(selectedValue);
    }
    public static String myPathReasonable( Pointer p){
        if ( p == null ) return "" ;
        if ( !(p instanceof NodePointer) ) return p.asPath();
        NodePointer np = (NodePointer) p;
        NodePointer pp = np.getImmediateParentPointer();
        if ( pp == null ){
            return "/" ;
        }
        final StringBuilder buffer = new StringBuilder();
        final String parentPath = myPathReasonable(pp);
        buffer.append(parentPath);
        if ( buffer.length() == 0  || buffer.charAt(buffer.length() - 1) != '/') {
            buffer.append('/');
        }
        if ( pp.getValue().equals( np.getValue()) ){ // crazy, right?
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

}

