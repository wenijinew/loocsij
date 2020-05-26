package org.loocsij.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;

/**
 * provide methos of processing strings.
 * 
 * @author wengm
 * 
 */
public class StringUtil {

	private static Logger log = Log.getLogger(StringUtil.class);

	public static final String wordSeparator = "~";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat();

	private static DecimalFormat numberFormat = new DecimalFormat();

	/**
	 * replace all specified substrings in specified string with specified
	 * string
	 * 
	 * @param str
	 * @param sub
	 * @param with
	 */
	public static String replace(String str, String sub, String with) {
		if (isEmpty(str) || isEmpty(sub) || isEmpty(with)) {
			log.error("Invalid arguments.");
			return str;
		}
		int c = 0;
		int i = str.indexOf(sub, c);
		if (i == -1) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length() + with.length());
		do {
			buf.append(str.substring(c, i));
			buf.append(with);
			c = i + sub.length();
		} while ((i = str.indexOf(sub, c)) != -1);
		if (c < str.length()) {
			buf.append(str.substring(c, str.length()));
		}
		return buf.toString();
	}

	public static String replace(String str, String[] subs, String with) {
		if ((str == null) || (subs == null) || subs.length == 0
				|| (with == null)) {
			return str;
		}
		for (int i = 0; i < subs.length; i++) {
			str = replace(str, subs[i], with);
		}
		return str;
	}

	public static String replace(String str, char[] subs, char with) {
		if ((str == null) || (subs == null) || subs.length == 0) {
			return str;
		}
		for (int i = 0; i < subs.length; i++) {
			str = replace(str, subs[i], with);
		}
		return str;
	}

	/**
	 * judge if specified string contains non-english character
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containNonEnChar(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		char c = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (!CharUtil.isEnChar(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * judge if specified string contains non-word character
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containNonWordChar(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		char c = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c == '-') {
				continue;
			}
			if (!CharUtil.isEnChar(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get non-english chars from specified string
	 * 
	 * @param str
	 * @return
	 */
	public static Object[] getNonEnChars(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char c = 0;
		ArrayList al = new ArrayList();
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (!CharUtil.isEnChar(c)) {
				al.add(String.valueOf(c));
			}
		}
		return al.toArray();
	}

	/**
	 * get all non-word chars
	 * 
	 * @param str
	 * @return
	 */
	public static Object[] getNonWordChars(String str) {
		Object[] objs = getNonEnChars(str);
		ArrayList al = new ArrayList();
		for (int i = 0; i < objs.length; i++) {
			if (!objs[i].toString().equals(String.valueOf('-'))) {
				al.add(objs[i]);
			}
		}
		return al.toArray();
	}

	/**
	 * get rid of non-english chars from specified string
	 * 
	 * @param str
	 * @return
	 */
	public static String tripWord(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (!StringUtil.containNonEnChar(str)) {
			return str;
		}
		char s = str.charAt(0);
		char e = str.charAt(str.length() - 1);
		while (!CharUtil.isEnChar(s) && str.length() > 0) {
			str = str.substring(1);
			if (str.length() == 0) {
				break;
			}
			s = str.charAt(0);
		}
		while (!CharUtil.isEnChar(e) && str.length() > 0) {
			str = str.substring(0, str.length() - 1);
			if (str.length() == 0) {
				break;
			}
			e = str.charAt(str.length() - 1);
		}
		return str;
	}

	/**
	 * get words from specified string
	 * 
	 * @author wengm
	 * @param str
	 * @return - String[], words of the string;null, if the string is null or
	 *         empty
	 */
	public static String[] getWords(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] wordSeparators = { '(', ')', '@', ',', ';', ':', '<', '>', '/',
				'[', ']', '?', '.', '=', '*', '^', '#', '$', '%', '&', '_',
				'+', '\\', '!', '|', '{', '}', '0', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', '~', '\t' };
		str = replace(str, wordSeparators, ' ');
		StringBuffer buffer = new StringBuffer();
		String word = null;
		StringTokenizer st = new StringTokenizer(str, " ");
		while (st.hasMoreTokens()) {
			word = tripWord(st.nextToken());
			if (word.length() > 0) {
				buffer.append(word).append("~");
			}
		}
		return buffer.toString().split("~");
	}

	public static boolean containWord(String str, String target,
			boolean sensitive) {
		String[] words = getWords(str);
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(target)) {
				return true;
			} else if (!sensitive && words[i].equalsIgnoreCase(target)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * escape chars that is element of HTML. replace these chars with entity
	 * chars
	 * 
	 * @author wengm
	 * @param s
	 * @return
	 */
	public String escapeHTML(String s) {
		s = s.replaceAll("&", "&amp;");
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("\"", "&quot;");
		s = s.replaceAll("'", "&apos;");
		return s;
	}

	public static boolean isValidDate(String dateString,
			String dateFormatPattern) {
		Date validDate = null;
		synchronized (dateFormat) {
			try {
				dateFormat.applyPattern(dateFormatPattern);
				dateFormat.setLenient(false);
				validDate = dateFormat.parse(dateString);
			} catch (ParseException e) {
			}
		}
		return validDate != null;
	}

	public static boolean isValidInteger(String numberString, int min, int max) {
		Integer validInteger = null;
		try {
			Number aNumber = numberFormat.parse(numberString);
			int anInt = aNumber.intValue();
			if (anInt >= min && anInt <= max) {
				validInteger = new Integer(anInt);
			}
		} catch (ParseException e) {
		}
		return validInteger != null;
	}

	public static boolean isValidEmailAddr(String mailAddr) {
		if (mailAddr == null) {
			return false;
		}
		boolean isValid = true;
		mailAddr = mailAddr.trim();
		int atSign = mailAddr.indexOf('@');
		if (atSign == -1 || atSign == 0 || atSign == mailAddr.length() - 1
				|| mailAddr.indexOf('@', atSign + 1) != -1
				|| mailAddr.indexOf(' ') != -1 || mailAddr.indexOf('\t') != -1
				|| mailAddr.indexOf('\n') != -1 || mailAddr.indexOf('\r') != -1) {
			isValid = false;
		}
		if (isValid) {
			mailAddr = mailAddr.substring(atSign + 1);
			int dot = mailAddr.indexOf('.');
			if (dot == -1 || dot == 0 || dot == mailAddr.length() - 1) {
				isValid = false;
			}
		}
		return isValid;
	}

	public static boolean isValidString(String value, String[] validStrings,
			boolean ignoreCase) {
		boolean isValid = false;
		for (int i = 0; validStrings != null && i < validStrings.length; i++) {
			if (ignoreCase) {
				if (validStrings[i].equalsIgnoreCase(value)) {
					isValid = true;
					break;
				}
			} else {
				if (validStrings[i].equals(value)) {
					isValid = true;
					break;
				}
			}
		}
		return isValid;
	}

	public static boolean isValidString(String[] values, String[] validStrings,
			boolean ignoreCase) {
		if (values == null) {
			return false;
		}
		boolean isValid = true;
		for (int i = 0; values != null && i < values.length; i++) {
			if (!isValidString(values[i], validStrings, ignoreCase)) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

	public static String toHTMLString(String in) {
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#039;");
			} else if (c == '\"') {
				out.append("&#034;");
			} else if (c == '<') {
				out.append("&lt;");
			} else if (c == '>') {
				out.append("&gt;");
			} else if (c == '&') {
				out.append("&amp;");
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	public static Date toDate(String dateString, String dateFormatPattern)
			throws ParseException {
		Date date = null;
		if (dateFormatPattern == null) {
			dateFormatPattern = "yyyy-MM-dd";
		}
		synchronized (dateFormat) {
			dateFormat.applyPattern(dateFormatPattern);
			dateFormat.setLenient(false);
			date = dateFormat.parse(dateString);
		}
		return date;
	}

	public static Number toNumber(String numString, String numFormatPattern)
			throws ParseException {
		Number number = null;
		if (numFormatPattern == null) {
			numFormatPattern = "######.##";
		}
		synchronized (numberFormat) {
			numberFormat.applyPattern(numFormatPattern);
			number = numberFormat.parse(numString);
		}
		return number;
	}

	public static String replaceInString(String in, String from, String to) {
		if (in == null || from == null || to == null) {
			return in;
		}
		StringBuffer newValue = new StringBuffer();
		char[] inChars = in.toCharArray();
		int inLen = inChars.length;
		char[] fromChars = from.toCharArray();
		int fromLen = fromChars.length;
		for (int i = 0; i < inLen; i++) {
			if (inChars[i] == fromChars[0] && (i + fromLen) <= inLen) {
				boolean isEqual = true;
				for (int j = 1; j < fromLen; j++) {
					if (inChars[i + j] != fromChars[j]) {
						isEqual = false;
						break;
					}
				}
				if (isEqual) {
					newValue.append(to);
					i += fromLen - 1;
				} else {
					newValue.append(inChars[i]);
				}
			} else {
				newValue.append(inChars[i]);
			}
		}
		return newValue.toString();
	}

	public static String toContextRelativeURI(String relURI, String currURI)
			throws IllegalArgumentException {
		if (relURI.startsWith("/")) {
			return relURI;
		}
		String origRelURI = relURI;
		if (relURI.startsWith("./")) {
			relURI = relURI.substring(2);
		}
		String currDir = currURI.substring(0, currURI.lastIndexOf("/") + 1);
		StringTokenizer currLevels = new StringTokenizer(currDir, "/");
		int removeLevels = 0;
		while (relURI.startsWith("../")) {
			if (relURI.length() < 4) {
				throw new IllegalArgumentException("Invalid relative URI: "
						+ origRelURI);
			}
			relURI = relURI.substring(3);
			removeLevels++;
		}
		if (removeLevels > currLevels.countTokens()) {
			throw new IllegalArgumentException("Invalid relative URI: "
					+ origRelURI + " points outside the context");
		}
		int keepLevels = currLevels.countTokens() - removeLevels;
		StringBuffer newURI = new StringBuffer("/");
		for (int j = 0; j < keepLevels; j++) {
			newURI.append(currLevels.nextToken()).append("/");
		}
		return newURI.append(relURI).toString();
	}

	public static boolean isNumber(String s) {
		int count = 0;
		for (int index = 0, length = s.length(); index < length; index++) {
			char c = s.charAt(index);
			if (index == 0 && c == '-') {
				continue;
			}
			if (c == '.') {
				count++;
			}
			if (count > 1) {
				return false;
			}
			if (index == length - 1
					&& (c == 'F' || c == 'f' || c == 'L' || c == 'l'
							|| c == 'D' || c == 'd')) {
				return true;
			}
			if (!isNumber(c) && count < 2) {
				return false;
			}
		}
		return true;
	}

	private static boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

	public static boolean isValidMobile(String mobile) {
		if (mobile == null) {
			return false;
		}
		mobile.trim();
		if (mobile.length() < 12) {
			return false;
		}
		if (mobile.indexOf("13") != 0) {
			return false;
		}
		for (int index = 2, length = mobile.length(); index < length; index++) {
			if (!isNumber(mobile.charAt(index))) {
				return false;
			}
		}
		return true;
	}

	public static boolean containNull(String[] strs) {
		if (strs == null) {
			return true;
		}
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] == null) {
				return true;
			}
		}
		return false;
	}

	public static boolean containEmpty(String[] strs) {
		if (strs == null) {
			return true;
		}
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] == null || strs[i].length() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * get password based on random number
	 * 
	 * @return String
	 */
	public static String getRandPassword() {
		int charRange = 1000000;
		int randomNum = (int) (Math.random() * charRange);
		String password = new Integer(randomNum).toString();
		return password;
	}

	/**
	 * Removes whitespace from a specified string
	 * <p>
	 * 
	 * @param String
	 *            s - string to remove whitespace from
	 * @return String - string based on orinal specified string except without
	 *         whitespace (i.e. string "I went out" is returned as "Iwentout"
	 */

	public static String removeWhiteSpace(String s) {
		StringTokenizer st = new StringTokenizer(s);
		int index = st.countTokens();

		StringBuffer str = new StringBuffer();

		for (int i = 0; i < index; i++) {
			str.append(st.nextToken().trim());
		}

		return str.toString();
	}

	/**
	 * Converts a specified string to a string array using default space
	 * delimeter
	 * <p>
	 * 
	 * @param String
	 *            s - string to convert to string array
	 * @return String[] - the string array (i.e. string "one two three four" is
	 *         returned as String [] = {"one","two","three","four"}
	 */

	public static String[] stringToStringArray(String s) {
		return stringToStringArray(s, " ");
	}

	/**
	 * Converts a specified string to a string array using specified delimiter
	 * <p>
	 * 
	 * @param String
	 *            s - string to convert to string array
	 * @return String[] - the string array (i.e. string "one, two, three, four,"
	 *         is returned as String [] = {"one","two","three","four"}
	 */

	public static String[] stringToStringArray(String s, String sDelim) {
		String[] lines = s.split(sDelim);
		return lines;
	}

	/**
	 * Strips whitespace character (160) from output
	 * 
	 * @param String -
	 *            String to strip ascii 160 characters from
	 * @return String - Returns the original string stripped of any ascii 160
	 *         characters
	 */

	public static String stripWSChar(String s) {
		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < s.length(); x++) {
			// System.out.println("Character: " + s.charAt(x));

			char c = s.charAt(x);
			int i = c;

			// System.out.println(c + " ASCII Value: " + i);

			if (i != 160) {
				sb.append(c);
			}

		}

		return sb.toString();
	}

	/**
	 * Sorts data in string array. Ignores case.
	 * 
	 * @param String
	 *            s[] - String Array of unsorted data
	 * @param boolean
	 *            bAscOrDesc - true for Ascending or false for descending order
	 * 
	 * @return String[] - String array data in sorted order
	 */
	public static String[] sortIgnoreCase(String[] s, boolean bAscOrDesc) {
		int cnt;
		String temp;
		for (int i = 0; i < s.length; i++) {
			cnt = 0;
			for (int j = 0; j < s.length - i - 1; j++) {
				if (bAscOrDesc) {
					if (s[j].compareToIgnoreCase(s[j + 1]) > 0) {
						temp = s[j];
						s[j] = s[j + 1];
						s[j + 1] = temp;
						cnt++;
					}
				} else {
					if (s[j].compareToIgnoreCase(s[j + 1]) <= 0) {
						temp = s[j];
						s[j] = s[j + 1];
						s[j + 1] = temp;
						cnt++;
					}
				}
			}
			if (cnt == 0)
				continue;
		}
		return s;
	}

	/**
	 * Sorts data in string array according order of alphabetical list. Case
	 * sensitive.
	 * 
	 * @param String
	 *            s[] - String Array of unsorted data
	 * @param boolean
	 *            bAscOrDesc - true for Ascending or false for descending order
	 * 
	 * @return String[] - String array data in sorted order
	 */
	public static String[] sortByAlpha(String[] s, boolean bAscOrDesc) {
		int cnt;
		String temp;
		for (int i = 0; i < s.length; i++) {
			cnt = 0;
			for (int j = 0; j < s.length - i - 1; j++) {
				if (bAscOrDesc) {
					if (s[j].compareTo(s[j + 1]) > 0) {
						temp = s[j];
						s[j] = s[j + 1];
						s[j + 1] = temp;
						cnt++;
					}
				} else {
					if (s[j].compareTo(s[j + 1]) <= 0) {
						temp = s[j];
						s[j] = s[j + 1];
						s[j + 1] = temp;
						cnt++;
					}
				}
			}
			if (cnt == 0)
				continue;
		}
		return s;
	}

	public static String[] sortByLength(String[] s, boolean bAscOrDesc) {
		int cnt;
		String temp;
		for (int i = 0; i < s.length; i++) {
			cnt = 0;
			for (int j = 0; j < s.length - i - 1; j++) {
				if (bAscOrDesc) {
					if (s[j].length() - (s[j + 1]).length() > 0) {
						temp = s[j];
						s[j] = s[j + 1];
						s[j + 1] = temp;
						cnt++;
					}
				} else {
					if (s[j].length() - (s[j + 1]).length() <= 0) {
						temp = s[j];
						s[j] = s[j + 1];
						s[j + 1] = temp;
						cnt++;
					}
				}
			}
			if (cnt == 0)
				continue;
		}
		return s;
	}

	/**
	 * Character replacement in a string. All occurrencies of a character will
	 * be replaces.
	 * 
	 * @param s
	 *            input string
	 * @param sub
	 *            character to replace
	 * @param with
	 *            character to replace with
	 * 
	 * @return string with replaced characters
	 */
	public static String replace(String s, char sub, char with) {
		if (s == null) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces the very first occurance of a substring with suplied string.
	 * 
	 * @param s
	 *            source string
	 * @param sub
	 *            substring to replace
	 * @param with
	 *            substring to replace with
	 * 
	 * @return modified source string
	 */
	public static String replaceFirst(String s, String sub, String with) {
		if ((s == null) || (sub == null) || (with == null)) {
			return s;
		}
		int i = s.indexOf(sub);
		if (i == -1) {
			return s;
		}
		return s.substring(0, i) + with + s.substring(i + sub.length());
	}

	/**
	 * Replaces the very first occurence of a character in a String.
	 * 
	 * @param s
	 *            string
	 * @param sub
	 *            char to replace
	 * @param with
	 *            char to replace with
	 * 
	 * @return modified string
	 */
	public static String replaceFirst(String s, char sub, char with) {
		if (s == null) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
				break;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces the very last occurance of a substring with suplied string.
	 * 
	 * @param s
	 *            source string
	 * @param sub
	 *            substring to replace
	 * @param with
	 *            substring to replace with
	 * 
	 * @return modified source string
	 */
	public static String replaceLast(String s, String sub, String with) {
		if ((s == null) || (sub == null) || (with == null)) {
			return s;
		}
		int i = s.lastIndexOf(sub);
		if (i == -1) {
			return s;
		}
		return s.substring(0, i) + with + s.substring(i + sub.length());
	}

	/**
	 * Replaces the very last occurence of a character in a String.
	 * 
	 * @param s
	 *            string
	 * @param sub
	 *            char to replace
	 * @param with
	 *            char to replace with
	 * 
	 * @return modified string
	 */
	public static String replaceLast(String s, char sub, char with) {
		if (s == null) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = str.length - 1; i >= 0; i--) {
			if (str[i] == sub) {
				str[i] = with;
				break;
			}
		}
		return new String(str);
	}

	/**
	 * Determines if a string is empty. If string is NULL, it returns true.
	 * 
	 * @param s
	 *            string
	 * 
	 * @return true if string is empty or null.
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * Set the maximum length of the string. If string is longer, it will be
	 * shorten.
	 * 
	 * @param s
	 *            string
	 * @param len
	 *            max number of characters in string
	 * 
	 * @return string with length no more then specified
	 */
	public static String setMaxLength(String s, int len) {
		if (s == null) {
			return s;
		}
		if (s.length() > len) {
			s = s.substring(0, len);
		}
		return s;
	}

	/**
	 * Converts an object to a String. If object is <code>null</code> it will
	 * be not converted.
	 * 
	 * @param obj
	 *            object to convert to string
	 * 
	 * @return string created from the object or <code>null</code>
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * Converts an object to a String. If object is <code>null</code> a empty
	 * string is returned.
	 * 
	 * @param obj
	 *            object to convert to string
	 * 
	 * @return string created from the object
	 */
	public static String toNotNullString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	// ---------------------------------------------------------------- split

	/**
	 * Splits a string in several parts (tokens) that are separated by
	 * delimeter. Delimeter is <b>always</b> surrounded by two strings! If
	 * there is no content between two delimeters, empty string will be returned
	 * for that token. Therefore, the length of the returned array will always
	 * be: #delimeters + 1.<br>
	 * <br>
	 * 
	 * Method is much, much faster then regexp <code>String.split()</code>,
	 * and a bit faster then <code>StringTokenizer</code>.
	 * 
	 * @param src
	 *            string to split
	 * @param delimeter
	 *            split delimeter
	 * 
	 * @return array of splitted strings
	 */
	public static String[] split(String src, String delimeter) {
		if (src == null) {
			return null;
		}
		if (delimeter == null) {
			return new String[] { src };
		}
		int maxparts = (src.length() / delimeter.length()) + 2; // one more for
		// the last
		int[] positions = new int[maxparts];
		int dellen = delimeter.length();

		int i = 0, j = 0;
		int count = 0;
		positions[0] = -dellen;
		while ((i = src.indexOf(delimeter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = src.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	// ---------------------------------------------------------------- ignore
	// cases

	/**
	 * Finds first index of a substring in the given source string with ignored
	 * case.
	 * 
	 * @param src
	 *            source string for examination
	 * @param subS
	 *            substring to find
	 * 
	 * @return index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 */
	public static int indexOfIgnoreCase(String src, String subS) {
		return indexOfIgnoreCase(src, subS, 0);
	}

	/**
	 * Finds first index of a substring in the given source string with ignored
	 * case. This seems to be the fastest way doing this, with common string
	 * length and content (of course, with no use of Boyer-Mayer type of
	 * algorithms). Other implementations are slower: getting char array frist,
	 * lowercasing the source string, using String.regionMatch etc.
	 * 
	 * @param src
	 *            source string for examination
	 * @param subS
	 *            substring to find
	 * @param startIndex
	 *            starting index from where search begins
	 * 
	 * @return index of founded substring or -1 if substring is not found
	 */
	public static int indexOfIgnoreCase(String src, String subS, int startIndex) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		int total = src.length() - sublen + 1;
		for (int i = startIndex; i < total; i++) {
			int j = 0;
			while (j < sublen) {
				char source = Character.toLowerCase(src.charAt(i + j));
				if (sub.charAt(j) != source) {
					break;
				}
				j++;
			}
			if (j == sublen) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds last index of a substring in the given source string with ignored
	 * case.
	 * 
	 * @param s
	 * @param subS
	 *            substring to find
	 * 
	 * @return last index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 * @see #lastIndexOfIgnoreCase(String, String, int)
	 */
	public static int lastIndexOfIgnoreCase(String s, String subS) {
		return lastIndexOfIgnoreCase(s, subS, 0);
	}

	/**
	 * Finds last index of a substring in the given source string with ignored
	 * case.
	 * 
	 * @param src
	 *            source string for examination
	 * @param subS
	 *            substring to find
	 * @param startIndex
	 *            starting index from where search begins
	 * 
	 * @return last index of founded substring or -1 if substring is not found
	 * @see #indexOfIgnoreCase(String, String, int)
	 */
	public static int lastIndexOfIgnoreCase(String src, String subS,
			int startIndex) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		int total = src.length() - sublen;
		for (int i = total; i >= startIndex; i--) {
			int j = 0;
			while (j < sublen) {
				char source = Character.toLowerCase(src.charAt(i + j));
				if (sub.charAt(j) != source) {
					break;
				}
				j++;
			}
			if (j == sublen) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Tests if this string starts with the specified prefix with ignored case.
	 * 
	 * @param src
	 *            source string to test
	 * @param subS
	 *            starting substring
	 * 
	 * @return <code>true</code> if the character sequence represented by the
	 *         argument is a prefix of the character sequence represented by
	 *         this string; <code>false</code> otherwise.
	 */
	public static boolean startsWithIgnoreCase(String src, String subS) {
		return startsWithIgnoreCase(src, subS, 0);
	}

	/**
	 * Tests if this string starts with the specified prefix with ignored case
	 * and with the specified prefix beginning a specified index.
	 * 
	 * @param src
	 *            source string to test
	 * @param subS
	 *            starting substring
	 * @param startIndex
	 *            index from where to test
	 * 
	 * @return <code>true</code> if the character sequence represented by the
	 *         argument is a prefix of the character sequence represented by
	 *         this string; <code>false</code> otherwise.
	 */
	public static boolean startsWithIgnoreCase(String src, String subS,
			int startIndex) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		if (startIndex + sublen > src.length()) {
			return false;
		}
		int j = 0;
		int i = startIndex;
		while (j < sublen) {
			char source = Character.toLowerCase(src.charAt(i));
			if (sub.charAt(j) != source) {
				return false;
			}
			j++;
			i++;
		}
		return true;
	}

	/**
	 * Tests if this string ends with the specified suffix.
	 * 
	 * @param src
	 *            String to test
	 * @param subS
	 *            suffix
	 * 
	 * @return <code>true</code> if the character sequence represented by the
	 *         argument is a suffix of the character sequence represented by
	 *         this object; <code>false</code> otherwise.
	 */
	public static boolean endsWithIgnoreCase(String src, String subS) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		int j = 0;
		int i = src.length() - sublen;
		if (i < 0) {
			return false;
		}
		while (j < sublen) {
			char source = Character.toLowerCase(src.charAt(i));
			if (sub.charAt(j) != source) {
				return false;
			}
			j++;
			i++;
		}
		return true;
	}

	// ---------------------------------------------------------------- wildcard
	// match

	/**
	 * Checks whether a string matches a given wildcard pattern. Possible
	 * patterns allow to match single characters ('?') or any count of
	 * characters ('*'). Wildcard characters can be escaped (by an '\').
	 * 
	 * <p>
	 * This method uses recursive matching, as in linux or windows. regexp works
	 * the same. This method is very fast, comparing to similar implementations.
	 * 
	 * @param string
	 *            input string
	 * @param pattern
	 *            pattern to match
	 * @return <code>true</code> if string matches the pattern, otherwise
	 *         <code>fasle</code>
	 */
	public static boolean isMatch(String string, String pattern) {
		return isMatch(string, pattern, 0, 0);
	}

	/**
	 * Internal matching recursive function.
	 */
	public static boolean isMatch(String string, String pattern,
			int stringStartNdx, int patternStartNdx) {
		int pNdx = patternStartNdx;
		int sNdx = stringStartNdx;
		int pLen = pattern.length();
		int sLen = string.length();
		boolean nextIsNotWildcard = false;
		while (true) {

			// check if end of string and/or pattern occured
			if ((sNdx >= sLen) == true) { // end of string still may have
				// pending '*' in pattern
				while ((pNdx < pLen) && (pattern.charAt(pNdx) == '*')) {
					pNdx++;
				}
				if (pNdx >= pLen) { // end of both string and pattern
					return true;
				}
				return false;
			}
			if (pNdx >= pLen) { // end pf pattern, but not end of the string
				return false;
			}
			char p = pattern.charAt(pNdx); // pattern char

			// perform logic
			if (nextIsNotWildcard == false) {

				if (p == '\\') {
					pNdx++;
					nextIsNotWildcard = true;
					continue;
				}
				if (p == '?') {
					sNdx++;
					pNdx++;
					continue;
				}
				if (p == '*') {
					char pnext = 0; // next pattern char
					if (pNdx + 1 < pLen) {
						pnext = pattern.charAt(pNdx + 1);
					}
					if (pnext == '*') { // double '*' have the same effect as
						// one '*'
						pNdx++;
						continue;
					}
					int i;
					pNdx++;

					// find recursively if there is any substring from the end
					// of the
					// line that matches the rest of the pattern !!!
					for (i = string.length(); i >= sNdx; i--) {
						if (isMatch(string, pattern, i, pNdx) == true) {
							return true;
						}
					}
					return false;
				}
			} else {
				nextIsNotWildcard = false;
			}

			// check if pattern char and string char are equals
			if (p != string.charAt(sNdx)) {
				return false;
			}

			// everything matches for now, continue
			sNdx++;
			pNdx++;
		}
	}

	// ---------------------------------------------------------------- count
	// substrings

	/**
	 * Count substring occurences in a source string.
	 * 
	 * @param source
	 *            source string
	 * @param sub
	 *            substring to count
	 * @return number of substring occurences
	 */
	public static int count(String source, String sub) {
		int count = 0;
		int i = 0, j = 0;
		while (true) {
			i = source.indexOf(sub, j);
			if (i == -1) {
				break;
			}
			count++;
			j = i + sub.length();
		}
		return count;
	}

	/**
	 * Repeats a specified string a specified number of times
	 * 
	 * @param s -
	 *            string to repeat
	 * @param iRepeat -
	 *            number of times to repeat string. repeat("*",7); would return
	 *            "*******"
	 * @return
	 */
	public static String repeat(String s, int iRepeat) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < iRepeat; i++) {
			buf.append(s);
		}
		return buf.toString();
	}

	/**
	 * Returns specified string in proper case
	 * 
	 * @param s -
	 *            string to convert to proper case (first character is
	 *            initialized)
	 */
	public static String toProperCase(String s) {
		String sInitChar = s.substring(0, 1); // get first char
		String sCapitalizedChar = sInitChar.toUpperCase(); // capitalize the
		// first char
		String sOutStr = replaceFirst(s, sInitChar, sCapitalizedChar); // replace
		// the
		// existing
		// char
		// with
		// the
		// capitalized
		// one
		return sOutStr; // return the string in proper case
	}

	/**
	 * Returns the result of a text check on a string-- checks for strings being
	 * equal; no VP performed
	 * 
	 * @param firstString
	 *            string being checked in
	 * @param secondString
	 *            pattern being checked for
	 * @return true if strings equal, false if not
	 * @author Chris Carlson
	 */
	public static boolean doStringsMatch(String firstString, String secondString) {
		return firstString.equals(secondString);
	}

	/**
	 * Returns the string with the space characters "fixed"-- this was coded for
	 * difficulties with weird spaces screwing up string matching
	 * 
	 * @param s
	 *            string being fixed
	 * @return string with fixed space characters
	 * @author Travis Grigsby
	 */
	public static String fixString(String s) {
		String t = "";
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i)) == 160) {
				t += (char) 32;
			} else {
				t += s.charAt(i);
			}
		}
		return t;
	}

	/**
	 * Prints the character values for 2 strings
	 * 
	 * @param s1
	 *            1st string
	 * @param s2
	 *            2nd string
	 * @author Travis Grigsby
	 */
	public static void printCharVals(String s1, String s2) {
		for (int i = 0; i < s1.length(); i++) {
			System.out.print("'" + s1.charAt(i) + "' == '" + (int) s1.charAt(i)
					+ "'");
			System.out.print(":");
			System.out.println("'" + (int) s2.charAt(i) + "' == '"
					+ s2.charAt(i) + "'");
		}
	}

	/**
	 * Searches the input array for an occurrence of the search string
	 * 
	 * @param string
	 *            the substring to search for
	 * @param screenContents
	 *            the String array to search in
	 * @return boolean, true if found, false if not found
	 */
	public static boolean findString(String string, String[] screenContents) {
		boolean found = false;
		// search for a string in the screen contents
		for (int i = 0; i < screenContents.length; ++i) {
			if (screenContents[i] != null) {
				if (screenContents[i].indexOf(string) != -1)
					return true;
			}
		}
		return found;
	}

	/**
	 * Searches the input array for an occurrence of the search string
	 * 
	 * @param string
	 *            the substring to search for
	 * @param screenContents
	 *            the String to search in
	 * @return boolean, true if found, false if not found
	 */
	public static boolean findString(String string, String screenContents) {
		if (string == null || screenContents == null)
			return false;

		if (screenContents.indexOf(string) != -1)
			return true;

		else
			return false;
	}

	/**
	 * Searches the input array for an occurrence of the search string
	 * 
	 * @param string
	 *            the substring to search for
	 * @param screenContents
	 *            the String array to search in
	 * @return int index of the first occurrence of the search string
	 */
	public static int findStringRow(String string, String[] screenContents) {
		int row = -1;
		// search for a string in the screen contents
		for (int i = 0; i < screenContents.length; ++i) {
			if (screenContents[i] != null) {
				if (screenContents[i].indexOf(string) != -1)
					return i;
			}
		}
		return row;
	}

	/**
	 * Searches the input array for an occurrence of the search string after the
	 * index
	 * 
	 * @param string
	 *            the substring to search for
	 * @param fromIndex
	 *            the array index from which to start the search
	 * @param screenContents
	 *            the String array to search in
	 * @return int index of the first occurrence of the search string after the
	 *         index
	 */
	public static int findStringRow(String string, int fromIndex,
			String[] screenContents) {
		int row = -1;
		// search for a string in the screen contents
		for (int i = fromIndex; i < screenContents.length; ++i) {
			if (screenContents[i] != null) {
				if (screenContents[i].indexOf(string) != -1)
					return i;
			}
		}
		return row;
	}

	/**
	 * Searches the input array for occurrences of the search string
	 * 
	 * @param string
	 *            the substring to search for
	 * @param screenContents
	 *            the String array to search in
	 * @return int [] containing the indexes where the search string was found
	 */
	public static int[] findStringRows(String string, String[] screenContents) {
		Vector v = new Vector();
		// search for a string in the screen contents
		for (int i = 0; i < screenContents.length; ++i) {
			if (screenContents[i] != null) {
				if (screenContents[i].indexOf(string) != -1)
					v.add(new java.lang.Integer(i));
			}
		}
		// creates the array of indexes found with the string
		Object[] objs = v.toArray();
		int[] rows = new int[objs.length];
		for (int i = 0; i < objs.length; ++i) {
			rows[i] = ((java.lang.Integer) objs[i]).intValue();
		}
		return rows;
	}

	/**
	 * This method performs multiple replacements in the same string.
	 * 
	 * @param ht
	 *            A hashtable whose keys are the strings to be searched for and
	 *            replaced, and whose values are the replacements strings.
	 * @param s
	 *            The source string to search.
	 * 
	 * @return The original source string with all replacements completed.
	 */
	public static String replace(Hashtable ht, String s) {
		Enumeration enumr = ht.keys();
		String newstring = s;

		while (enumr.hasMoreElements()) {
			String from = (String) enumr.nextElement();
			String to = (String) ht.get(from);
			newstring = replace(newstring, from, to);
		}

		// Debug.print("Old string: " + s + " New String: " + newstring);
		return newstring;
	}

	/**
	 * Performs multiple replacements in multiple strings.
	 * 
	 * @param ht
	 *            A hashtable whose keys are the strings to be searched for and
	 *            replaced, and whose values are the replacements strings.
	 * @param s
	 *            The source strings (in an array) to search.
	 * 
	 * @return The original source strings with all replacements completed.
	 */
	public static String[] replaceAll(Hashtable ht, String[] s) {
		String[] newstring = s;

		for (int i = 0; i < s.length; i++) {
			newstring[i] = replace(ht, s[i]);
		}

		return newstring;
	}

	/**
	 * Converts a string array to a String. Items are separated by system line
	 * separator.
	 * 
	 * @param a
	 * @param separator
	 * @return
	 */
	public static String arrayToString(String[] s) {
		StringBuffer sOut = new StringBuffer();
		if (s.length > 0) {
			sOut.append(s[0]);
			for (int i = 1; i < s.length; i++) {
				sOut.append(System.getProperty("line.separator"));
				sOut.append(s[i]);
			}
		}
		return sOut.toString();
	}

	public static String arrayToString(String[] s, String sDelim) {
		if (s == null) {
			return null;
		}
		StringBuffer sOut = new StringBuffer();
		if (s.length > 0) {
			sOut.append(s[0]);
			for (int i = 1; i < s.length; i++) {
				sOut.append(sDelim);
				sOut.append(s[i]);
			}
		}
		return sOut.toString();
	}

	public static String oracleSQLEncode(String param) {
		return param.replaceAll("'", "''");
	}

	public static String toOracleDate(Date date) {
		dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		return "TO_DATE('" + dateFormat.format(date)
				+ "','yyyy-MM-dd HH24:mi:ss')";
	}

	public static String convertToHTML(String content) {
		if (content == null) {
			return "";
		}
		String str = replace(content, '<', "&lt;");
		str = replace(str, '<', "&lt;");
		str = replace(str, '>', "&gt;");
		str = replace(str, '&', "&amp;");
		str = replace(str, '\t', "&nbsp;&nbsp;&nbsp;&nbsp;");
		str = replace(str, '\r', "<br />");
		str = replace(content, '\n', "<br />");
		return str;
	}

	public static String replace(String content, char c, String str) {
		if (content == null) {
			return null;
		}

		int index = content.indexOf(c);
		if (index < 0) {
			return content;
		}

		char[] cs = new char[1];
		cs[0] = c;

		String regex = new String(cs);
		return content.replaceAll(regex, str);
	}

	public static String getExceptionStack(Exception e, String lineSeparator) {
		StringBuffer result = new StringBuffer(e.getMessage());
		StackTraceElement[] stacks = e.getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			result.append("at ").append(stacks[i].getClassName()).append(
					stacks[i].getMethodName()).append("(").append(
					stacks[i].getFileName()).append(":").append(
					stacks[i].getLineNumber()).append(")")
					.append(lineSeparator);
		}
		return result.toString();
	}

	public static String getExceptionStackForHtml(Exception e) {
		return getExceptionStack(e, "<br />");
	}

	public static void main(String[] strs) {

	}
}
