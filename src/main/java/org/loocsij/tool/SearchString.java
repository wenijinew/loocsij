/**
 * 
 */
package org.loocsij.tool;

import org.loocsij.logger.*;
import org.loocsij.util.FileUtil;

/**
 * @author wengm
 * 
 */
public class SearchString {
	private static SimpleLogger log = Log.getSimpleLogger(SearchString.class);

	/**
	 * 
	 */
	private SearchString() {
	}

	public static String[] doSearch(String[] args) {
		if (args == null || args.length != 4) {
			log
					.error(new Exception(
							"Invalid arguments:Usage - $dir $fileExtension $target $matchType. Note:$matchType should be 0(MATCH_TYPE_EXACT) or 1(MATCH_TYPE_FUZZY) or 2(MATCH_TYPE_STARTSWITH) or 3(MATCH_TYPE_ENDSWITH)"));
			return null;
		}
		long start = new java.util.Date().getTime();

		int matchType = -1;
		try {
			matchType = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			log
					.error(new Exception(
							"Invalid argument:"
									+ args[3]
									+ ". Note:$matchType should be 0(MATCH_TYPE_EXACT) or 1(MATCH_TYPE_FUZZY) or 2(MATCH_TYPE_STARTSWITH) or 3(MATCH_TYPE_ENDSWITH"));
			return null;
		}
		String strMatchType = "MATCH_TYPE_EXACT";
		switch (matchType) {
		case FileUtil.MATCH_TYPE_EXACT:
			break;
		case FileUtil.MATCH_TYPE_FUZZY:
			strMatchType = "MATCH_TYPE_FUZZY";
			break;
		case FileUtil.MATCH_TYPE_STARTSWITH:
			strMatchType = "MATCH_TYPE_STARTSWITH";
			break;
		case FileUtil.MATCH_TYPE_ENDSWITH:
			strMatchType = "MATCH_TYPE_ENDSWITH";
			break;
		}

		log
				.info("=============================================================================");
		log.info("Search Words: [" + args[1] + "][" + args[2] + "] from ["
				+ args[0] + "] with match type:[" + strMatchType + "]");
		log
				.info("=============================================================================");

		String[] results = FileUtil.searchString(args[0], args[1], args[2],
				matchType);

		if (results == null || results.length == 0) {
			log.info("Find 0 results from [" + args[0] + "]");
			log.info(org.loocsij.util.DateUtil.elapsedTime(start));
			return null;
		}

		int length = results.length;
		log.info("Find " + length + " results from [" + args[0] + "]:");
		String result = null;
		log.info("[$index] - $lineNumber^$line^$filePath");
		for (int i = 0; i < length; i++) {
			result = results[i];
			log.info("[" + i + "] - " + result);
		}
		log.info(org.loocsij.util.DateUtil.elapsedTime(start));
		return results;
	}

	/**
	 * @param args -
	 *            args[0]:file name<br>
	 *            args[1]:extension<br>
	 *            args[2]:target<br>
	 *            args[3]: match type:
	 *            <ul>
	 *            <li>MATCH_TYPE_EXACT : 0
	 *            <li>MATCH_TYPE_FUZZY : 1
	 *            <li>MATCH_TYPE_STARTSWITH : 2
	 *            <li>MATCH_TYPE_ENDSWITH : 3
	 *            </ul>
	 */
	public static void main(String[] args) {
		doSearch(args);
	}
}
