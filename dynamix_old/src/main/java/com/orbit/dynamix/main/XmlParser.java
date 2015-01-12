package com.orbit.dynamix.main;


/**
 *
 * @author Pritom K Mondal
 * @published 12th September 2013 08:04 PM
 */
public class XmlParser {

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        System.out.println("Pritom");
//        XmlParser xmlParser = new XmlParser();
//        xmlParser.parseXML("");
//    }
//
//    public Map parseXML(String xmlString) {
//        try {
////            xmlString = "<?xml version=\"1.0\"?>\n" +
////                    "<company>\n" +
////                    "   <staff id=\"1001\" class='First Class'>\n" +
////                    "       <firstname><![CDATA[Pritom in CDATA section]]></firstname>\n" +
////                    "       <lastname>Kumar Mondal</lastname>\n" +
////                    "       <nickname>pritom</nickname>\n" +
////                    "       <salary>100000</salary>\n" +
////                    "   </staff>\n" +
////                    "   <staff id=\"2001\">\n" +
////                    "       <firstname>Sajib</firstname>\n" +
////                    "       <lastname>Mondal</lastname>\n" +
////                    "       <nickname>sajib</nickname>\n" +
////                    "       <salary>200000</salary>\n" +
////                    "       <bonus>200</bonus>\n" +
////                    "   </staff>\n" +
////                    "   <member id=\"3001\">\n" +
////                    "       <lastname>Roy</lastname>\n" +
////                    "       <nickname>atanu</nickname>\n" +
////                    "       <salary>3000</salary>\n" +
////                    "       <salary>4000</salary>\n" +
////                    "       <salary><basic>2000</basic><inc>2000</inc></salary>\n" +
////                    "       <bonus>200</bonus>\n" +
////                    "       <bonus empty_cell='true'></bonus>\n" +
////                    "       <colors><c0><asCode>448d3d</asCode></c0><c1>blue</c1></colors>" +
////                    "   </member>\n" +
////                    "</company>";
//            
//            File fXmlFile = new File(System.getProperty("user.dir")
//                    + "\\xml\\OR.xml");
//
//            /* OR FROM FILE */
//            //File fXmlFile = new File("C:\\users\\pritom\\Downloads\\staff.xml");
//
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//
////            Document doc = dBuilder.parse( new ByteArrayInputStream(xmlString.getBytes()) );
//            /* OR FROM FILE */
//            Document doc = dBuilder.parse(fXmlFile);
//
//            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//
//            NodeList resultNode = doc.getChildNodes();
//
//            HashMap result = new HashMap();
//            MyNodeList tempNodeList = new MyNodeList();
//
//            String emptyNodeName = null, emptyNodeValue = null;
//
//            for(int index = 0; index < resultNode.getLength(); index ++) {
//                Node tempNode = resultNode.item(index);
//                if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
//                    tempNodeList.addNode(tempNode);
//                }
//                emptyNodeName = tempNode.getNodeName();
//                emptyNodeValue = tempNode.getNodeValue();
//            }
//
//            if (tempNodeList.getLength() == 0 && emptyNodeName != null
//                    && emptyNodeValue != null) {
//                result.put(emptyNodeName, emptyNodeValue);
//                return result;
//            }
//
//            this.parseXMLNode(tempNodeList, result);
//            System.out.println(result);
//            return result;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//
//    private void parseXMLNode(NodeList nList, HashMap result) {
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            if (nNode.getNodeType() == Node.ELEMENT_NODE
//                    && nNode.hasChildNodes()
//                    && nNode.getFirstChild() != null
//                    && (nNode.getFirstChild().getNextSibling() != null
//                    || nNode.getFirstChild().hasChildNodes())) {
//                NodeList childNodes = nNode.getChildNodes();
//                MyNodeList tempNodeList = new MyNodeList();
//                for(int index = 0; index < childNodes.getLength(); index ++) {
//                    Node tempNode = childNodes.item(index);
//                    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
//                        tempNodeList.addNode(tempNode);
//                    }
//                }
//                HashMap counterHashMap = new HashMap();
//                HashMap dataHashMap = new HashMap();
//                if (result.containsKey(nNode.getNodeName())
//                        && ((HashMap) result.get(nNode.getNodeName())).containsKey(0)) {
//                    Map mapExisting = (Map) result.get(nNode.getNodeName());
//                    Integer index = 0;
//                    if (mapExisting.containsKey(0)) {
//                        while (true) {
//                            if (mapExisting.containsKey(index)) {
//                                counterHashMap.put(
//                                        index,
//                                        mapExisting.get(index));
//                                index ++;
//                            } else {
//                                break;
//                            }
//                        }
//                    } else {
//                        result.put(nNode.getNodeName(), counterHashMap);
//                        counterHashMap.put("0", mapExisting);
//                        index = 1;
//                    }
//                    result.put(nNode.getNodeName(), counterHashMap);
//                    counterHashMap.put(index, dataHashMap);
//                } else if(result.containsKey(nNode.getNodeName())) {
//                    counterHashMap.put(0, result.get(nNode.getNodeName()));
//                    result.put(nNode.getNodeName(), counterHashMap);
//                    counterHashMap.put(1, dataHashMap);
//                } else {
//                    result.put(nNode.getNodeName(), dataHashMap);
//                }
//                if (nNode.getAttributes().getLength() > 0) {
//                    Map attributeMap = new HashMap();
//                    for(int attributeCounter = 0;
//                        attributeCounter < nNode.getAttributes().getLength();
//                        attributeCounter++) {
//                        attributeMap.put(
//                                nNode.getAttributes().item(attributeCounter).getNodeName(),
//                                nNode.getAttributes().item(attributeCounter).getNodeValue()
//                        );
//                    }
//                    dataHashMap.put("__attributes", attributeMap);
//                }
//                this.parseXMLNode(tempNodeList, dataHashMap);
//            } else if (nNode.getNodeType() == Node.ELEMENT_NODE
//                    && nNode.hasChildNodes() && nNode.getFirstChild() != null
//                    && nNode.getFirstChild().getNextSibling() == null) {
//                this.putValue(result, nNode);
//            } else if(nNode.getNodeType() == Node.ELEMENT_NODE) {
//                this.putValue(result, nNode);
//            }
//        }
//    }
//
//    private void putValue(HashMap result, Node nNode) {
//        HashMap attributeMap = new HashMap();
//        Object nodeValue = null;
//        if(nNode.getFirstChild() != null) {
//            nodeValue = nNode.getFirstChild().getNodeValue();
//            if(nodeValue != null) {
//                nodeValue = nodeValue.toString().trim();
//            }
//        }
//        HashMap nodeMap = new HashMap();
//        nodeMap.put("value", nodeValue);
//        Object putNode = nodeValue;
//        if (nNode.getAttributes().getLength() > 0) {
//            for(int attributeCounter = 0;
//                attributeCounter < nNode.getAttributes().getLength();
//                attributeCounter++) {
//                attributeMap.put(
//                        nNode.getAttributes().item(attributeCounter).getNodeName(),
//                        nNode.getAttributes().item(attributeCounter).getNodeValue()
//                );
//            }
//            nodeMap.put("__attributes", attributeMap);
//            putNode = nodeMap;
//        }
//        HashMap counterHashMap = new HashMap();
//        HashMap dataHashMap = new HashMap();
//        if (result.containsKey(nNode.getNodeName())
//                && result.get(nNode.getNodeName()) instanceof HashMap
//                && ((HashMap) result.get(nNode.getNodeName())).containsKey(0)) {
//            Map mapExisting = (Map) result.get(nNode.getNodeName());
//            Integer index = 0;
//            if (mapExisting.containsKey(0)) {
//                while (true) {
//                    if (mapExisting.containsKey(index)) {
//                        counterHashMap.put(
//                                index,
//                                mapExisting.get(index));
//                        index ++;
//                    } else {
//                        break;
//                    }
//                }
//            } else {
//                index = 1;
//            }
//            counterHashMap.put(index, putNode);
//            result.put(nNode.getNodeName(), counterHashMap);
//        } else if(result.containsKey(nNode.getNodeName())) {
//            Object existingObject = result.get(nNode.getNodeName());
//            result.put(nNode.getNodeName(), dataHashMap);
//            dataHashMap.put(0, existingObject);
//            dataHashMap.put(1, putNode);
//        } else {
//            result.put(nNode.getNodeName(), putNode);
//        }
//    }

}