package org.loocsij.web.crawler;

import java.io.IOException;

import org.cyberneko.html.parsers.DOMParser;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.log4j.Logger;
import org.apache.html.dom.HTMLElementImpl;

import org.loocsij.logger.*;
import org.loocsij.web.WebAccessException;
import org.loocsij.web.WebAccessor;

/**
 * This is one spide program to extract kinds of information in Internet. It
 * accept one seed url and parse the response of this url to find more url then
 * do the same action on every found url for ever. User can restrict its depth
 * by specify the total of url.
 * 
 * @author wengm
 * 
 */
public class URLExtractor {
	private static Logger log = Log.getLogger(URLExtractor.class);

	private static String[] validElements = { "a", "img", "embed", "object",
			"frame", "iframe", "meta", "link", "script" };

	private static String[] validURLAttrs = { "href", "src", "url" };

	private static String httpProtocal1 = "http";

	private static String httpProtocal2 = "https";

	private static String htmlContentType = "text/html";

	private static String homepageExtension = "homepage";

	private static ArrayList accessedURL = new ArrayList();

	/**
	 * 
	 * @param url
	 * @param obtainedURLs
	 * @param maxNum
	 * @throws SAXException
	 */
	private static void extractHtmlURLs(String url, Hashtable obtainedURLs,
			String fileExtension, int maxNum) throws SAXException {
		extractURLs(url, obtainedURLs, httpProtocal1, htmlContentType,
				fileExtension, maxNum);
	}

	/**
	 * 
	 * @param url
	 * @param obtainedURLs
	 * @param maxNum
	 * @throws SAXException
	 */
	private static void extractURLs(String url, Hashtable obtainedURLs,
			String fileExtension, int maxNum) throws SAXException {
		extractURLs(url, obtainedURLs, null, null, fileExtension, maxNum);
	}

