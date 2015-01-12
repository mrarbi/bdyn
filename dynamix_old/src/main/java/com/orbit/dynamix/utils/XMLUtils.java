package com.orbit.dynamix.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
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


public class XMLUtils {

	public static void main0(String[] args) throws Exception {

		File file = new File(System.getProperty("user.dir") + "\\xml\\OR.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		// InputSource is = new InputSource( new StringReader( xmlString) );
		Document doc = builder.parse(file);

		XPathFactory factory2 = XPathFactory.newInstance();
		XPath xpath = factory2.newXPath();
		// xpath.setNamespaceContext(new PersonalNamespaceContext());
		XPathExpression expr = xpath.compile("//OR[1]/page[1]/Object[2][@name='pg1obj2']");

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < nodes.getLength(); i++) {
			urls.add(nodes.item(i).getNodeValue());
			System.out.println(nodes.item(i).getNodeName() + ":" + nodes.item(i).getTextContent());
		}
	}
	
	
	
	public static String getXpath(File file) {
		String path = "";
		try {
			// File file = new File(System.getProperty("user.dir") +
			// "\\xml\\OR.xml");

			List<String> list = getXmlXpaths(file);
			System.out.println(list);

			if (null != list && !list.isEmpty()) {
				bcl1: for (String eltXml : list) {
					if (StringUtils.contains(eltXml, "/@")) {
						continue;
					}
					Pattern p = Pattern.compile("(.*?)/");
					Matcher m = p.matcher(eltXml);
					path = "//";
					while (m.find()) {
						String xPath = m.group(1);
						if (StringUtils.isNotBlank(xPath)) {
							xPath = StringUtils.replace(xPath, "[1]", "[2]");
							try {
								System.out.println(readXPath(file, path + xPath));
								path += StringUtils.replace(xPath, "[2]", "");
								break bcl1;
							} catch (SAXParseException e) {
								path += StringUtils.replace(xPath, "[2]", "[1]");
								path += "/";
								continue;
							}
						}
					}
				}
				if (StringUtils.endsWith(path, "/")) {
					path = StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "/"));
				}
				try {
					readXPath(file, path);
					if (StringUtils.endsWith(path, "[1]")) {
						path = StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "[1]"));
					}
					if (StringUtils.endsWith(path, "[2]")) {
						path = StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "[2]"));
					}
					return path;
				} catch (SAXParseException e) {
					return "";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @throws SAXException
	 */
	public static List<String> getXmlXpaths(File file) throws Exception, SAXException {
		List<String> list = new ArrayList<String>(0);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		FragmentContentHandler contentHandler = new FragmentContentHandler(xr, list);

		xr.setContentHandler(contentHandler);
		xr.parse(new InputSource(new FileInputStream(file)));

		return list;
	}

	/**
	 * 
	 * @param file
	 * @param expression
	 * @return
	 * @throws SAXException
	 * @throws Exception
	 */
	public static List<String> readXPath(File file, String expression) throws SAXException, Exception {
		List<String> list2 = new ArrayList<String>(0);
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

		return list2;
	}

	/**
	 * 
	 * @param node
	 * @param out
	 */
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
}
