/**
 * 
 */
package org.loocsij.web.crawler;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import org.loocsij.SystemProperties;
import org.loocsij.logger.*;
import org.loocsij.util.FileUtil;
import org.loocsij.web.WebAccessException;
import org.loocsij.web.WebAccessor;

/**
 * @author wengm
 * 
 */
public class WebResourceExtractor {
	private static Logger log = Log.getLogger(WebResourceExtractor.class);

	/**
	 * 
	 */
	public WebResourceExtractor() {
	}

	/**
	 * download specified kind of web resources from specified web url. user can
	 * specified the max number and store location(should be one directory).
	 * 
	 * @param seedURL
	 * @param fileExtension
	 * @param maxNum
	 * @param storeLocation
	 */
	public static void download(String seedURL, String fileExtension,
			int maxNum, String storeLocation) {
		String[] urls = URLExtractor.extractAllURLs(seedURL, fileExtension,
				maxNum);
		if (urls == null) {
			return;
		}
		WebAccessor web = null;
		String file = null;
		String destFileName = null;
		boolean isOk = false;
		log.info("results num:" + urls.length);
		for (int i = 0; i < urls.length; i++) {
			web = WebAccessor.getInstance(urls[i]);
			if (web == null) {
				continue;
			}
			file = web.getFile();
			if (file == null) {
				continue;
			}
			if (file.indexOf('?') > 0) {
				file = file.substring(0, file.indexOf('?'));
			}
			if (file.length() <= 1) {
				file = web.getHost() + fileExtension;
			}
			destFileName = storeLocation
					+ SystemProperties.sFileSeparator
					+ file.substring(file.lastIndexOf("/") > 0 ? file
							.lastIndexOf("/") : 0);
			isOk = download(urls[i], destFileName);
			log.info("[" + (isOk ? "OK" : "FAIL") + "] - download " + urls[i]
					+ " to " + destFileName);
		}
	}

	/**
	 * download specified web resource to specified file.
	 * 
	 * @param targetURL
	 * @param storeFileName
	 * @return boolean - true,ok;false,fail due to some exception.
	 */
	public static boolean download(String targetURL, String storeFileName) {
		boolean append = false;
		File sfile = new File(storeFileName);
		if (sfile.exists()) {
			if (true) {
				return true;
			}
			boolean b = FileUtil.deleteFiles(storeFileName, FileUtil
					.getFileExtension(storeFileName));
			if (b) {
				try {
					FileUtil.createFile(storeFileName);
				} catch (Exception e) {
					log.error("Fail - Create File:" + storeFileName, e);
					return false;
				}
				append = true;
			} else {
				log.error("old [" + storeFileName + "] can not be deleted!");
			}
		}
		WebAccessor web = WebAccessor.getInstance(targetURL);
		if (web == null) {
			return false;
		}
		InputStream in = null;
		try {
			in = web.getInputStream();
		} catch (IOException ioe) {
			log.error(new WebAccessException("Fail to get inputstream from ["
					+ targetURL + "]"));
			return false;
		}
		byte[] buf = new byte[1024];
		int count = 0;
		try {
			while ((count = in.read(buf)) > 0) {
				FileUtil.writeBytes(buf, 0, count, append, storeFileName);
				if (!append) {
					append = true;
				}
			}
		} catch (IOException ioe) {
			log.error(new WebAccessException("Fail to read content from ["
					+ targetURL + "]"));
			return false;
		}
		return true;
	}

	/**
	 * @param args -
	 *            args[0]:seedURL, args[1]:fileExtension, args[2]:maxNum,
	 *            args[3]:storeLocation
	 */
	public static void main(String[] args) {
		if (args.length!=4) {
			throw new IllegalArgumentException("Usage:java org.loocsij.wis.link.WebResourceExtractor [seedUrl] [fileExt] [maxNum] [targetPath]");
		}
		if (!org.loocsij.util.StringUtil.isNumber(args[2])) {
			throw new IllegalArgumentException(args[2]
					+ " should be one valid integer.");
		}
		int maxNum = Integer.parseInt(args[2]);
		download(args[0], args[1], maxNum, args[3]);
	}

}
