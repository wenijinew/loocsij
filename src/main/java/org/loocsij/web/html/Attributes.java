package org.loocsij.web.html;

import java.util.Enumeration;
import java.util.Hashtable;

public class Attributes {
	private Hashtable attrs = new Hashtable();

	private Enumeration attrNames = null;

	public void addAttribute(String name, Attribute attr) {
		attrs.put(name, attr);
	}

	public Attribute getAttribute(String name) {
		return (Attribute) attrs.get(name);
	}

	public Attribute next() {
		if (hasMoreChild()) {
			return (Attribute) attrs.get(attrNames.nextElement());
		}
		return null;
	}

	public boolean hasMoreChild() {
		if (attrNames == null) {
			attrNames = attrs.keys();
		}
		return attrNames.hasMoreElements();
	}
}
