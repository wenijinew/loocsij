/**
 * XMLUtil.java-�ṩ��xml�ļ�����Ļ�������
 */
package org.loocsij.util;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

/**
 * 1.DOM���<br />
 * Ŀǰ��W3C����2000��11��13���Ƴ��˹淶DOM level 2��<br />
 * �ĵ�����ģ�ͣ�DOM����HTML��XML�ĵ��ı�̽ӿڹ淶��<br />
 * ����ƽ̨���������޹صģ���������ø��������ڸ���ƽ̨��ʵ�֡�<br />
 * ��ģ�Ͷ�����THML��XML�ļ����ڴ��е��߼��ṹ����Ϊ�ĵ������ṩ�˷��ʡ���ȡTHML��XML�ļ��ķ�����<br />
 * ����DOM�淶������ʵ��DOM �ĵ���XML֮����໥ת����������������ӦDOM�ĵ������ݡ�<br />
 * ����˵��Ҫ���ɵĲ���XML�ļ�����Ҫ�õ�DOM�淶��<br />
 * 2. DOM�ڲ��߼��ṹ<br />
 * DOM�ĵ��е��߼��ṹ�����ýڵ�������ʽ���б�����<br />
 * ͨ����XML�ļ��Ľ�������XML�ļ��е�Ԫ�ر�ת��ΪDOM�ĵ��еĽڵ����<br />
 * DOM���ĵ��ڵ���Document��Element��Comment��Type�ȵȽڵ����ͣ�<br />
 * ����ÿһ��DOM�ĵ�������һ��Document�ڵ㣬����Ϊ�ڵ����ĸ��ڵ㡣<br />
 * ���������ӽڵ㣬����Ҷ�ӽڵ���Text�ڵ㡢Comment�ڵ�ȡ�<br />
 * �κεĸ�ʽ���õ�XML�ļ��е�ÿһ��Ԫ�ؾ���DOM�ĵ��е�һ���ڵ�������֮��Ӧ��<br />
 * ����DOM�ӿڽ�XML�ļ�ת����DOM�ĵ������ǾͿ������ɵĴ���XML�ļ��ˡ�<br />
 * 3. java�е�DOM�ӿ�<br />
 * DOM�淶�ṩ��API�Ĺ淶��ĿǰSun��˾�Ƴ���jdk1.4���԰��е�java API��ѭ�� DOM level 2
 * Core�Ƽ��ӿڵ�����˵�����ṩ����Ӧ��java���Ե�ʵ�֡�<br />
 * ��org.xml.dom�У�jkd1.4�ṩ��Document��DocumentType��Node��NodeList��Element��Text�Ƚӿڣ���Щ�ӿھ��Ƿ���DOM�ĵ�������ġ�<br />
 * ���ǿ���������Щ�ӿڴ������������޸�DOM�ĵ���<br />
 * ��javax.xml.parsers�У�jkd1.4�ṩ��DoumentBuilder��DocumentBuilderFactory��Ͽ��Զ�XML�ļ����н�����ת����DOM�ĵ���<br />
 * ��javax.xml.transform.dom��javax.xml.transform.stream�У�jdk1.4�ṩ��DOMSource���StreamSource�࣬<br />�������������º��DOM�ĵ�д�����ɵ�XML�ļ��С�
 * 
 * @author wengm
 * @version 1.0.1 
 */
public class XMLUtil {

	/**
	 * 
	 */
	public XMLUtil() {
		super();
	}

