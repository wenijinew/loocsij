package org.loocsij.web.conf;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.loocsij.util.XMLUtil;
public abstract class AppConfParser {
	private Vector container = new Vector();
	private String realPath;
	public AppConfParser(String xmlFile) {
		this.realPath=AppInfo.getConfPath()+"\\"+xmlFile;
	}
	public void parse(String elementName){
		Document doc = XMLUtil.getDocument(realPath);
		Element root = doc.getDocumentElement();
		NodeList children = root.getElementsByTagName(elementName);
		Node node = null;
		for(int index=0,length=children.getLength();index<length;index++){
			node = children.item(index);
			transform(node);
		}		 
	}
	public abstract void transform(Node node);
	public static String getChildValue(Node parentNode,short childNodeType,String childNodeName,int childNodeIndex){
		Node child = getChildNode(parentNode,childNodeType,childNodeName,childNodeIndex);
		return child.getNodeValue();
	}
	public static Node getChildNode(Node parentNode,short childNodeType,String childNodeName,int childNodeIndex){
		NodeList children = parentNode.getChildNodes();
		Node child = null;
		Node returnValue = null;
		String nodeName = null;		
		short type = -1;
		int nodeIndex = 0;
		for(int index=0,length=children.getLength();index<length;index++){
			child = children.item(index);
			type = child.getNodeType();
			nodeName = child.getNodeName();			
			if(type == Node.ELEMENT_NODE){
				returnValue = child.getChildNodes().item(0);
			}
			if(type == Node.ATTRIBUTE_NODE){
				returnValue = child;
			}
			if(type==childNodeType){
				if(nodeName.equals(childNodeName) && nodeIndex==childNodeIndex){					
					return returnValue;
				}
				nodeIndex++;
			} 
		}
		return returnValue;
	}
	public Vector getContainer(){
		return this.container;		
	}
}
