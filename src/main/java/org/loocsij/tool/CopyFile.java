package org.loocsij.tool;

import org.loocsij.logger.*;
import org.loocsij.util.FileUtil;

import org.apache.logging.log4j.*;

public class CopyFile {
	private static Logger log = Log.getLogger(CopyFile.class);

	/**
	 * 
	 * @param args -
	 *            args[0]:source file;args[1]:destination file
	 */
	public static void main(String[] args) {
		boolean b = FileUtil.copyCharacters(args[0], args[1], false);
		if (b) {
			log.info("PASS - Copy [" + args[0] + "] to [" + args[1] + "]");
		} else {
			log.info("OK - Copy [" + args[0] + "] to [" + args[1] + "]");
		}
	}

}
