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
public class SearchFile {
	private static SimpleLogger log = Log.getSimpleLogger(SearchFile.class);

	/**
	 * @param args -
	 *            args[0]:root directory;args[1]:file name portion;args[1]:file
	 *            content portion
	 */
	public static void main(String[] args) {
		if(args==null || args.length!=3) {
			log.info("Invalid arguments. Usage: $dir $fileName $fileContent");
			return;
		}
		
		log.info("Search File: file name contains [" + args[1] + "], file content contains [" + args[2] + "] from [" + args[0]
				+ "]");
		long start = new java.util.Date().getTime();
		
		String[] results = FileUtil.searchFile(args[0], args[1], args[2]);
		
		if (results == null || results.length == 0) {
			log.info("Find 0 results from [" + args[0] + "]");
			log.info(org.loocsij.util.DateUtil.elapsedTime(start));
			return;
		}
		
		int length = results.length;
		log.info("Find " + length + " result(s) from [" + args[0] + "]:");
		
		String result = null;
		for (int i = 0; i < length; i++) {
			result = results[i];
			log.info(result);
		}
		
		log.info(org.loocsij.util.DateUtil.elapsedTime(start));
	}
}
