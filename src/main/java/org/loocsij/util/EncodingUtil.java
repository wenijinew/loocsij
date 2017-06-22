package org.loocsij.util;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.SortedMap;
import java.util.Set;
import java.util.Iterator;

import org.apache.log4j.Logger;

import org.loocsij.logger.Log;

/**
 * just to show all supported encodings of locale system. TODO: merge
 * UnicodeEncoder and UnicodeEncrypter into this class.
 * 
 * @author GuangMing Wen
 * 
 */
public class EncodingUtil extends Charset {
	/**
	 * log instance
	 */
	private static Logger log = Log.getLogger(EncodingUtil.class);

	/** A table of hex digits */
	public static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * All possible chars for representing a number as a String
	 */
	public final static char[] digits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };

	/**
	 * constructor - extends from Charset
	 * 
	 * @param canonicalName
	 * @param aliases
	 */
	protected EncodingUtil(final String canonicalName, final String[] aliases) {
		super(canonicalName, aliases);
	}

	public boolean contains(Charset arg0) {
		throw new RuntimeException("Not Supported Method!");
	}

	public CharsetDecoder newDecoder() {
		throw new RuntimeException("Not Supported Method!");
	}

	public CharsetEncoder newEncoder() {
		throw new RuntimeException("Not Supported Method!");
	}

	/**
	 * Get Standard UNICODE Pattern - \\uHHH of specified string. For example,
	 * if input string is "I love China.", then the output will be
	 * "\u0049\u0020\u006c\u006f\u0076\u0065\u0020\u0043\u0068\u0069\u006e\u0061\u002e".
	 * .
	 * 
	 * @param str
	 *            - string value
	 * @return String - Standard UNICODE Pattern - \\uHHHH string.
	 * @author wengm
	 */
	public static String encodeToUnicodeFormat(String str) {
		char[] cs = new char[str.length()];
		str.getChars(0, str.length(), cs, 0);
		StringBuffer unicodeString = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			unicodeString.append("\\u");
			unicodeString.append(hexDigit[cs[i] >> 12 & 0xf]);
			unicodeString.append(hexDigit[cs[i] >> 8 & 0xf]);
			unicodeString.append(hexDigit[cs[i] >> 4 & 0xf]);
			unicodeString.append(hexDigit[cs[i] & 0xf]);
			// if contain invalid character,return null
			if (unicodeString.indexOf("fffd") >= 0) {
				return null;
			}
		}
		return unicodeString.toString();
	}

	/**
	 * Get readable string from given string with standard Unicode Pattern
	 * Format - \\uHHHH. For example, if input string is "\u0049\u0020\u006c\u006f\u0076\u0065\u0020\u0043\u0068\u0069\u006e\u0061\u002e", then
	 * output will be "I love China.".
	 * 
	 * @param str
	 *            - string value with standard Unicode Pattern Format - \\uHHHH.
	 * @return String - readable string.
	 * @author wengm
	 */
	public static String decodeFromUnicodeFormat(String str) {
		int length = str.length();
		char[] cs = new char[length];
		str.getChars(0, length, cs, 0);
		int iv = 0;
		char c = 0;
		str.getChars(0, length, cs, 0);
		StringBuffer commonString = new StringBuffer();
		int j = 0;
		int k = 0;
		int count = 0;
		for (int i = 0; i < length; i++) {
			if (cs[i] == '\\') {
				i++;
				if (i == length) {
					return str;
				}
				if (cs[i] == 'u') {
					i++;
					iv = 0;
					for (j = 0; j < 4; j++) {
						c = cs[i + j];
						count = 0;
						for (k = 0; k < hexDigit.length; k++) {
							if (c == hexDigit[k]) {
								c -= (hexDigit[k] - k);
								iv += c << ((3 - j) * 4);
								count = 1;
							}
						}
						if (count == 0) {
							throw new RuntimeException(
									"Malformed \\uxxxx encoding.");
						}
					}
					commonString.append((char) iv);
					i += 3;
				}
			}
		}
		return commonString.toString();
	}

	/**
	 * Get UNICODE ASCII Format(HHHH) of specified string. For example, if input
	 * string is "I love China.", then output will be
	 * "00490020006c006f007600650020004300680069006e0061002e".
	 * 
	 * @param str
	 *            - string value
	 * @return String - UNICODE ASCII Format(HHHH) of given string. If result string
	 *         contain "fffd" - invalid/garbage character, return null.
	 * @author wengm
	 */
	public static String encodeToUnicode(String str) {
		char[] cs = new char[str.length()];
		str.getChars(0, str.length(), cs, 0);
		StringBuffer unicodeString = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			unicodeString.append(hexDigit[cs[i] >> 12 & 0xf]);
			unicodeString.append(hexDigit[cs[i] >> 8 & 0xf]);
			unicodeString.append(hexDigit[cs[i] >> 4 & 0xf]);
			unicodeString.append(hexDigit[cs[i] & 0xf]);
			if (unicodeString.indexOf("fffd") >= 0) {
				return null;
			}
		}
		return unicodeString.toString();
	}

	/**
	 * Get normal string from hhhh format string.
	 * 
	 * @param str -
	 *            string value
	 * @return String - unicode asicc format of given string
	 */
	public static String decodeFromUnicode(String str) {
		int length = str.length();
		char[] cs = new char[length];
		str.getChars(0, length, cs, 0);
		int iv = 0;
		char c = 0;
		str.getChars(0, length, cs, 0);
		StringBuffer commonString = new StringBuffer();
		int j = 0;
		int k = 0;
		int count = 0;
		for (int i = 0; i < length;) {
			iv = 0;
			for (j = 0; j < 4; j++) {
				c = cs[i + j];
				count = 0;
				for (k = 0; k < hexDigit.length; k++) {
					if (c == hexDigit[k]) {
						c -= (hexDigit[k] - k);
						iv += c << ((3 - j) * 4);
						count = 1;
					}
				}
				if (count == 0) {
					throw new RuntimeException(
							"Malformed Unicode Encoding:****");
				}
			}
			commonString.append((char) iv);
			i += 4;
			if (i > length) {
				throw new RuntimeException("Malformed Unicode Encoding:****");
			}
		}
		return commonString.toString();
	}

	/**
	 * Display all available Charsets in local system, both Charsets' name and their aliases.
	 * @author wengm
	 */
	public static void showAvailableCharsets() {
		SortedMap charsets = Charset.availableCharsets();
		Set names = charsets.keySet();
		for (Iterator e = names.iterator(); e.hasNext();) {
			String name = (String) e.next();
			Charset charset = (Charset) charsets.get(name);
			//print Charset name
			log.info(charset);

			//print Charset Alias
			Set aliases = charset.aliases();
			for (Iterator ee = aliases.iterator(); ee.hasNext();) {
				log.info("    " + ee.next());
			}
		}
	}

	/**
	 * Get all supported encodings/charsets names
	 * 
	 * @author GuangMing Wen
	 * @return
	 */
	public static String[] getAllSupportedEncodings() {
		SortedMap map = Charset.availableCharsets();
		Set keys = map.keySet();
		Iterator it = keys.iterator();
		Object key = null;
		Object value = null;

		StringBuffer buf = new StringBuffer();

		int count = 0;
		String spliter = "~";
		while (it.hasNext()) {
			key = it.next();
			value = map.get(key);
			if (count > 0) {
				buf.append(spliter);
			}
			buf.append(value);
			count++;
		}
		return buf.toString().split(spliter);
	}

	public static void main(String[] strs) {
		System.out.println(encodeToUnicode("I love China."));
		System.out.println(encodeToUnicodeFormat("I love China."));
	}
}
