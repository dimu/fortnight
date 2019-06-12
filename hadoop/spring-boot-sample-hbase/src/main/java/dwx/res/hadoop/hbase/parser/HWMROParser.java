package dwx.res.hadoop.hbase.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dwx.res.hadoop.hbase.model.MroHW;
import dwx.res.hadoop.hbase.repository.MroHWRepository;


/**
 * mro file parser
 * @author dwx
 * 2016年10月13日
 *
 */
public class HWMROParser {
	
	public static void parserHWMro(File file) throws ParserConfigurationException, SAXException {
		try {
//			BufferedReader in2 = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
			GZIPInputStream in2 = new GZIPInputStream(new FileInputStream(file));
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(in2);

			// normalize text representation
			doc.getDocumentElement().normalize();
			NodeList eNBList = doc.getElementsByTagName("eNB");
			Node eNBnode = eNBList.item(0);
			NamedNodeMap eNBmap = eNBnode.getAttributes();
			String eNBid = eNBmap.getNamedItem("id").getNodeValue();
			
			// 解析object对象
			NodeList objectList = doc.getElementsByTagName("object");
			for(int i = 0; i < objectList.getLength(); i++) {
				Node objectNode = objectList.item(i);
				NamedNodeMap objectMap = objectNode.getAttributes();
				String mmeCode = objectMap.getNamedItem("MmeCode").getNodeValue();
				String mmeGroupId = objectMap.getNamedItem("MmeGroupId").getNodeValue();
				String mmeUeS1apId = objectMap.getNamedItem("MmeUeS1apId").getNodeValue();
				String timeStamp = objectMap.getNamedItem("TimeStamp").getNodeValue();
				String objectId = objectMap.getNamedItem("id").getNodeValue();
				System.out.println("mme info: " + eNBid + ":" + mmeCode + ":" + mmeGroupId + ":" + mmeUeS1apId + ":" + timeStamp + ":" + objectId);
				
				if (objectNode.hasChildNodes()) {
					NodeList vList = ((Element) objectNode).getElementsByTagName("v");
					for (int index = 0; index < vList.getLength(); index++) {
						Node vNode = vList.item(index);
						String vValue = vNode.getChildNodes().item(0).getNodeValue();
						if (null == vValue) {
							continue;
						}
						String[] vValueArray = vValue.split(" ");
						MroHW mroHw = new MroHW();
						mroHw.setBtsId(eNBid);
						mroHw.setLteScRSRP(vValueArray[0]);
						mroHw.setMmeUeS1apId(mmeUeS1apId);
						mroHw.setTimeStamp(timeStamp);
						MroHWRepository.put(mroHw);
					}
				}
			
			}
			//			String s;
//			while ((s = in2.readLine()) != null) {
//				System.out.println(s);
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
