package org.loocsij.web.html;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * childs of given parent node.
 * 
 * @author wengm
 * 
 */
public class Children {
	private Node parent;

	private Hashtable children = new Hashtable();
	
	private Enumeration nodeNames = null;

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int size() {
		return children.size();
	}

	public void addChild(String name, Node node) {
		children.put(name, node);
	}

	public Node getChild(String name) {
		return (Node) children.get(name);
	}
	
	public Node next() {
		if(hasMoreChild()) {
			return (Node)children.get(nodeNames.nextElement());
		}
		return null;
	}
	
	public boolean hasMoreChild() {
		if(nodeNames == null) {
			nodeNames = children.keys();
		}
		return nodeNames.hasMoreElements();
	}
}
