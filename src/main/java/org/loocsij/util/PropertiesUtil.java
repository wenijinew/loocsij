package org.loocsij.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Properties;

//import org.apache.log4j.Logger;

import org.loocsij.SystemProperties;
import org.loocsij.logger.*;

public class PropertiesUtil {
	private static SimpleLogger log = Log.getSimpleLogger(PropertiesUtil.class);

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static final String specialSaveChars = "=: \t\r\n\f#!";

	public static void showProperties(String path, String kfilter,
			String vfilter) {
		java.util.Properties prop = getPropertiesFromFile(path);
		showProperties(prop, kfilter, vfilter);
	}

	public static void readProperties(String path, String kfilter,
			String vfilter) {
		try {
			String[] lines = FileUtil.lines(path, "iso-8859-1");
			String line = null;
			String[] kv = null;
			String key = null;
			String value = null;
			int count = 0;
			// java.util.ArrayList al = new java.util.ArrayList();
			// int c = 0;
			for (int i = 0; i < lines.length; i++) {
				count++;
				line = lines[i];
				kv = line.split("=");
				if (kv.length > 0) {
					key = kv[0];
					key = loadConvert(key);
				}
				if (kv.length > 1) {
					value = kv[1];
					value = loadConvert(value);
				}
				// while(key.indexOf("<")>=0) {
				// int st = key.indexOf("<");
				// int ed = key.indexOf(">",st);
				// String s = null;
				// if(ed==-1) {
				// s = key.substring(st);
				// }else {
				// s = key.substring(st,ed+1);
				// }
				// if(!al.contains(s)) {
				// al.add(s);
				// log.info("["+c+"]"+s);
				// c++;
				// }
				// key = key.substring(key.indexOf(s)+s.length());
				// }
				if (vfilter != null && vfilter.length() > 0) {
					if (value.indexOf(vfilter) != -1) {
						log.info("[" + count + "]" + key + "=" + value);
					}
				} else if (kfilter != null && kfilter.length() > 0) {
					if (key.indexOf(kfilter) != -1) {
						log.info("[" + count + "]" + key + "=" + value);
					}
				} else {
					if (key.indexOf("<") >= 0) {
						log.info("[" + count + "]" + key + "=" + value);
					}
				}
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	/*
	 * Converts unicodes to encoded &#92;uxxxx and writes out any of the
	 * characters in specialSaveChars with a preceding slash
	 */
	public static String saveConvert(String theString, boolean escapeSpace) {
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len * 2);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');

				outBuffer.append(' ');
				break;
			case '\\':
				outBuffer.append('\\');
				outBuffer.append('\\');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			default:
				if ((aChar < 0x0020) || (aChar > 0x007e)) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					if (specialSaveChars.indexOf(aChar) != -1)
						outBuffer.append('\\');
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * Convert a nibble to a hex character
	 * 
	 * @param nibble
	 *            the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/*
	 * Converts encoded &#92;uxxxx to unicode chars and changes special saved
	 * chars to their original forms
	 */
	public static String loadConvert(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len; x++) {
			aChar = theString.charAt(x);
			if (aChar == '\\') {
				aChar = theString.charAt(x);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public static void showProperties(Properties prop, String kfilter,
			String vfilter) {
		java.util.Enumeration en = prop.keys();
		int count = 0;
		String key = null;
		String value = null;
		while (en.hasMoreElements()) {
			key = en.nextElement().toString();
			value = prop.getProperty(key);
			count++;
			if (kfilter != null && kfilter.length() > 0) {
				if (key.indexOf(kfilter) >= 0) {
					log.info("[" + count + "]" + key + "=" + value);
				}
			} else if (vfilter != null && vfilter.length() > 0) {
				if (value.indexOf(vfilter) >= 0) {
					log.info("[" + count + "]" + key + "=" + value);
				}
			} else {
				// if (containHtmlTags(key)) {
				// log.info("[" + count + "]" + key + "=" + value);
				// }
				log.info("[" + count + "]" + key + "="
						+ EncodingUtil.decodeFromUnicodeFormat(value));
			}
		}
	}

	static final String[] HTML40_TAGS = { "A", "ABBR", "ACRONYM", "ADDRESS",
			"AREA", "B", "BASE", "BDO", "BIG", "BLOCKQUOTE", "BODY", "BR",
			"BUTTON", "CAPTION", "CITE", "CODE", "COL", "COLGROUP", "DD",
			"DEL", "DFN", "DIV", "DL", "DT", "EM", "FIELDSET", "FORM", "H1",
			"H2", "H3", "H4", "H5", "H6", "HEAD", "HR", "HTML", "I", "IMG",
			"INPUT", "INS", "KBD", "LABEL", "LEGEND", "LI", "LINK", "MAP",
			"META", "NOSCRIPT", "OBJECT", "OL", "OPTGROUP", "OPTION", "P",
			"PARAM", "PRE", "Q", "SAMP", "SCRIPT", "SELECT", "SMALL", "SPAN",
			"STRONG", "STYLE", "SUB", "SUP", "TABLE", "TBODY", "TD",
			"TEXTAREA", "TFOOT", "TH", "THEAD", "TITLE", "TR", "TT", "UL",
			"VAR", "!DOCTYPE" };

	public static boolean containHtmlTags(String str) {
		String up_str = str.toUpperCase();
		for (int i = 0; i < HTML40_TAGS.length; i++) {
			if (up_str.indexOf("<" + HTML40_TAGS[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static void showPropertiesFiles(String[] paths, String kfilter,
			String vfilter) {
		for (int i = 0; i < paths.length; i++) {
			System.out
					.println("================================================================================");
			log.info("	" + paths[i] + "	");
			System.out
					.println("================================================================================");
			showProperties(paths[i], kfilter, vfilter);
		}
	}

	/**
	 * combine two properties together
	 * 
	 * @author GuangMing Wen
	 * @param origin
	 * @param obj
	 * @return Properties - combined result properties.
	 */
	public static Properties combineProperties(Properties origin, Properties obj) {
		Enumeration e = obj.keys();
		String key = null;
		String v = null;
		while (e.hasMoreElements()) {
			key = e.nextElement().toString();
			v = obj.getProperty(key);
			origin.put(key, v);
		}
		return origin;
	}

	/**
	 * get properties object which loaded content from common file rely on
	 * specified locale
	 * 
	 * @author GuangMing Wen
	 * @param fileName
	 * @param locale
	 * @return Properties of specified locale,if the file is not exists,return
	 *         null
	 */
	public static Properties getPropertiesFromFile(String fileName) {
		if (fileName == null || fileName.trim().length() == 0) {
			return null;
		}
		File f = new File(fileName);
		if (!f.exists()) {
			FileUtil.createFile(fileName);
			return new Properties();
		}
		FileInputStream in = null;
		Properties prop = new Properties();
		try {
			in = new FileInputStream(f);
			prop.load(in);
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.getMessage(), fnfe);
			return prop;
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return prop;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
					return prop;
				}
			}
		}

		return prop;
	}

	public static String getProperty(String fileName, String key) {
		Properties p = getPropertiesFromFile(fileName);
		return p == null ? null : p.get(key).toString();
	}

	/**
	 * get string content of properties
	 * 
	 * @author GuangMing Wen
	 * @param p
	 * @return
	 */
	public static String toString(Properties p, boolean unicodePattern) {
		Enumeration e = p.keys();
		String k = null;
		String v = null;
		StringBuffer buf = new StringBuffer();
		while (e.hasMoreElements()) {
			k = e.nextElement().toString();
			v = p.getProperty(k);
			if (unicodePattern) {
				v = EncodingUtil.encodeToUnicodeFormat(v);
			}
			buf.append(k).append("=").append(v).append(
					SystemProperties.sLineSeparator);
		}
		return buf.toString();
	}

	/**
	 * Write properties object into properties file.
	 * 
	 * @author GuangMing Wen
	 * @param p
	 * @param locale
	 */
	public static void storeProperties(Properties prop, String fileName,
			String charset, boolean append, boolean unicodePattern) {
		FileOutputStream fops = null;
		try {
			fops = new FileOutputStream(fileName, append);
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.getMessage(), fnfe);
			return;
		}
		OutputStreamWriter writer = new OutputStreamWriter(fops);
		String content = toString(prop, unicodePattern);
		try {
			writer.write(content);
			writer.flush();
			// prop.store(fops, null);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		} finally {
			try {
				writer.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage(), ioe);
				return;
			}
			if (fops != null) {
				try {
					fops.close();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
					return;
				}
			}
		}
	}

	public static void main(String[] strs) {
		showProperties(strs[0], "", "");
	}
}