	/**
	 * Judge if the given url is valid. The url is not valid if following cases
	 * occurred:<br>
	 * <li>The rul can not be accessed
	 * <li>Can not get "protocol","contentType",and "file" of the url
	 * <li>if specified protocol is valid and the protocol of the url is not
	 * identical with it
	 * <li>if specified file extension is valid and there is one content type
	 * matched with it, then compare the content type with that of the url, and
	 * they are not identical; if there is no matched content type with the file
	 * extension, then compare the specified content type with that of the url
	 * directly and they are not identical
	 * <li>if specified content type is not the same as that of the url
	 * 
	 * @param url -
	 *            valid URL string
	 * @param protocol -
	 *            http,https,ftp,and file(not recommend)
	 * @param contentType -
	 *            can be got from
	 *            {@link org.loocsij.web.WebAccessor#getFileNameMap()}
	 * @param fileExtension -
	 *            can be got from
	 *            {@link org.loocsij.web.WebAccessor#getFileNameMap()}
	 * @return boolean true, if valid
	 */
	private static boolean isTargetURL(String url, String protocol,
			String contentType, String fileExtension) {
		log.debug("[isTargetURL - url:]" + url);
		WebAccessor web = WebAccessor.getInstance(url);
		if (web == null) {
			log.debug("isTargetURL - err 0");
			return false;
		}
		String pro = web.getProtocol();
		String ct = web.getContentType();
		String file = web.getFile();
		if (pro == null || ct == null || file == null) {
			log.debug("isTargetURL - err 1");
			return false;
		}
		if (protocol != null) {
			if (protocol.equals(httpProtocal1)) {
				/*
				 * default protocol - html
				 */
				if (!pro.equals(httpProtocal1) && !pro.equals(httpProtocal2)) {
					log.debug("isTargetURL - err 2");
					return false;
				}
			} else if (!pro.equals(protocol)) {
				log.debug("isTargetURL - err 3");
				return false;
			}
		}
		if (fileExtension != null) {
			/*
			 * for homepage
			 */
			if (fileExtension.equals(homepageExtension)) {
				if (file.length() > 1) {
					log.debug("isTargetURL - err 4");
					return false;
				} else {
					return true;
				}
			}
			/*
			 * check if there is one mapped content type with given file
			 * extension. if there is one mapped content type with given file
			 * extension, then compare it with content type of the url, else,
			 * compare given content type with the content type of the url.
			 */
			java.net.FileNameMap f = web.getFileNameMap();
			String tempCT = f.getContentTypeFor(fileExtension);
			if (tempCT != null) {
				String[] cts = tempCT.split(",");
				for(int i=0;i<cts.length;i++) {
					if (ct.equals(cts[i])) {
						return true;
					}
				}
				log.debug("isTargetURL - err 5 [" + ct + "] - [" + tempCT
						+ "]");
				return false;
			} else {
				if (!ct.equals(contentType)) {
					log.debug("isTargetURL - err 6");
					return false;
				}
				return true;
			}
		} else if (contentType != null) {
			if (!ct.equals(contentType)) {
				log.debug("isTargetURL - err 7");
				return false;
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param strURL
	 * @param container
	 * @return
	 */
	private static Hashtable addTargetURL(String strURL, Hashtable container) {
		WebAccessor webTemp = WebAccessor.getInstance(strURL);
		if(webTemp==null) {
			return container;
		}
		String key = webTemp.getFile();
		if (key == null || key.length() <= 1) {
			key = webTemp.getHost();
		}
		String value = strURL;
		if (container.get(key) == null) {
			log.info("[add url] - " + strURL);
			container.put(key, value);
		}
		return container;
	}

	/**
	 * judge if specified string url is relative url
	 * 
	 * @param url
	 * @return boolean - true,relative url
	 */
	private static boolean relativeURL(String url) {
		return url != null && url.indexOf("://") == -1 && !invalidURL(url);
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private static boolean invalidURL(String url) {
		return url == null || url.length() == 0 || url.startsWith("mailto:")
				|| url.startsWith("#") || url.startsWith("javascript:")
				|| url.equals("/");
	}

	/**
	 * converts relative URL to absolute URL
	 * 
	 * @param origURL
	 * @param newURL
	 * @return String
	 */
	private static String formURL(String origStrURL, String newURL) {
		WebAccessor web = WebAccessor.getInstance(origStrURL);
		if (web == null) {
			return null;
		}
//		log.info("newURL="+newURL);
		StringBuffer base = new StringBuffer(web.getProtocol());
		base.append("://").append(web.getHost());
		if (web.getPort() != -1) {
			base.append(":").append(web.getPort());
		}
		if (newURL.startsWith("/")) {
			// log.info("[r1]" + newURL);
			base.append(newURL);
		} else if (newURL.startsWith("..")) {
			// log.info("[r2]" + newURL);
			// String file = origURL.getFile();
		} else {
			// log.info("[r3]" + newURL);
			String file = web.getFile();
//			log.info("file="+file);
			int pos = file.lastIndexOf("/");
			if (pos != -1)
				file = file.substring(0, pos);
			while (newURL.startsWith("../")) {
				pos = file.lastIndexOf("/");
				file = file.substring(0, pos);
				newURL = newURL.substring(3);
			}
			base.append(file).append("/").append(newURL);
		}
		return base.toString();
	}

	/**
	 * 
	 * @param url
	 * @param container
	 * @param protocol
	 * @param contentType
	 * @param fileExtension
	 * @param maxNum
	 * @throws SAXException
	 */
	private static void extractURLs(String url, Hashtable container,
			String protocol, String contentType, String fileExtension,
			int maxNum) throws SAXException {
		/*
		 * avoid loop links
		 */
		if (accessedURL.contains(url)) {
			log.info(url + " has been accessed!");
			return;
		} else {
			accessedURL.add(url);
		}
		log.info("[url] - " + url);
		WebAccessor web = WebAccessor.getInstance(url);
		if (web == null) {
			log.info("Can not access!");
			return;
		}
		if (isTargetURL(url, protocol, contentType, fileExtension)) {
			addTargetURL(url, container);
			if (container.size() >= maxNum) {
				log.info("Have got [" + maxNum + "] urls!");
				return;
			}
		}
		HTMLDocumentImpl doc = parse(url);
		if (doc == null) {
			log.info("Can not get document by parse!");
			return;
		}
		String[] validURLs = getValidURLAttributeValues(doc);
		String tempStrURL = null;
		ArrayList subExtractURLs = new ArrayList();
		// log.debug("validURLs.length=" + validURLs.length);
		for (int i = 0; i < validURLs.length; i++) {
			// log.debug("validURLs[" + i + "]" + validURLs[i]);
			tempStrURL = validURLs[i];
			if (invalidURL(tempStrURL)) {
				continue;
			}
			if (relativeURL(tempStrURL)) {
				tempStrURL = formURL(url, tempStrURL);
				if (tempStrURL == null) {
					continue;
				}
			}
			if (tempStrURL.endsWith("/")) {
				tempStrURL = tempStrURL.substring(0, tempStrURL.length() - 1);
			}
			if (isTargetURL(tempStrURL, protocol, contentType, fileExtension)) {
				addTargetURL(tempStrURL, container);
				if (container.size() >= maxNum) {
					log.info("Have got [" + maxNum + "] urls!");
					return;
				}
			} else if (canExtract(tempStrURL)) {
				subExtractURLs.add(tempStrURL);
			} else {
				continue;
			}
		}
		if (subExtractURLs.size() > 0) {
			for (int i = 0; i < subExtractURLs.size(); i++) {
				if(container.size() < maxNum) {
					extractURLs((String) subExtractURLs.get(i), container,
							protocol, contentType, fileExtension, maxNum);
				}
			}
		}
	}

	/**
	 * add href value of anchors into specified container from specified html
	 * document.
	 * 
	 * @param doc -
	 *            HTMLDocumentImple instance, should not be null
	 * @param validElements -
	 *            container used to store urls
	 * @return ArrayList - container
	 */
	private static HTMLElementImpl[] getValidURLElement(HTMLDocumentImpl doc) {
		if (doc == null) {
			return null;
		}
		NodeList elements = null;
		HTMLElementImpl htmlElement = null;
		String elementName = null;
		ArrayList validElementsContainer = new ArrayList();
		for (int i = 0; i < validElements.length; i++) {
			elementName = validElements[i];
			elements = doc.getElementsByTagName(elementName);
			if (elements == null || elements.getLength() == 0) {
				elements = doc.getElementsByName(elementName.toUpperCase());
				if (elements == null || elements.getLength() == 0)
					continue;
			}
			for (int j = 0; j < elements.getLength(); j++) {
				if (elements.item(j) == null
						|| !(elements.item(j) instanceof HTMLElementImpl)) {
					continue;
				}
				htmlElement = (HTMLElementImpl) elements.item(j);
				if (!validElementsContainer.contains(htmlElement)) {
					validElementsContainer.add(htmlElement);
				}
			}
		}
		if (validElementsContainer.size() == 0) {
			return new HTMLElementImpl[0];
		}
		HTMLElementImpl[] veles = new HTMLElementImpl[validElementsContainer
				.size()];
		validElementsContainer.toArray(veles);
		return veles;
	}

	/**
	 * 
	 * @param doc
	 * @return
	 */
	private static String[] getValidURLAttributeValues(HTMLDocumentImpl doc) {
		HTMLElementImpl[] veles = getValidURLElement(doc);
		if (veles.length == 0) {
			log.info("No Valid Elements found from [" + doc.getDocumentElement() + "]");
			return new String[0];
		}
		ArrayList vattrs = new ArrayList();
		HTMLElementImpl element = null;
		for (int i = 0; i < veles.length; i++) {
			element = veles[i];
			String urlAttri = null;
			in: for (int j = 0; j < validURLAttrs.length; j++) {
				urlAttri = element.getAttribute(validURLAttrs[j]);
				if (urlAttri != null && urlAttri.trim().length() > 0) {
					log.debug(element + ">>" + validURLAttrs[j] + "="
							+ element.getAttribute(validURLAttrs[j]));
					vattrs.add(element.getAttribute(validURLAttrs[j]));
					break in;
				}
			}
		}
		if (vattrs.size() == 0) {
			return new String[0];
		}
		String[] strURLs = new String[vattrs.size()];
		vattrs.toArray(strURLs);
		return strURLs;
	}

	/**
	 * 
	 * @param feedURL
	 * @param maxNum
	 * @return
	 */
	public static String[] extractHtmlURLs(String feedURL,
			String fileExtension, int maxNum) {
		Hashtable hash = new Hashtable();
		try {
			extractHtmlURLs(feedURL, hash, fileExtension, maxNum);
		} catch (SAXException e) {
			log.error(new WebAccessException("Fail to extract html urls of ["
					+ feedURL + "]"), e);
			return null;
		}
		String[] strs = new String[hash.size()];
		java.util.Enumeration elements = hash.elements();
		int index = 0;
		while (elements.hasMoreElements()) {
			strs[index] = elements.nextElement().toString();
			index++;
		}
		return strs;
	}

	public static boolean canExtract(String strURL) {
		WebAccessor web = WebAccessor.getInstance(strURL);
		if (web == null) {
			return false;
		}
		String file = web.getFile();
		String[] nonHtmlFiles = {"gif"};
		if(file.length()>0) {
			for(int i=0;i<nonHtmlFiles.length;i++) {
				if(file.endsWith(nonHtmlFiles[i])) {
					return false;
				}
			}
		}
		String contentType = web.getContentType();
		return contentType != null && contentType.equals(htmlContentType);
	}

	/**
	 * extract all foundable urls with specified feedURL.
	 * 
	 * @param feedURL
	 * @param fileExtension
	 * @param maxNum
	 * @return String[]
	 */
	public static String[] extractAllURLs(String feedURL, String fileExtension,
			int maxNum) {
		Hashtable hash = new Hashtable();
		try {
			extractURLs(feedURL, hash, fileExtension, maxNum);
		} catch (SAXException e) {
			log.error(new WebAccessException("Fail to extract html urls of ["
					+ feedURL + "]"), e);
			return null;
		}
		String[] strs = new String[hash.size()];
		java.util.Enumeration elements = hash.elements();
		int index = 0;
		while (elements.hasMoreElements()) {
			strs[index] = elements.nextElement().toString();
			index++;
		}
		return strs;
	}

	/**
	 * parse specified url to get the instance of org.w3c.dom.Document which
	 * stores the content of the url in logical structure.
	 * 
	 * @param strURL
	 * @return
	 * @throws SAXException
	 */
	public static HTMLDocumentImpl parse(String strURL) throws SAXException {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(strURL);
		} catch (IOException ioe) {
			log.error(new WebAccessException("Fail to parse [" + strURL + "]"),
					ioe);
			return null;
		}
		return (HTMLDocumentImpl) parser.getDocument();
	}

	/**
	 * obtain the root element of the instantce of org.w3c.dom.Document
	 * 
	 * @param doc
	 * @return Node
	 */
	public static Node getRootNode(Document doc) {
		Element root = null;
		try {
			root = doc.getDocumentElement();
		} catch (org.w3c.dom.DOMException e) {
			log.error(new WebAccessException(
					"Fail to get document element of [" + doc + "]"), e);
			return null;
		}
		return root;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws IOException, SAXException {
		HTMLDocumentImpl doc = parse("http://www.cfan.com.cn/images/images/hardware_error_03.gif");
		if (doc == null) {
			log.info("Can not get document by parse!");
			return;
		}
		String[] validURLs = getValidURLAttributeValues(doc);
		if (validURLs != null && validURLs.length > 0) {
			for (int i = 0; i < validURLs.length; i++) {
				System.out.println(validURLs[i]);
			}
		}
	}

	/**
	 * 
	 * @param feedURL
	 * @param maxNum
	 * @return
	 */
	public static String[] extractDomains(String feedURL, int maxNum) {
		ArrayList domains = new ArrayList();
		String[] urls = extractHomePages(feedURL, maxNum);
		WebAccessor tempWeb = null;
		for (int i = 0; i < urls.length; i++) {
			tempWeb = WebAccessor.getInstance(urls[i]);
			if (tempWeb == null) {
				continue;
			}
			domains.add(tempWeb.getHost());
		}

		String[] strsDomains = new String[domains.size()];
		domains.toArray(strsDomains);
		return strsDomains;
	}

	/**
	 * 
	 * @param feedURL
	 * @param maxNum
	 * @return
	 */
	public static String[] extractHomePages(String feedURL, int maxNum) {
		return extractHtmlURLs(feedURL, homepageExtension, maxNum);
	}
}
