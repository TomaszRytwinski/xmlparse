import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		
		String pathOrg = "C:/DEV/LOB/org/CovTermPatternSort.ttx";
		String pathGen = "C:/DEV/LOB/output/CovTermPatternSort.ttx";
		String claimOrg = "C:/DEV/LOB/org/CovTermPattern.ttx";
		String claimGen = "C:/DEV/LOB/output/CovTermPattern.ttx";
		File file = new File(claimOrg);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		List<TypeCode> typeCodes = XmlReader.read(document);
		typeCodes = XmlReader.sort(typeCodes);
		//XmlReader.print(typeCodes);
		
		XmlReader.write(typeCodes, pathOrg);
	}

	

}
