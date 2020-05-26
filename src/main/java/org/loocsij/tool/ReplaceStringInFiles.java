package org.loocsij.tool;

import org.loocsij.util.FileUtil;

public class ReplaceStringInFiles {
	/**
	 * @param args -
	 *            args[0]:dir or file path, args[1]:extension of fils(s),
	 *            args[2]:target string, args[3]:replacement string.
	 */
	public static void main(String[] args) {
		FileUtil.replace(args[0], args[1], args[2], args[3]);
	}
}
