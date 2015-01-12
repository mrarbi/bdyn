package com.orbit.dynamix.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


public class FragmentContentHandler extends DefaultHandler {

    private String xPath = "/";
    private XMLReader xmlReader;
    private FragmentContentHandler parent;
    private StringBuilder characters = new StringBuilder();
    private Map<String, Integer> elementNameCount = new HashMap<String, Integer>();
    private List<String> list;

    public FragmentContentHandler(XMLReader xmlReader, List<String> list) {
        this.xmlReader = xmlReader;
        this.list = list;
    }

    private FragmentContentHandler(String xPath, XMLReader xmlReader, FragmentContentHandler parent) {
        this(xmlReader, parent.list);
        this.xPath = xPath;
        this.parent = parent;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        Integer count = elementNameCount.get(qName);
        if(null == count) {
            count = 1;
        } else {
            count++;
        }
        elementNameCount.put(qName, count);
        String childXPath = xPath + "/" + qName + "[" + count + "]";

        int attsLength = atts.getLength();
        for (int x=0; x<attsLength; x++) {
//            System.out.println(childXPath + "/@" + atts.getQName(x) + "=" + atts.getValue(x));
            list.add(childXPath + "/@" + atts.getQName(x) + "=" + atts.getValue(x));
        }

        FragmentContentHandler child = new FragmentContentHandler(childXPath, xmlReader, this);
        xmlReader.setContentHandler(child);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String value = characters.toString().trim();
        if(value.length() > 0) {
//            System.out.println(xPath + "=" + characters.toString());
            list.add(xPath + "=" + characters.toString());
        }
        xmlReader.setContentHandler(parent);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        characters.append(ch, start, length);
    }

}
