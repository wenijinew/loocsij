package org.loocsij.web.html;

import java.util.Hashtable;

public class Node {
	private String name;

	private String body;

	private Attributes attributes;

	private Children child;
	
	/**
	 * 
	 */
	private Hashtable descendant = new Hashtable();

	private static String w1 = " ";

	private static String w2 = "\t";

	public static String equalSign = "=";

	private static String quote1 = "\'";

	private static String quote2 = "\"";

	private static String escapeQuote1 = "\\\'";

	private static String escapeQuote2 = "\\\"";

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attrs) {
		this.attributes = attrs;
	}

	public Children getChild() {
		return child;
	}

	public void setChild(Children child) {
		this.child = child;
	}

	public String getDoby() {
		return body;
	}

	public void setDoby(String doby) {
		this.body = doby;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Node generateNode(NodeEntity ne) {
		String startTag = ne.getStartTag();
		String body = ne.getBody();
		Node node = new Node();
		String name = generateNodeName(startTag);
		String atc = generateAttrContent(startTag);
		Attributes ats = generateAttribues(atc);
		Children cld = generateChildren(body);
		node.setName(name);
		node.setAttributes(ats);
		node.setDoby(body);
		node.setChild(cld);
		return node;
	}

	private static String generateAttrContent(String startTag) {
		String attrContent = null;
		return attrContent;
	}

	private static String generateNodeName(String startTag) {
		String nodeName = null;
		return nodeName;
	}

	private static Attributes generateAttribues(String attrContent) {
		Attributes attrs = new Attributes();
		return attrs;
	}

	private static Children generateChildren(String body) {
		Children children = null;
		return children;
	}

	/**
	 * Returns true if the given character is considered to be whitespace.
	 * 
	 * @param str -
	 *            string value to be checked
	 */
	public static int indexOfWhitespace(String str) {
		int index = str.indexOf(w1);
		if (index == -1) {
			index = str.indexOf(w2);
		}
		return index;
	}

	public static int indexOfQuote(String str) {
		int index = str.indexOf(quote1);
		if (index == -1) {
			index = str.indexOf(quote2);
		}
		return index;
	}

	public static int indexOfEscapeQuote(String str) {
		int index = str.indexOf(escapeQuote1);
		if (index == -1) {
			index = str.indexOf(escapeQuote2);
		}
		return index;
	}
	
	/**
	 * @param node
	 */
	public void addDescendant(Node node) {
		String name = node.getName();
		Children children = (Children) descendant.get(name);
		if (children == null) {
			children = new Children();
		}
		children.addChild(name, node);
		descendant.put(name, children);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Children getDescendant(String name) {
		return (Children) descendant.get(name);
	}

	/**
	 * 
	 * @param name
	 * @param property
	 * @param value
	 * @return
	 */
	public Children getDescendant(String name, String property, String value) {
		Children children = (Children) descendant.get(name);
		if (children == null) {
			return null;
		}
		Children result = new Children();
		Node child = null;
		Attributes attrs = null;
		Attribute attr = null;
		String an = null;
		String av = null;
		while (children.hasMoreChild()) {
			child = children.next();
			attrs = child.getAttributes();
			if (attrs == null) {
				continue;
			}
			in: while (attrs.hasMoreChild()) {
				attr = attrs.next();
				an = attr.getName();
				av = attr.getValue();
				if (!an.equals(property)) {
					continue;
				}
				if (!av.equals(value)) {
					continue;
				}
				result.addChild(name, child);
				break in;
			}
		}
		return result;
	}
}
