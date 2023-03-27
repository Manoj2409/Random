package NormalTest;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.StringReader;
import java.util.Iterator;

import static org.apache.commons.jxpath.ri.model.NodePointer.WHOLE_COLLECTION;
import static org.apache.commons.jxpath.util.ValueUtils.isCollection;

public class UsingXpath {

    public static void main(String[] args) throws IOException, ParseException, XPathExpressionException, ParserConfigurationException, SAXException {
        JSONParser jsonParser = new JSONParser();
        JSONObject o = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\TestTheProblem\\src\\main\\java\\NormalTest\\sample.json"));
        JXPathContext ctx = JXPathContext.newContext(o);
        Iterator<Pointer> iter = ctx.iteratePointers("//products");
        while ( iter.hasNext() ){
            Pointer p = iter.next();
            String path = p.asPath();;
            System.out.println(path);
            NodePointer rp = (NodePointer) ctx.getPointer(path);
            System.out.println( rp.isActual() );
        }
    }


}
