package NormalTest;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zoomba.lang.core.types.ZTypes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NogaWithPatch {
    public static final Pattern NAME_PATTERN = Pattern.compile("\\[@name='(?<n>[^']+)'\\]\\[(?<i>[\\d]+)\\]" , Pattern.MULTILINE );
    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\small.json"));
        JXPathContext ctx = JXPathContext.newContext(o);
        Iterator<Pointer> iter = ctx.iteratePointers("//strings");
        while ( iter.hasNext() ){
            Pointer p = iter.next();
            String path = myPath(p);
            System.out.println(path);
            NodePointer rp = (NodePointer) ctx.getPointer(path);
            System.out.println( rp.isActual() );
            //print the value of the path

            Object selectedValue = ctx.getValue(path);

            // Print the selected value
            System.out.println(selectedValue);
        }

    }
    public static String myPath( Pointer p){
        String path = p.asPath();//using pointer and jxpath
        JXPathContext rc = JXPathContext.newContext(p.getRootNode());//trying to find the root node using JXPathContext and JPCF API
        NodePointer rec = (NodePointer)rc.getPointer(path);
        if ( rec.isActual() ) {
            System.out.println("F1 : "+path);
            return path;
        }
        else{
            System.out.println("BP : "+path);
            String r = path.substring(2);
            System.out.println("AP : "+r);
            Matcher m = NAME_PATTERN.matcher(r);
            StringBuilder buf = new StringBuilder();
            while (m.find()) {
                String nodeName = m.group("n");
                String inxName = m.group("i");
                buf.append("/").append( nodeName ).append("[").append(inxName).append("]");
            }
            System.out.println("F2 : "+ buf);
            return buf.toString();
        }
    }

}
