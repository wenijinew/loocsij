package org.loocsij.web.html;

import java.io.InputStream;
import java.io.Reader;
import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;
import org.loocsij.util.StringUtil;
import org.loocsij.web.WebAccessor;

public class HTMLParser {
	/**
	 * private assistants
	 */
	private static Logger log = Log.getLogger(StringUtil.class);

	private static boolean isDebug = false;

	/**
	 * global sort type - for get html tags
	 */
	public static final int SORT_BY_LENGTH = 1;

	public static final int SORT_BY_ALPHA = 1;

	/**
	 * global html version - for get html tags
	 */
	public static final float HTML_40 = 4.0f;

	/**
	 * Html Tags in HTML4.0 Specification and order by length. In cleaning
	 * resource, html tags should be removed from resource values,if any. <br>
	 * "BLOCKQUOTE", "!DOCTYPE", "COLGROUP", "OPTGROUP", "BASEFONT", "FRAMESET",
	 * "NOSCRIPT", "TEXTAREA", <br>
	 * "NOFRAMES", "FIELDSET", "ISINDEX", "ADDRESS", "ACRONYM", "CAPTION",
	 * "OBJECT", "SELECT", <br>
	 * "SCRIPT", "LEGEND", "APPLET", "IFRAME", "CENTER", "OPTION", "STRONG",
	 * "STRIKE", <br>
	 * "BUTTON", "SMALL", "TFOOT", "LABEL", "TBODY", "TABLE", "INPUT", "PARAM",
	 * <br>
	 * "STYLE", "FRAME", "TITLE", "THEAD", "FONT", "HEAD", "BODY", "META", <br>
	 * "MENU", "AREA", "LINK", "ABBR", "CODE", "CITE", "BASE", "FORM", <br>
	 * "HTML", "SPAN", "SAMP", "DIR", "MAP", "DFN", "KBD", "VAR", <br>
	 * "INS", "IMG", "DEL", "BIG", "COL", "BDO", "SUP", "SUB", <br>
	 * "PRE", "DIV", "H2", "H1", "EM", "DT", "UL", "DL", <br>
	 * "TT", "TR", "TH", "TD", "DD", "OL", "LI", "BR", <br>
	 * "HR", "H6", "H5", "H4", "H3", "A", "S", "Q", <br>
	 * "B", "U", "P", "I", <br>
	 * Note:!DOCTYPE can not be considered as html tag but document type
	 * declaration tag. Putting it here is for convenience.
	 */
	public static final String[] HTML40_TAGS = { "BLOCKQUOTE", "!DOCTYPE",
			"COLGROUP", "OPTGROUP", "BASEFONT", "FRAMESET", "NOSCRIPT",
			"TEXTAREA", "NOFRAMES", "FIELDSET", "ISINDEX", "ADDRESS",
			"ACRONYM", "CAPTION", "OBJECT", "SELECT", "SCRIPT", "LEGEND",
			"APPLET", "IFRAME", "CENTER", "OPTION", "STRONG", "STRIKE",
			"BUTTON", "SMALL", "TFOOT", "LABEL", "TBODY", "TABLE", "INPUT",
			"PARAM", "STYLE", "FRAME", "TITLE", "THEAD", "FONT", "HEAD",
			"BODY", "META", "MENU", "AREA", "LINK", "ABBR", "CODE", "CITE",
			"BASE", "FORM", "HTML", "SPAN", "SAMP", "DIR", "MAP", "DFN", "KBD",
			"VAR", "INS", "IMG", "DEL", "BIG", "COL", "BDO", "SUP", "SUB",
			"PRE", "DIV", "H2", "H1", "EM", "DT", "UL", "DL", "TT", "TR", "TH",
			"TD", "DD", "OL", "LI", "BR", "HR", "H6", "H5", "H4", "H3", "A",
			"S", "Q", "B", "U", "P", "I", };
	
	public static final String rootElement = "HTML";

