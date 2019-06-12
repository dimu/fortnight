package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * simple xml read sample
 * @author dwx
 *
 */
public class ParserXmlWithDom {
	
	public static void main(String argv[]) {
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File("src/xml/book.xml"));

			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());

			NodeList listOfPersons = doc.getElementsByTagName("person");
			int totalPersons = listOfPersons.getLength();
			System.out.println("Total no of people : " + totalPersons);

			for (int s = 0; s < listOfPersons.getLength(); s++) {

				Node firstPersonNode = listOfPersons.item(s);
				if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {

					Element personElement = (Element) firstPersonNode;

					NodeList firstNameList = personElement.getElementsByTagName("first");
					Element firstNameElement = (Element) firstNameList.item(0);

					NodeList textFNList = firstNameElement.getChildNodes();
					System.out.println("First Name : " + ((Node) textFNList.item(0)).getNodeValue().trim());

					// -------
					NodeList lastNameList = personElement.getElementsByTagName("last");
					Element lastNameElement = (Element) lastNameList.item(0);

					NodeList textLNList = lastNameElement.getChildNodes();
					System.out.println("Last Name : " + ((Node) textLNList.item(0)).getNodeValue().trim());

					// ----
					NodeList ageList = personElement.getElementsByTagName("age");
					Element ageElement = (Element) ageList.item(0);

					NodeList textAgeList = ageElement.getChildNodes();
					System.out.println("Age : " + ((Node) textAgeList.item(0)).getNodeValue().trim());
				} 
			}

		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		

	}
}
