package ValidateThingsManually;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class XpathValidator {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = "C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\ValidateThingsManually\\small.json";
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader(filePath));
        JXPathContext ctx = JXPathContext.newContext(o);
        System.out.println("CTX : "+ctx.toString());
        Iterator<Pointer> iter = ctx.iteratePointers("//strings");
        while ( iter.hasNext() ){
            Pointer p = iter.next();
            String path = p.asPath();// trying to find the 'products' using pointer and jxpath
            StringBuilder pathy=new StringBuilder(path);
            System.out.println("Val : "+path);

            Pointer BeforePtr=ctx.getPointer(path); //it returns the pointer for patched and not return the pointer for normal code
            NodePointer rp = (NodePointer)BeforePtr ;
            System.out.println("Node pointer value : "+BeforePtr);
            System.out.println("BP path : "+ BeforePtr.asPath() );
            System.out.println(rp.isActual());
        }
    }
}