	/**
	 * ��XML�ļ�ת����DOM�ĵ�<br>
	 * ��������ǻ��һ��XML�ļ�������������XML�ļ�ת����DOM�ĵ��Ĺ��̡�<br />
	 * Jdk1.4�У�Document�ӿ������˶�Ӧ������XML�ļ����ĵ������ṩ�˶��ĵ����ݵķ��ʣ��Ǹò����Ŀ�ꡣ<br />
	 * Document�ӿڿ��Դ���DocumentBuilder�л�ȡ����������˴�XML�ĵ����DOM�ĵ�ʵ����API��<br />
	 * XML�Ľ��������Դ���DocumentBuilderFactory�л�ȡ��<br />
	 * 
	 * @param xmlFile
	 * @return Document
	 * @throws Exception
	 */
	public static Document getDocument(String xmlFile) {
		/*
		 * ���һ��XML�ļ��Ľ�����(DocumentBuilder)
		 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			throw new RuntimeException("Error(001),cause:" + pce);
		}
		/*
		 * ����XML�ļ�����DOM�ĵ��Ľӿ���(Document)���Ա����DOM��
		 */
		Document document = null;
		try {
			document = builder.parse(new File(xmlFile));
		} catch (IOException ioe) {
			throw new RuntimeException("Error(002),cause:" + ioe);
		} catch (SAXException saxe) {
			throw new RuntimeException("Error(003),cause:" + saxe);
		}
		return document;
	}

	/**
	 * ��DOM�ĵ�ת�����ƶ��ļ�����XML�ļ�
	 * 
	 * @param document
	 * @param FileName
	 */
	public static void getXmlFile(Document document, String FileName){
		try {
			/*
			 * ��ý�DOM�ĵ�ת��ΪXML�ļ���ת����
			 */
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			/*
			 * ��DOM����ת��ΪDOMSource�����. �ö������Ϊת���ɱ�ı����ʽ����Ϣ������
			 */
			DOMSource source = new DOMSource(document);
			/*
			 * ���һ��StreamResult�����. �ö�����DOM�ĵ�ת���ɵ�������ʽ���ĵ�������.
			 * ������XML�ļ����ı��ļ���HTML�ļ�������Ϊһ��XML�ļ���
			 */
			StreamResult result = new StreamResult(new File(FileName));
			/*
			 * ����API����DOM�ĵ�ת����XML�ļ���
			 */
			transformer.transform(source, result);
		} catch (TransformerConfigurationException tce) {
			throw new RuntimeException("Error(001),cause:" + tce);
		} catch (TransformerException te) {
			throw new RuntimeException("Error(002),cause:" + te);
		}
	}

	/**
	 * ��Ĭ���ļ�������xml�ļ�
	 * 
	 * @param document
	 * @throws RuntimeException
	 */
	public static void getXmlFile(Document document) {
		getXmlFile(document, "manager.xml");
	}

	/**
	 * ��DOM�ĵ�����ӽڵ�(ʾ������)
	 * 
	 * @param document
	 */
	public static void addNode(Document document) {
		Element root = document.getDocumentElement();
		Element books = document.createElement("COMPUTES");
		root.appendChild(books);
		Element infor = document.createElement("Title");
		books.appendChild(infor);
		Text text = document.createTextNode("understand XML");
		infor.appendChild(text);
		infor = document.createElement("Writer");
		books.appendChild(infor);
		text = document.createTextNode("IBM");
		infor.appendChild(text);
		infor = document.createElement("Date");
		books.appendChild(infor);
		text = document.createTextNode("2001.8.12");
		books.appendChild(infor);
	}

	public static NodeList getChildren(Document document) {
		Element root = document.getDocumentElement();
		NodeList children = root.getChildNodes();
		return children;
	}

	/**
	 * �ݹ鷨��ʾ����Ҷ�ӽڵ�����
	 * 
	 * @param children
	 */
	public static void showAll(NodeList children) {
		Node cnode;
		int i;
		String str;
		int len;

		if (children.getLength() == 0) {
			return;
		}
		for (i = 0; i < children.getLength(); i++) {
			cnode = children.item(i);
			if (cnode.getNodeType() == 1) {
				System.out.println(cnode.getNodeName());
				showAll(cnode.getChildNodes());
			} else if (cnode.getNodeType() == 3) {
				str = cnode.getNodeValue();
				len = str.length();
				if (len > 1)
					System.out.println("      " + str + " " + len);
			}
		}

	}

	/**
	 * ����������DOM�ĵ�
	 * 
	 * @param document
	 */
	public static void showAll(Document document) {
		NodeList children = getChildren(document);
		showAll(children);
	}

	/**
	 * Test!
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		Document document = XMLUtil.getDocument("/xml project/order.xml");
		XMLUtil.showAll(document);
		System.out.println(document.getDoctype());
	}

}

