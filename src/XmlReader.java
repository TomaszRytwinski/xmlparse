import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {

	public static List<TypeCode> read(Document document) {
		List<TypeCode> typeCodes = new ArrayList<TypeCode>();
		TypeCode temp = new TypeCode();
		Category category = new Category();
		NodeList listTypeCode = document.getElementsByTagName("typecode");
		int left = 0;
		for (int i = 0; i < listTypeCode.getLength(); i++) {
			Node codeTypeNode = listTypeCode.item(i);
			temp = new TypeCode();
			if (listTypeCode.item(i).getAttributes().getLength() != 0) {
				if (listTypeCode.item(i).getAttributes().getNamedItem("code") != null) {
					temp.typeCodeProp.add(listTypeCode.item(i).getAttributes().getNamedItem("code").getTextContent());
				}
				if ((listTypeCode.item(i).getAttributes().getNamedItem("desc") != null)) {
					temp.typeCodeProp.add(listTypeCode.item(i).getAttributes().getNamedItem("desc").getTextContent());
				}
				if ((listTypeCode.item(i).getAttributes().getNamedItem("name") != null)) {
					temp.typeCodeProp.add(listTypeCode.item(i).getAttributes().getNamedItem("name").getTextContent());
				}
			}
			if (codeTypeNode.getNodeType() == Node.ELEMENT_NODE) {
				left = listTypeCode.getLength() - i;
				Element typeCodeElement = (Element) codeTypeNode;
				NodeList codeElementList = typeCodeElement.getElementsByTagName("category");
				for (int j = 0; j < codeElementList.getLength(); j++) {
					if (codeElementList.item(j).getAttributes().getLength() != 0) {
						if (codeElementList.item(j).getAttributes().getNamedItem("code") != null) {
							category.categoryProperties
									.add(codeElementList.item(j).getAttributes().getNamedItem("code").getTextContent());
						}
						if (codeElementList.item(j).getAttributes().getNamedItem("typelist") != null) {
							category.categoryProperties
									.add(codeElementList.item(j).getAttributes().getNamedItem("typelist").getTextContent());
						}
					}
					temp.category.add(category);
					category = new Category();
				}
				typeCodes.add(temp);
			}
		}
		return typeCodes;
	}

	public static void print(List<TypeCode> typeCodes) {
		for (int i = 0; i < typeCodes.size(); i++) {
			System.out.println(typeCodes.get(i).typeCodeProp + " ");
			for (int j = 0; j < typeCodes.get(i).category.size(); j++) {
				System.out.print(" " + typeCodes.get(i).category.get(j).categoryProperties);
			}
			System.out.println("");
		}
	}

	public static List<TypeCode> sort(List<TypeCode> typeCodes) {
		Collections.sort(typeCodes, new CustomComparator());
		for(int i=0;i<typeCodes.size();i++){
			if(typeCodes.get(i).category!=null && typeCodes.get(i).category.size()!=0)
			typeCodes.get(i).sortCategory();
		}
		return typeCodes;

	}

	public static void write(List<TypeCode> typeCodes, String path) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			Element typeCode;
			Element typeCategory;
			Element rootElement  = document.createElement("typelist");
			document.appendChild(rootElement);
			for(int i=0;i<typeCodes.size();i++){
				typeCode = document.createElement("typecode");
				typeCode.setAttribute("code", typeCodes.get(i).typeCodeProp.get(0));
				typeCode.setAttribute("desc", typeCodes.get(i).typeCodeProp.get(1));
				typeCode.setAttribute("name", typeCodes.get(i).typeCodeProp.get(2));
				rootElement.appendChild(typeCode);
				if(typeCodes.get(i).category !=null && !typeCodes.get(i).category.isEmpty()){
					for(int j=0;j<typeCodes.get(i).category.size();j++){
						typeCategory = document.createElement("category");
						typeCategory.setAttribute("code", typeCodes.get(i).category.get(j).categoryProperties.get(0));
						typeCategory.setAttribute("typelist", typeCodes.get(i).category.get(j).categoryProperties.get(1));
						typeCode.appendChild(typeCategory);
					}
					

				}

				
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(path));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes"); 
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

}
