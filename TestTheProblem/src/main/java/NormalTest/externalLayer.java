package NormalTest;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class externalLayer {
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\small.json"));
        JXPathContext ctx = JXPathContext.newContext(o);
        Iterator<Pointer> iter = ctx.iteratePointers("//strings");
        while ( iter.hasNext() ){
            Pointer p = iter.next();
            String path = myPathReasonable(p);
            System.out.println(path);
            NodePointer rp = (NodePointer) ctx.getPointer(path);
            System.out.println( rp.isActual() );
        }
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
