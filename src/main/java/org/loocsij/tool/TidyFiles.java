/**
 * 
 */
package org.loocsij.tool;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import org.loocsij.SystemProperties;
import org.loocsij.logger.Log;
import org.loocsij.util.FileUtil;

/**
 * @author wengm
 * 
 */
public class TidyFiles {

	private static Logger log = Log.getLogger(TidyFiles.class);

	/**
	 * 
	 */
	public TidyFiles() {
	}

	public static void extract(String directory, String driver, String extension) {
		log.info(directory);
		File f = new File(directory);
		if (f.isDirectory()) {
			String[] fs = f.list();
			if (fs == null) {
				log.info("[NOT FOUND] - " + f);
				return;
			}
			for (int i = 0; i < fs.length; i++) {
				extract(f.getPath() + SystemProperties.sFileSeparator
						+ fs[i], driver, extension);
			}
		}
		String ext = FileUtil.getFileExtension(f.getAbsolutePath());
		if (ext == null || !ext.equalsIgnoreCase(extension)) {
			return;
		}
		process(f.getPath() + SystemProperties.sFileSeparator
				+ f, driver);
	}

	public static void process(String directory, String driver) {
		ArrayList al = new ArrayList();
		File f = new File(directory);
		if (f.isDirectory()) {
			String[] fs = f.list();
			if (fs == null) {
				log.info("[NOT FOUND] - " + f);
				return;
			}
			for (int i = 0; i < fs.length; i++) {
				process(f.getPath() + SystemProperties.sFileSeparator + fs[i],
						driver);
			}
		}
		String extension = null;
		String targetRoot = null;
		String fileName = f.getAbsolutePath();
		extension = FileUtil.getFileExtension(fileName);
		if (extension == null) {
			extension = "others";
		}
		targetRoot = driver + SystemProperties.sFileSeparator + extension;
		if (!al.contains(extension)) {
			al.add(extension);
			FileUtil.createDir(targetRoot);
		}
		FileUtil.moveFiles(fileName, targetRoot);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		extract(args[0], args[1], args[2]);
	}

}