	/**
	 * start and end sign of html tag
	 */
	static String s_sign1 = "<";

	static String s_sign2 = "</";

	static String e_sign1 = ">";

	static String e_sign2 = "/>";

	/**
	 * weed html tags from given string, return the result.
	 * 
	 * @param str -
	 *            string value
	 * @return String - result string which has been weeded html tags
	 */
	public static String weedHtmlTags(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		String beforeHtmlTag = null;
		String afterHtmlTag = null;
		if (!containHtmlTags(str)) {
			return str;
		}
		String htmlTag = getHtmlTagPortion(str);
		while (htmlTag != null && htmlTag.length() != 0) {
			beforeHtmlTag = str.substring(0, str.indexOf(htmlTag));
			afterHtmlTag = str.substring(str.indexOf(htmlTag)
					+ htmlTag.length());
			str = beforeHtmlTag + afterHtmlTag;
			htmlTag = getHtmlTagPortion(str);
		}
		if (containHtmlTags(str)) {
			if (isDebug) {
				log.info("[END] - " + str + " - " + str.length());
			}
		}
		return str;
	}

	/**
	 * judge if given string value contain html tag
	 * 
	 * @param str -
	 *            string value
	 * @return boolean - true, if given string contain html tag; else, false
	 */
	public static boolean containHtmlTags(String str) {
		String up_str = str.toUpperCase();
		for (int i = 0; i < HTML40_TAGS.length; i++) {
			if (up_str.indexOf("<" + HTML40_TAGS[i] + " ") >= 0
					|| up_str.indexOf("<" + HTML40_TAGS[i] + ">") >= 0) {
				return true;
			}
			if (up_str.indexOf("</" + HTML40_TAGS[i] + "") >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get html tag portion of the given string
	 * 
	 * @param strTemp -
	 *            string value
	 * @return String - html tag portion,null, if can not find any html tag in
	 *         given string
	 */
	public static String getHtmlTagPortion(String str) {
		String default_str = "";

		String start1 = null;
		String start2 = null;
		String htmlTag = null;

		int idx_s1 = 0;
		int idx_s2 = 0;
		int idx_e1 = 0;
		int idx_e2 = 0;

		for (int i = 0; i < HTML40_TAGS.length; i++) {
			start1 = s_sign1 + HTML40_TAGS[i];
			idx_s1 = str.indexOf(start1);
			if (idx_s1 == -1) {
				idx_s1 = str.indexOf(start1.toLowerCase());
			}
			if (idx_s1 != -1) {
				idx_e1 = str.indexOf(e_sign1, idx_s1);
				idx_e2 = str.indexOf(e_sign2, idx_s1);
				if (idx_e1 != -1) {
					if (idx_e1 + e_sign1.length() < str.length()) {
						htmlTag = str.substring(idx_s1, idx_e1
								+ e_sign1.length());
						if (htmlTag.indexOf(' ') == -1) {
							if (!htmlTag.toUpperCase().equals(start1 + e_sign1)) {
								if (isDebug) {
									log.info("(00)[Invalid Html Tag] - "
											+ htmlTag + " in - [" + str
											+ "] - [" + str + "]");
								}
								continue;
							}
						}
					} else {
						htmlTag = str.substring(idx_s1);
						if (htmlTag.indexOf(' ') == -1) {
							if (!htmlTag.toUpperCase().equals(start1 + e_sign1)) {
								if (isDebug) {
									log.info("(00)[Invalid Html Tag] - "
											+ htmlTag + " in - [" + str
											+ "] - [" + str + "]");
								}
								continue;
							}
						}
					}
					if (htmlTag.indexOf(' ') == -1) {
						if (!htmlTag.toUpperCase().equals(start1 + e_sign1)) {
							if (isDebug) {
								log
										.info("(00)[Invalid Html Tag] - "
												+ htmlTag + " in - [" + str
												+ "] - [" + str + "]");
							}
							continue;
						}
					}
					return htmlTag;
				} else if (idx_e2 != -1) {
					if (idx_e2 + e_sign2.length() < str.length()) {
						htmlTag = str.substring(idx_s1, idx_e2
								+ e_sign2.length());
					} else {
						htmlTag = str.substring(idx_s1);
					}
					if (htmlTag.indexOf(' ') == -1) {
						if (!htmlTag.toUpperCase().equals(start1 + e_sign2)) {
							if (isDebug) {
								log
										.info("(00)[Invalid Html Tag] - "
												+ htmlTag + " in - [" + str
												+ "] - [" + str + "]");
							}
							continue;
						}
					}
					return htmlTag;
				} else {
					htmlTag = str.substring(idx_s1, idx_s1 + start1.length());
					return htmlTag;
				}
			}
			start2 = s_sign2 + HTML40_TAGS[i];
			idx_s2 = str.indexOf(start2);
			if (idx_s2 == -1) {
				idx_s2 = str.indexOf(start2.toLowerCase());
			}
			if (idx_s2 != -1) {
				idx_e1 = str.indexOf(e_sign1, idx_s2);
				if (idx_e1 != -1) {
					if (idx_e1 + e_sign1.length() < str.length()) {
						htmlTag = str.substring(idx_s2, idx_e1
								+ e_sign1.length());
					} else {
						htmlTag = str.substring(idx_s2);
					}
					if (htmlTag.indexOf(' ') == -1) {
						if (!htmlTag.toUpperCase().equals(start2 + e_sign1)) {
							if (isDebug) {
								log
										.info("(00)[Invalid Html Tag] - "
												+ htmlTag + " in - [" + str
												+ "] - [" + str + "]");
							}
							continue;
						}
					}
					return htmlTag;
				} else {
					if (idx_s2 + start2.length() < str.length()) {
						htmlTag = str.substring(idx_s2, idx_s2
								+ start2.length());
					} else {
						htmlTag = str.substring(idx_s2);
					}
					return htmlTag;
				}
			}
		}
		return default_str;
	}

	/**
	 * get html tags
	 * 
	 * @param sortType -
	 *            default by {@link #SORT_BY_ALPHA}, may specify it as
	 *            {@link #SORT_BY_LENGTH} also.
	 * @param bAscOrDesc -
	 *            true, asc;false, desc
	 * @return String[] - html tags. at present, the version is 4.0
	 */
	public static String[] htmlTags(int sortType, boolean bAscOrDesc) {
		if (sortType == SORT_BY_LENGTH) {
			return StringUtil.sortByLength(HTML40_TAGS, bAscOrDesc);
		}
		return StringUtil.sortByLength(HTML40_TAGS, bAscOrDesc);
	}

	public static Document parseFile(String file) {
		return null;
	}

	/**
	 * TODO: implement html tag is the root of html file. tags in the parent tag
	 * body are all children of the parent tag.
	 * 
	 * @param fileContent
	 * @return
	 */
	public static Document parseFileContent(String fileContent) {
		if (fileContent == null || fileContent.length() == 0) {
			return null;
		}

		if (!containHtmlTags(fileContent)) {
			return null;
		}
		Document doc = new Document();
		NodeEntity rootNodeEntity = getNodeEntity(fileContent);
		if(rootNodeEntity==null) {
			return null;
		}
		Node rootNode = Node.generateNode(rootNodeEntity);
		doc.setRoot(rootNode);
		return doc;
	}
	
	/**
	 * TODO:---
	 * @param fileContent
	 * @return
	 */
	private static NodeEntity getNodeEntity(String fileContent) {
		
		return null;
	}

	public static Document parseInputStream(InputStream is) {
		return null;
	}

	public static Document parseReader(Reader is) {
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String res = WebAccessor.getWebResponse("http://www.baidu.com");
		log.info(res);
		res = weedHtmlTags(res);
		log.info(res);
	}

}
