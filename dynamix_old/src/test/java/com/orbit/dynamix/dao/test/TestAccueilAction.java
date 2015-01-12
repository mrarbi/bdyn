package com.orbit.dynamix.dao.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.orbit.dynamix.utils.FragmentContentHandler;


public class TestAccueilAction {

	public static void main_(String arg[]) throws Exception {
		File file = new File(System.getProperty("user.dir") + "\\xml\\OR.xml");
		// JAXBContext jaxbContext = JAXBContext.newInstance(ReadXML.class);
		//
		// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		// ReadXML readXML = (ReadXML) jaxbUnmarshaller.unmarshal(file);
		// // for(String o: readXML.getCheckList()){
		// System.out.println(readXML);
		// // }
		// Map<String, String> map = convertNodesFromXml(file);
		// for(String o: map.keySet()){
		// System.out.println(o + "=>" + map.get(o));
		// }

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		NodeList nodeList = doc.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node textChild = nodeList.item(i);
			NodeList childNodes = textChild.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node grantChild = childNodes.item(j);
				NodeList grantChildNodes = grantChild.getChildNodes();
				for (int k = 0; k < grantChildNodes.getLength(); k++) {
					if (!StringUtils.isEmpty(grantChildNodes.item(k).getTextContent())) {
						if (grantChildNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
							// Map<String, Object> map = new HashMap<String,
							// Object>();
							// Map o = new HashMap<String, String>();
							// if(grantChildNodes.item(k).hasChildNodes()) {
							// o.put(grantChildNodes.item(k).getNodeName(),
							// grantChildNodes.item(k).getTextContent());
							// map.put(grantChildNodes.item(k).getParentNode().getNodeName(),
							// o);
							// } else {
							// String s =
							// grantChildNodes.item(k).getTextContent();
							// map.put(grantChildNodes.item(k).getParentNode().getNodeName(),
							// s);
							// }

							System.out.println(getMap(grantChildNodes.item(k)));
						}
					}
				}
			}
		}
	}

	public static Map<String, String> getMap(Node node) {
		if (node.hasChildNodes()) {
			return getMap(node);
		} else {
			Map map = new HashMap<String, String>(0);
			map.put(node.getNodeName(), node.getTextContent());
			return map;
		}
	}

	public static Map<String, String> convertNodesFromXml(File file) throws Exception {

		// InputStream is = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(file);
		return createMap(document.getDocumentElement());
	}

	public static Map<String, String> createMap(Node node) {
		Map<String, String> map = new HashMap<String, String>();
		NodeList nodeList = node.getChildNodes();
		int id = 1;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);
			if (currentNode.hasChildNodes()) {
				for (int j = 0; j < currentNode.getChildNodes().getLength(); j++) {
					Node item = currentNode.getChildNodes().item(j);
					if (null != item && item.getNodeType() == Node.ELEMENT_NODE) {
						map.put(item.getNodeName() + id, item.getTextContent());
						id++;
					}
				}
			}
			if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
				map.putAll(createMap(currentNode));
			}
			// else if (node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
			// map.put(node.getNodeName(), node.getTextContent());
			// }
		}
		return map;
	}

	public static void main0(String arg[]) {
		try {
			File inputfile = new File(System.getProperty("user.dir") + "\\xml\\OR.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
			Document doc = docbuilder.parse(inputfile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nodelist = doc.getDocumentElement().getChildNodes(); // getElementsByTagName("Object");
			System.out.println("##################################");
			for (int tmp = 0; tmp < nodelist.getLength(); tmp++) {
				Node nNode = nodelist.item(tmp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					StringBuffer elem = new StringBuffer();
					getNodeValue(eElement, elem);
					System.out.println(elem.toString());
					// NodeList subNodelist = eElement.getChildNodes();
					// int i = 0;
					// Node node = null;
					// if (null != subNodelist) {
					// while (subNodelist != null && i <
					// subNodelist.getLength()) {
					// node = subNodelist.item(i);
					// i++;
					// }
					// if (null != node) {
					// System.out.println(node.getNodeName() + ":" +
					// node.getNodeValue());
					// }
					// } else {
					// System.out.println(eElement.getNodeName() + ":" +
					// eElement.getNodeValue());
					// }
					// for (int tmpS = 0; tmpS < subNodelist.getLength();
					// tmpS++) {
					// Node nNodeS = subNodelist.item(tmpS);
					// if (nNodeS.getNodeType() == Node.ELEMENT_NODE) {
					// Element eElementS = (Element) nNodeS;
					// System.out.println(eElementS.getNodeName() + ":" +
					// eElementS.getNodeValue());
					// }
					// }
					// } else {
					// System.out.println(eElement.getNodeName() + ":" +
					// eElement.getNodeValue());
					// }
					// System.out.println("object name : "
					// + eElement.getAttribute("name"));
					// System.out.println("propertytype : "
					// + eElement.getElementsByTagName("propertytype")
					// .item(0).getTextContent());
					// System.out.println("propertyvalue: "
					// + eElement.getElementsByTagName("propertyvalue")
					// .item(0).getTextContent());
					System.out.println("--------------------------------------");
				}
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	private static void getNodeValue(Element element, StringBuffer elem) {
		NodeList subNodelist = element.getChildNodes();
		if (subNodelist != null) {
			for (int tmpS = 0; tmpS < subNodelist.getLength(); tmpS++) {
				Node nNodeS = subNodelist.item(tmpS);
				if (nNodeS.getNodeType() == Node.ELEMENT_NODE) {
					Element eElementS = (Element) nNodeS;
					getNodeValue(eElementS, elem);
					// elem.append("\n");
				}
				// else {
				// // System.out.println(nNodeS.getNodeName() + ":" +
				// nNodeS.getTextContent());
				// elem.append(nNodeS.getNodeName() + ":" +
				// nNodeS.getTextContent());
				// elem.append("\n");
				// }
			}

		}
		// else {
		// if(element.getNodeType() == Node.ELEMENT_NODE) {
		elem.append(element.getNodeName() + ":" + element.getTextContent());
		elem.append("\n");
		// }
		// }
	}

	public static void main(String[] args) throws Exception {
		File file = new File(System.getProperty("user.dir") + "\\xml\\OR.xml");

		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		List<String> list = new ArrayList<String>(0);

		FragmentContentHandler contentHandler = new FragmentContentHandler(xr, list);

		xr.setContentHandler(contentHandler);
		xr.parse(new InputSource(new FileInputStream(file)));

		System.out.println(list);

		if (null != list && !list.isEmpty()) {
			String firstElt = list.get(0);
			Pattern p = Pattern.compile("(.*?)/");
			Matcher m = p.matcher(firstElt);
			List<String> matches = new ArrayList<String>();
			String path = "//";
			while (m.find()) {
				String xPath = m.group(1);
				if (StringUtils.isNotBlank(xPath)) {
					xPath = StringUtils.replace(xPath, "[1]", "[2]");
					try {
						System.out.println(readXPath(file, path + xPath));
						path += StringUtils.replace(xPath, "[2]", "");
						break;
					} catch (SAXParseException e) {
						path += StringUtils.replace(xPath, "[2]", "[1]");
						path += "/";
						continue;
					}
				}
			}
			System.out.println(path);
		}

		/*
		 * 
		 * // read the xml
		 */
	}

	public static List<String> readXPath(File file, String expression) throws Exception, SAXException {
		List<String> list2 = new ArrayList<String>(0);
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder;
			builder = builderFactory.newDocumentBuilder();

			Document xmlDocument = builder.parse(file);

			XPath xPath = XPathFactory.newInstance().newXPath();

			// expression = "//Employees[1]/Employee[2]";

			System.out.println(expression);
			Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			xr.setContentHandler(new FragmentContentHandler(xr, list2));

			xr.parse(sourceToInputSource(new DOMSource(node)));

			System.out.println(list2);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list2;
	}

	public static void ElementToStream(Node node, OutputStream out) {
		try {
			DOMSource source = new DOMSource(node);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) {
		}
	}

	/**
	 * Utility to get the bytes uri
	 * 
	 * @param source
	 *            the resource to get
	 */
	public static InputSource sourceToInputSource(DOMSource source) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Node node = ((DOMSource) source).getNode();
		if (node instanceof Document) {
			node = ((Document) node).getDocumentElement();
		}
		Element domElement = (Element) node;
		ElementToStream(domElement, baos);
		InputSource isource = new InputSource(source.getSystemId());
		isource.setByteStream(new ByteArrayInputStream(baos.toByteArray()));
		return isource;
	}

	/**
	 * Utility to get the bytes uri. Does NOT handle authenticated URLs, use
	 * getInputSourceFromURI(uri, username, password)
	 * 
	 * @param uri
	 *            the resource to get
	 */
	public static InputSource getInputSourceFromURI(String uri) {
		return new InputSource(uri);
	}

}
