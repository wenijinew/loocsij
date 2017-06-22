package org.loocsij.web.html;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Node;

public class TestHTMLDOM {
    public static void main(String[] argv) throws Exception {
        DOMParser parser = new DOMParser();
//        for (int i = 0; i < argv.length; i++) {
//            parser.parse(argv[i]);
        parser.parse("F:\\Study\\HTML����\\index.html");
            print(parser.getDocument(), "");
//        }
    }
    public static void print(Node node, String indent) {
        System.out.println(indent+node.getClass().getName());
        Node child = node.getFirstChild();
        while (child != null) {
            print(child, indent+" ");
            child = child.getNextSibling();
        }
    }
}
