package org.loocsij.util;

public class CharUtil {
	/**
	 * judge if specified character is English language character(A-Z|a-z)
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEnChar(char c) {
		if (c > 122 || c < 65 || (c > 90 && c < 97)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static char[] getUnicodeChars(int start, int end) {
		char[] cs = new char[end - start];
		for (int i = start; i < end; i++) {
			cs[i] = (char) i;
		}
		return cs;
	}

	public static void main(String[] strs) {
	}
}
