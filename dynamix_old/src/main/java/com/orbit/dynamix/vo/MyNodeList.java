package com.orbit.dynamix.vo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MyNodeList implements NodeList {
	
    List<Node> nodes = new ArrayList<Node>();
    int length = 0;
    public MyNodeList() {}

    public void addNode(Node node) {
        nodes.add(node);
        length++;
    }

    public Node item(int index) {
        try {
            return nodes.get(index);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getLength() {
        return length;
    }
}
