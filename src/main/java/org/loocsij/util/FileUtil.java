package org.loocsij.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import org.loocsij.SystemProperties;
import org.loocsij.logger.Log;

/**
 * provides methods to process kinds of operation on files. TODO: Merge
 * FileProcessor into this class.
 * 
 * @author wsxspring
 * 
 */
public class FileUtil {
	private static Logger log = Log.getLogger(FileUtil.class);

	/**
	 * Global: match each other exactly
	 */
	public static final int MATCH_TYPE_EXACT = 0;

	/**
	 * Global: one is contained by another
	 */
	public static final int MATCH_TYPE_FUZZY = 1;

	/**
	 * Globale: one string starts with another
	 */
	public static final int MATCH_TYPE_STARTSWITH = 2;

	/**
	 * Globale: one string ends with another
	 */
	public static final int MATCH_TYPE_ENDSWITH = 3;

	/**
	 * delete specified directory and its sub-directories and sub-files
	 * 
	 * @param dir -
	 *            root directory which should be deleted.
	 * @param extension -
	 *            filter extension of files to be deleted, if all files and
	 *            folers should be deleted, this argument should be null.
	 * @return boolean - true,delete successfully
	 */
	public static boolean deleteFiles(String dir, String extension) {
		File f = new File(dir);
		if (extension == null && f.delete()) {
			log.info("[Deleted] - " + f);
			return true;
		}
		String[] fs = f.list();
		if (fs == null) {
			log.info("[NOT FOUND] - " + f);
			return false;
		}
		for (int i = 0; i < fs.length; i++) {
			File ff = new File(f.getPath() + SystemProperties.sFileSeparator
					+ fs[i]);
			// filter
			if (ff.isFile()) {
				String extensionTemp = getFileExtension(ff.getName());
				if (extension != null && !extension.equals(extensionTemp)) {
					continue;
				}
			}
			// if extension is not null, directory should not be deleted
			if (extension != null && ff.isDirectory()) {
				deleteFiles(ff.getAbsolutePath(), extension);
			} else {
				String rename = ff.getParent()
						+ SystemProperties.sFileSeparator + i;
				/*
				 * if failed in delete, maybe file name is too long, so rename
				 * to short name first, if failed again, maybe it has many
				 * subfolers and subfiles. It is necessary to delete its
				 * children first.
				 */
				if (!ff.delete()) {
					if (ff.renameTo(new File(rename))) {
						if (!ff.delete()) {
							deleteFiles(rename
									+ SystemProperties.sFileSeparator,
									extension);
						} else {
							log.info("[Deleted] - " + ff);
						}
					} else {
						/*
						 * if failed in renaming, maybe there is one file with
						 * the same name, so add random postfix
						 */
						if (!ff.renameTo(new File(rename
								+ new Random().nextDouble()))) {
							deleteFiles(rename + i
									+ SystemProperties.sFileSeparator,
									extension);
						}
					}
				} else {
					log.info("[Deleted] - " + ff);
					continue;
				}
			}
		}
		if (extension == null && f.delete()) {
			log.info("[Deleted] - " + f);
		}
		return true;
	}

	/**
	 * rename files under given directory as $index.$extension such as
	 * 1.jpg(from origin001.jpg).
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean renameFiles(String dir) {
		File f = new File(dir);
		String[] fs = f.list();
		if (fs == null) {
			log.info("[NOT FOUND] - " + f);
			return false;
		}
		for (int i = 0; i < fs.length; i++) {
			File ff = new File(f.getPath() + SystemProperties.sFileSeparator
					+ fs[i]);
			String extension = getFileExtension(ff.getName());
			if (ff.renameTo(new File(ff.getParent()
					+ SystemProperties.sFileSeparator + i + extension))) {
				continue;
			} else {
				if (!ff.renameTo(new File(ff.getParent()
						+ SystemProperties.sFileSeparator + i
						+ new Random().nextDouble()))) {
					// should be directory
					renameFiles(ff.getParent()
							+ SystemProperties.sFileSeparator + i
							+ SystemProperties.sFileSeparator);
				}
			}
		}
		return true;
	}

	/**
	 * create file with specified file name
	 * 
	 * @author wengm
	 * @param fileName
	 * @return true - create successfully
	 */
	public static boolean createFile(String fileName) {
		File f = new File(fileName);
		if (f.exists()) {
			return true;
		}
		File p = f.getParentFile();
		if (p != null && !p.exists()) {
			p.mkdirs();
		}
		try {
			f.createNewFile();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * create file with specified file name
	 * 
	 * @author wengm
	 * @param fileName
	 * @return true - create successfully
	 */
	public static boolean createDir(String dirName) {
		File f = new File(dirName);
		if (f.exists()) {
			return true;
		}
		File p = f.getParentFile();
		if (p != null && !p.exists()) {
			p.mkdirs();
		}
		return f.mkdir();
	}

	/**
	 * Get lines of file content. If get all content, use start as 0 and end as -1.
	 * @return String[]
	 * @author wengm
	 * @since 2009-1-17
	 */
	public static String[] lines(String fileName, String charset, int start,
			int end) {
		if (StringUtil.isEmpty(fileName) || !isExist(fileName) || start<0 || (end!=-1 && end<=start)) {
			log.error(new Exception("Invalid arguments."));
			return null;
		}
		if (StringUtil.isEmpty(charset)) {
			charset = SystemProperties.sFileEncoding;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			/*
			 * if the file name is directory, this exception will be throwed
			 * also.
			 */
			log.error(e, e);
			return null;
		}
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(in, charset);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
			return null;
		}
		BufferedReader bin = new BufferedReader(isr);
		String line = null;
		StringBuffer buf = new StringBuffer();
		int iCurrentLineIndex = -1;
		try {
			while ((line = bin.readLine()) != null) {
				iCurrentLineIndex++;
				// if current line is NOT in the specified range.
				if (iCurrentLineIndex < start) {
					continue;
				}
				if (end > 0 && iCurrentLineIndex > end) {
					break;
				}
				log.debug("[" + iCurrentLineIndex + "] - " + line);
				// if current line is IN the specified range.
				buf.append(line).append(SystemProperties.sLineSeparator);
			}
		} catch (IOException e) {
			log.error(e, e);
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e, e);
					return null;
				}
			}
			if (bin != null) {
				try {
					bin.close();
				} catch (IOException e) {
					log.error(e, e);
					return null;
				}
			}
		}
		return buf.toString().split(SystemProperties.sLineSeparator);
	}

	/**
	 * Get all lines of specified file.
	 * 
	 * @author wengm
	 * @param fileName
	 * @param charset
	 *            TODO
	 * @return - String[], lines of the file;null, if the file name is invalid
	 *         or some exception occurred.
	 */
	public static String[] lines(String fileName, String charset) {
		return lines(fileName, charset, 0, -1);
	}

	/**
	 * get words of specified file
	 * 
	 * @author wengm
	 * @param plainFileName
	 * @return String[] - words of the file
	 * @see StringUtil#getWords(String);null, if file name is invalid or any
	 *      exception occurred.
	 */
	public static String[] listWords(String plainFileName) {
		String[] ls = lines(plainFileName, null);
		if (ls == null) {
			return null;
		}
		String line = null;
		String[] lineWords = null;
		StringBuffer fileWords = new StringBuffer();
		for (int i = 0; i < ls.length; i++) {
			line = ls[i].toString();
			lineWords = StringUtil.getWords(line);
			for (int j = 0; j < lineWords.length; j++) {
				fileWords.append(lineWords[j]).append(StringUtil.wordSeparator);
			}
		}
		return fileWords.toString().split(StringUtil.wordSeparator);
	}

	/**
	 * search specified word(target) from specified file(plainFileName)
	 * 
	 * @author wengm
	 * @param fileName -
	 *            path of file to search
	 * @param extension -
	 *            file extension, if null or empty, search all type of files.
	 * @param target -
	 *            string to be searched
	 * @param matchType
	 */
	public static String[] searchString(String fileName, String extension,
			String target, int matchType) {
		// if arguments are invalid, return
		if (StringUtil.isEmpty(fileName) || !isExist(fileName)
				|| StringUtil.isEmpty(target)) {
			log.error("Invalid arguments.");
			return null;
		}

		// create instance of file
		File f = new File(fileName);

		// if file is directory, perform search action against every file.
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			String[] lines = null;
			StringBuffer buf = new StringBuffer();
			int count = 0;
			for (int i = 0; i < fs.length; i++) {
				if (extension != null && extension.trim().length() != 0
						&& fs[i].isFile()
						&& !fs[i].getName().endsWith(extension)) {
					continue;
				}
				if (fs[i].isFile()) {
					log.debug(fs[i]);
				}
				lines = searchString(fs[i].getAbsolutePath(), extension,
						target, matchType);
				if (lines == null || lines.length == 0) {
					continue;
				}
				for (int j = 0; j < lines.length; j++) {
					count++;
					buf.append(lines[j])
							.append(SystemProperties.sLineSeparator);
				}
			}
			if (count == 0) {
				return null;
			}
			return buf.toString().split(SystemProperties.sLineSeparator);
		}

		// perform search action.
		Object[] lines = lines(fileName, null);
		if (lines == null) {
			log.error("Can not get content from [" + fileName + "]");
			return null;
		}
		String line = null;
		String[] lineWords = null;
		String result = null;
		StringBuffer buf = new StringBuffer();
		int index = 0;
		int count = 0;
		String delimiter = "^";
		for (int i = 0; i < lines.length; i++) {
			line = lines[i].toString();
			// log.info(line);
			lineWords = StringUtil.getWords(line);
			if (lineWords == null) {
				continue;
			}
			switch (matchType) {
			case MATCH_TYPE_EXACT:
				for (int j = 0; j < lineWords.length; j++) {
					if (lineWords[j].equals(target)) {
						count++;
						result = (i + 1)
								+ delimiter
								+ StringUtil.replace(line, target, "[" + target
										+ "]") + delimiter + fileName;
						buf.append(result).append(
								SystemProperties.sLineSeparator);
					}
				}
				break;
			case MATCH_TYPE_STARTSWITH:
				if (line.startsWith(target)) {
					result = (i + 1)
							+ delimiter
							+ StringUtil.replace(line, target, "[" + target
									+ "]") + delimiter + fileName;
					count++;
					buf.append(result).append(SystemProperties.sLineSeparator);
				}
				break;
			case MATCH_TYPE_ENDSWITH:
				if (line.endsWith(target)) {
					result = (i + 1)
							+ delimiter
							+ StringUtil.replace(line, target, "[" + target
									+ "]") + delimiter + fileName;
					count++;
					buf.append(result).append(SystemProperties.sLineSeparator);
				}
				break;
			case MATCH_TYPE_FUZZY:
			default:
				for (int j = 0; j < lineWords.length; j++) {
					if (lineWords[j].equalsIgnoreCase(target)) {
						count++;
						result = (i + 1)
								+ delimiter
								+ StringUtil.replace(line, lineWords[j], "["
										+ target + "]") + delimiter + fileName;
						buf.append(result).append(
								SystemProperties.sLineSeparator);
					}
				}
				index = line.indexOf(target);
				if (index != -1) {
					result = (i + 1)
							+ delimiter
							+ StringUtil.replace(line, target, "[" + target
									+ "]") + delimiter + fileName;
					if (buf.indexOf(result) != -1) {
						continue;
					}
					count++;
					buf.append(result).append(SystemProperties.sLineSeparator);
				}
			}
		}

		if (count == 0) {
			return null;
		}
		return buf.toString().split(SystemProperties.sLineSeparator);
	}

	/**
	 * search file form specified root.<br>
	 * <li>if both file name portion and file content portion are null, find
	 * all files in root</li>
	 * <li>if file name portion is not null, find all files which have matched
	 * name;<br>
	 * if file content portion is null,out put the file absolute path<br>
	 * if file content portion is not null,find files which contain specified
	 * content portion</li>
	 * <li>if file name portion is null, find all files which contain specified
	 * content</li>
	 * 
	 * @param root -
	 *            directory to search
	 * @param fileNamePortion
	 * @param fileContentPortion
	 * 
	 * @return
	 */
	public static String[] searchFile(String root, String fileNamePortion,
			String fileContentPortion) {
		if (StringUtil.isEmpty(root)) {
			log.error(root + " == \"\"");
			return null;
		}
		if (!isExist(root)) {
			log.error(root + " does not exist");
			return null;
		}
		File f = new File(root);
		if (!f.isDirectory()) {
			log.error("Invalid argument [root]:not directory");
			return null;
		}
		if (StringUtil.containEmpty(new String[] { fileNamePortion,
				fileContentPortion })) {
			log
					.error("Invalid argument:[fileNamePortion] or [fileContentPortion] is NULL string(null or empty)");
			return null;
		}
		String[] subs = f.list();
		if (subs == null || subs.length == 0) {
			return null;
		}
		String fileName = null;
		String[] lines = null;
		StringBuffer sbResults = new StringBuffer();
		String[] subResults = null;
		ArrayList alDirs = new ArrayList();
		File sub = null;
		boolean found = false;
		boolean nameMatches = true;

		for (int i = 0; i < subs.length; i++) {
			sub = new File(f.getPath() + System.getProperty("file.separator")
					+ subs[i]);
			if (sub.isDirectory()) {
				alDirs.add(sub.getAbsolutePath());
			}
			fileName = sub.getName();
			if (fileNamePortion == null && fileContentPortion == null) {
				found = true;
				sbResults.append(sub.getAbsolutePath()).append(
						SystemProperties.sLineSeparator);
			} else if (fileNamePortion != null) {
				nameMatches = fileName.indexOf(fileNamePortion) != -1
						|| Pattern.compile(fileNamePortion).matcher(fileName)
								.matches();
				if (nameMatches) {
					/*
					 * if file content portion is null
					 */
					if (fileContentPortion == null) {
						found = true;
						sbResults.append(sub.getAbsolutePath()).append(
								SystemProperties.sLineSeparator);
					} else {
						lines = searchString(sub.getAbsolutePath(), null,
								fileContentPortion, MATCH_TYPE_FUZZY);
						if (lines != null && lines.length > 0) {
							found = true;
							sbResults.append(sub.getAbsolutePath()).append(
									SystemProperties.sLineSeparator);
						}
					}
				}
			} else {
				lines = searchString(sub.getAbsolutePath(), null,
						fileContentPortion, MATCH_TYPE_FUZZY);
				if (lines != null && lines.length > 0) {
					found = true;
					sbResults.append(sub.getAbsolutePath()).append(
							SystemProperties.sLineSeparator);
				}
			}
		}
		for (int i = 0; i < alDirs.size(); i++) {
			subResults = searchFile(alDirs.get(i).toString(), fileNamePortion,
					fileContentPortion);
			if (subResults != null && subResults.length > 0) {
				for (int j = 0; j < subResults.length; j++) {
					sbResults.append(subResults[j]).append(
							SystemProperties.sLineSeparator);
				}
			}
		}
		if (!found) {
			return new String[0];
		}
		return sbResults.toString().split(SystemProperties.sLineSeparator);
	}

	/**
	 * get content of plain file.
	 * 
	 * @author wengm
	 * @param fileName
	 * @return String - content of the file;null if file name if invalid or any
	 *         exception occurred
	 */
	public static String getContent(String fileName) {
		String c = null;
		if (StringUtil.isEmpty(fileName)) {
			System.out.println("B");
			log.info("B");
			return null;
		}
		if (!isExist(fileName)) {
			System.out.println("C");
			log.info("C");
			return null;
		}
		FileReader in = null;
		try {
			in = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e);
		}
		BufferedReader bin = new BufferedReader(in);
		String line = null;
		StringBuffer sbuf = new StringBuffer();
		try {
			while ((line = bin.readLine()) != null) {
				sbuf.append(line);
				sbuf.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		}
		c = sbuf.toString();
		return c;
	}

	/**
	 * get line count in specified plain file
	 * 
	 * @author wengm
	 * @param plainFileName
	 * @return int - count of line in file;-1, if file name is invalid or any
	 *         exception occurred
	 */
	public static int getLinesCount(String plainFileName) {
		String[] lines = lines(plainFileName, null);
		if (lines == null) {
			return -1;
		}
		return lines(plainFileName, null).length;
	}

	public static File[] listFiles(String dir, String extension) {
		return new File(dir).listFiles(new FileFilterImpl(extension));
	}

	/**
	 * read file with specified charset
	 * 
	 * @author wengm
	 * @param fileName -
	 *            absolute path of file
	 * @param charset -
	 *            charset name
	 * @return String - file's content; null if any exception occurred.
	 * @throws IOException -
	 *             if any error rises in reading file
	 */
	public static String readFile(String fileName, String charset)
			throws IOException {
		if (fileName == null) {
			return null;
		}
		File f = new File(fileName);
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(f);
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.getMessage(), fnfe);
			return null;
		}

		InputStreamReader in = new InputStreamReader(fin, charset);
		BufferedReader reader = new BufferedReader(in);
		String line = reader.readLine();
		StringBuffer buf = new StringBuffer();
		while (line != null) {
			buf.append(line).append(SystemProperties.sLineSeparator);
			line = reader.readLine();
		}
		return buf.toString();
	}

	private static int REMOVE = 0;

	private static int GET = 1;

	/**
	 * remove comment from given file.
	 * 
	 * @param fileName
	 * @return - String[], removed comments
	 */
	public static String[] removeComments(String fileName) {
		File f = new File(fileName);
		StringBuffer buf = new StringBuffer(fileName)
				.append(SystemProperties.sLineSeparator);
		String[] comments = null;
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].isDirectory()) {
						removeComments(fs[i].getAbsolutePath());
					}
					comments = removeComments(fs[i].getAbsolutePath(), REMOVE);
					if (comments != null && comments.length > 0) {
						for (int j = 0; j < comments.length; j++) {
							buf.append(comments[j]).append(
									SystemProperties.sLineSeparator);
						}
					}
				}
			}
			return buf.toString().split(SystemProperties.sLineSeparator);
		}
		return removeComments(fileName, REMOVE);
	}

	/**
	 * remove chars from given file.
	 * 
	 * @param fileName
	 * @return - String[], removed comments
	 */
	public static String[] removeChars(String fileName, int start, int end) {
		File f = new File(fileName);
		StringBuffer buf = new StringBuffer(fileName)
				.append(SystemProperties.sLineSeparator);
		String[] cs = null;
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					cs = removeChars(fs[i].getAbsolutePath(), start, end,
							REMOVE);
					if (cs != null && cs.length > 0) {
						for (int j = 0; j < cs.length; j++) {
							buf.append(cs[j]).append(
									SystemProperties.sLineSeparator);
						}
					}
				}
			}
			return buf.toString().split(SystemProperties.sLineSeparator);
		}
		return removeChars(fileName, start, end, REMOVE);
	}

	/**
	 * get chars in given gile.
	 * 
	 * @param fileName -
	 *            file name, do not allow directory.
	 * @return String[] - comments in given gile.
	 */
	public static String[] getChars(String fileName, int start, int end) {
		return removeChars(fileName, start, end, GET);
	}

	/**
	 * comments in given gile.
	 * 
	 * @param fileName -
	 *            file name, do not allow directory.
	 * @return String[] - comments in given gile.
	 */
	public static String[] getComments(String fileName) {
		return removeComments(fileName, GET);
	}

	/**
	 * remove comments content from given file.
	 * 
	 * @param fileName -
	 *            file name, do not allow directory.
	 * @param type
	 * @return String[] - removed comments,null if error occurred.
	 */
	private static String[] removeChars(String fileName, int rangeStart,
			int rangeEnd, int type) {
		if (StringUtil.isEmpty(fileName)) {
			log.error("[" + fileName + "] is empty");
			return null;
		}
		File f = new File(fileName);
		if (!f.isFile()) {
			log.error("[" + fileName + "] is not file");
			return null;
		}
		String[] lines = lines(fileName, null);
		if (lines == null) {
			log.error("Can not get content from [" + fileName + "]");
			return null;
		}

		if (type == REMOVE) {
			FileUtil.writeString("", fileName);
		}
		String line = null;
		StringBuffer buf = new StringBuffer(fileName)
				.append(SystemProperties.sLineSeparator);
		int index = 0;
		char[] cs = null;
		char c = 0;
		while (index < lines.length) {
			line = lines[index];
			cs = new char[line.length()];
			line.getChars(0, line.length(), cs, 0);
			for (int i = 0; i < cs.length; i++) {
				c = cs[i];
				if (((int) c) < rangeStart && ((int) c) > rangeEnd) {
					buf.append(c);
				} else {
					if (type == REMOVE) {
						FileUtil.writeString(String.valueOf(c), fileName,
								SystemProperties.sFileEncoding, true);
					}
				}
			}
			if (type == REMOVE) {
				FileUtil.writeLine(SystemProperties.sLineSeparator, fileName,
						SystemProperties.sFileEncoding, true);
			}
			buf.append(SystemProperties.sLineSeparator);
			index++;
		}
		return buf.toString().split(SystemProperties.sLineSeparator);
	}

	/**
	 * remove comments content from given file.
	 * 
	 * @param fileName -
	 *            file name, do not allow directory.
	 * @param type
	 * @return String[] - removed comments,null if error occurred.
	 */
	private static String[] removeComments(String fileName, int type) {
		if (StringUtil.isEmpty(fileName)) {
			return null;
		}
		File f = new File(fileName);
		if (!f.isFile()) {
			return null;
		}
		String[] lines = lines(fileName, null);
		if (lines == null) {
			log.error("Can not get content from [" + fileName + "]");
			return null;
		}

		if (type == REMOVE) {
			FileUtil.writeString("", fileName);
		}
		String line = null;
		StringBuffer buf = new StringBuffer(fileName)
				.append(SystemProperties.sLineSeparator);
		int index = 0;
		while (index < lines.length) {
			line = lines[index].trim();
			// function area start
			if (line.startsWith("/*")) {
				buf.append(lines[index])
						.append(SystemProperties.sLineSeparator);
				while (!line.endsWith("*/") && index < lines.length) {
					line = lines[++index].trim();
					buf.append(lines[index]).append(
							SystemProperties.sLineSeparator);
					continue;
				}
				index++;
				continue;
			} else if (line.startsWith("//")) {
				buf.append(lines[index])
						.append(SystemProperties.sLineSeparator);
				index++;
				continue;
			} else if (line.length() == 0) {
				index++;
				continue;
			}
			// if(line.indexOf("&")>=0) {
			// line = line.replaceAll("&", "&amp;");
			// }
			// function area end
			if (type == REMOVE) {
				FileUtil.writeLine(lines[index], fileName,
						SystemProperties.sFileEncoding, true);
			}
			index++;
		}
		return buf.toString().split(SystemProperties.sFileEncoding);
	}

	/**
	 * replace specified string of specified file with specified another string.
	 * 
	 * @author wengm
	 * @param fileName
	 * @param extension
	 * @param target
	 * @param replacement
	 * @return String[] - replaced lines of the file.
	 */
	public static String[] replace(String fileName, String extension,
			String target, String replacement) {
		long start = new Date().getTime();
		log.debug("====================================================");
		log.debug("Replace [" + target + "] From [" + fileName + "] with ["
				+ replacement + "]");
		if (StringUtil.isEmpty(fileName) || !isExist(fileName)
				|| StringUtil.isEmpty(target)
				|| replacement==null) {
			log.error("Invalid arguments.");
			return null;
		}
		
		File f = new File(fileName);
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			String[] lines = null;
			StringBuffer buf = new StringBuffer();
			int count = 0;
			for (int i = 0; i < fs.length; i++) {
				lines = replace(fs[i].getAbsolutePath(), extension, target,
						replacement);
				if (lines == null || lines.length == 0) {
					continue;
				}
				for (int j = 0; j < lines.length; j++) {
					count++;
					buf.append(lines[j])
							.append(SystemProperties.sLineSeparator);
				}
			}
			log.info(String.valueOf(new Date().getTime() - start));
			if (count == 0) {

				log.info("Can not find [" + target + "] from [" + fileName
						+ "]");
				log
						.info("====================================================");
				return new String[count];
			} else {

				log.info("Found [" + count + "] results in [" + fileName + "]");
				log
						.info("====================================================");
				return buf.toString().split(SystemProperties.sLineSeparator);
			}
		}
		if (!StringUtil.isEmpty(extension)) {
			String ext = FileUtil.getFileExtension(fileName);
			if (ext == null || !ext.equals(extension)) {
				return null;
			}
		}

		Object[] lines = lines(fileName, null);
		if (lines == null || lines.length==0) {
			log.error("Can not get content from [" + fileName + "]");
			return null;
		}
		FileUtil.writeString("", fileName);
		String line = null;
		String temp = null;
		StringBuffer buf = new StringBuffer();
		int index = 0;
		int count = 0;
		for (int i = 0; i < lines.length; i++) {
			line = lines[i].toString();
			index = line.indexOf(target);
			if (index != -1) {
				count++;
				temp = line;
				line = StringUtil.replace(line, target, replacement);
				buf.append(fileName).append(" - ").append(temp).append(" - ")
						.append(line).append(SystemProperties.sLineSeparator);
				log.info("[" + count + "] - [" + i + "," + index + "] - From ["
						+ temp.trim() + "]	To [" + line.trim() + "]");
			}
			FileUtil.writeLine(line, fileName, SystemProperties.sFileEncoding,
					true);
		}
		log.info("escaped time:" + String.valueOf(new Date().getTime() - start)
				+ "(ms)");
		if (count == 0) {

			log.info("Can not find [" + target + "] from [" + fileName + "]");
			log.info("====================================================");
			return new String[count];
		} else {

			log.info("Found [" + count + "] results in [" + fileName + "]");
			log.info("====================================================");
			return buf.toString().split(SystemProperties.sLineSeparator);
		}
	}

	public static boolean isExist(String fileName) {
		return new File(fileName).exists();
	}

	public static boolean isExist(String[] fileNames) {
		if (StringUtil.containNull(fileNames)) {
			return false;
		}
		for (int i = 0; i < fileNames.length; i++) {
			if (!new File(fileNames[i]).exists()) {
				log.error(fileNames[i] + " does not exist!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param sourceFileName
	 * @param targetRootDirectory
	 * @return
	 */
	public static boolean moveFiles(String sourceFileName,
			String targetRootDirectory) {
		File f = new File(sourceFileName);
		if (f.isDirectory()) {
			String[] fs = f.list();
			if (fs == null) {
				log.info("[NOT FOUND] - " + f);
				return false;
			}
			for (int i = 0; i < fs.length; i++) {
				File ff = new File(f.getPath()
						+ SystemProperties.sFileSeparator + fs[i]);
				if (!moveFiles(ff.getAbsolutePath(), targetRootDirectory)) {
					return false;
				}
			}
		} else {
			if (!copyBytes(sourceFileName, targetRootDirectory
					+ SystemProperties.sFileSeparator + f.getName(), false)) {
				return false;
			} else if (!deleteFiles(sourceFileName, null)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Copy content from source file to destination file.
	 * 
	 * @param sourceFileName
	 * @param destFileName
	 * @return
	 */
	public static boolean copyBytes(String sourceFileName, String destFileName,
			boolean append) {
		log.debug("From:" + sourceFileName + " To:" + destFileName);

		FileInputStream in = null;
		FileOutputStream out = null;
		int c = 0;

		// check if arguments are valid
		String[] argus = new String[] { sourceFileName, destFileName };
		if (StringUtil.containNull(argus)) {
			return false;
		}
		if (!new File(sourceFileName).exists()) {
			return false;
		}
		if (!new File(destFileName).exists()) {
			if (!createFile(destFileName)) {
				return false;
			}
		}

		// read from source and write to destination
		try {
			in = new FileInputStream(sourceFileName);
		} catch (FileNotFoundException e) {
			log.error(e);
			return false;
		}
		try {
			out = new FileOutputStream(destFileName, append);
		} catch (FileNotFoundException e) {
			log.error(e);
			return false;
		}
		try {
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		} catch (IOException e) {
			log.error(e);
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error(e);
				return false;
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error(e);
				return false;
			}
		}
		return true;
	}

	/**
	 * copy characters from source file to destination file
	 * 
	 * @param sourceFileName
	 * @param destFileName
	 * @return
	 */
	public static boolean copyCharacters(String sourceFileName,
			String destFileName, boolean append) {
		FileReader in = null;
		FileWriter out = null;
		int c = 0;

		String[] argus = new String[] { sourceFileName, destFileName };
		if (StringUtil.containNull(argus)) {
			log
					.info("Invalid Arguments:sourceFileName or destFileName is null");
			return false;
		}
		if (!isExist(sourceFileName)) {
			log.info("Invalid Arguments:sourceFileName does not exist");
			return false;
		}
		if (!isExist(destFileName)) {
			createFile(destFileName);
		}

		try {
			in = new FileReader(sourceFileName);
		} catch (FileNotFoundException e) {
			log.error(e);
			return false;
		}
		try {
			out = new FileWriter(destFileName, append);
		} catch (IOException e) {
			log.error(e);
			return false;
		}
		try {
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		} catch (IOException e) {
			log.error(e);
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error(e);
				return false;
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error(e);
				return false;
			}
		}
		return true;
	}

	/**
	 * copy lines from source file to destination file
	 * 
	 * @param sourceFileName
	 * @param destFileName
	 * @return
	 */
	public static boolean copyLines(String sourceFileName, String destFileName,
			boolean append) {
		BufferedReader in = null;
		PrintWriter out = null;
		String line = null;

		String[] argus = new String[] { sourceFileName, destFileName };
		if (StringUtil.containNull(argus)) {
			return false;
		}
		if (!isExist(argus)) {
			return false;
		}

		try {
			in = new BufferedReader(new FileReader(sourceFileName));
		} catch (FileNotFoundException e) {
			log.error(e);
			return false;
		}
		try {
			out = new PrintWriter(new FileWriter(destFileName), append);
		} catch (IOException e) {
			log.error(e);
			return false;
		}
		try {
			while ((line = in.readLine()) != null) {
				out.println(line);
			}
		} catch (IOException e) {
			log.error(e);
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error(e);
				return false;
			}
			if (out != null)
				out.close();
		}
		return true;
	}

	/**
	 * assume source directory has the same structure with the destination
	 * directory.
	 * 
	 * @param sourceDir -
	 *            source directory
	 * @param destinationDir -
	 *            destination directory
	 * @param filterFolderNames -
	 *            those folders that are forbidden to be synchronized, separated
	 *            by ","
	 * @param min_synchronization_break -
	 *            minious break of synchronization, the unit is millisecond
	 * @param bDelete -
	 *            whether delele excrescent files or not.
	 * @return boolean - if there is some error occurred during synchronizing.
	 */
	public static boolean synchronize(String sourceDir, String destinationDir,
			String filterFolderNames, long min_synchronization_break) {
		return synchronize(sourceDir, destinationDir, filterFolderNames,
				min_synchronization_break, 0);
	}

	/**
	 * synchronization types:<br>
	 * 0, full synchronization, update existent files, copy new files, delete
	 * excrescent destination files<br>
	 * 1, basic synchronization, update existent files and copy new files <br>
	 * 2, minimum synchronization, just copy existent files
	 */
	public static final int FULL_SYNCHRONIZATION = 0;

	public static final int BASIC_SYNCHRONIZATION = 1;

	public static final int MINIMUM_SYNCHRONIZATION = 2;

	/**
	 * assume source directory has the same structure with the destination
	 * directory.
	 * 
	 * @param sourceDir -
	 *            source directory
	 * @param destinationDir -
	 *            destination directory
	 * @param filterFolderNames -
	 *            those folders that are forbidden to be synchronized, separated
	 *            by ","
	 * @param min_synchronization_break -
	 *            minious break of synchronization, the unit is millisecond
	 * @param synchType -
	 *            0, full synchronization, update existent files, copy new
	 *            files, delete excrescent destination files<br>
	 *            1, basic synchronization, update existent files and copy new
	 *            files <br>
	 *            2, minimum synchronization, just copy new files
	 * @param bDelete -
	 *            whether delele excrescent files or not.
	 * @return boolean - if there is some error occurred during synchronizing.
	 */
	public static boolean synchronize(String sourceDir, String destinationDir,
			String filterFolderNames, long min_synchronization_break,
			int synchType) {

		// print source file and destination file.
		log.debug("[" + sourceDir + "] >>>>>> [" + destinationDir + "]");

		String[] tempFilterFolderNames = null;
		if (filterFolderNames != null && filterFolderNames.length() > 0) {
			tempFilterFolderNames = filterFolderNames.split(",");
		}
		// final long min_synchronization_break = 1000 * 60 * 5;

		File sdir = new File(sourceDir);
		File ddir = new File(destinationDir);

		String sroot = sdir.getAbsolutePath();
		String droot = ddir.getAbsolutePath();

		String[] sfiles = sdir.list();
		String[] dfiles = ddir.list();

		String sfilename = null;
		String dfilename = null;

		File sfile = null;
		File dfile = null;
		/*
		 * if full synchronization, delete excrescent files, store sameFiles
		 * files into one container. Get all destiniation files and compare with
		 * same files, if there are different files, delete them.
		 */
		ArrayList sameFiles = new ArrayList();

		long smodi = -1L;
		long dmodi = -1L;

		boolean bResult = true;
		long modiDiff = 0L;
		out: for (int i = 0; i < sfiles.length; i++) {
			sfilename = sroot + System.getProperty("file.separator")
					+ sfiles[i];
			dfilename = droot + System.getProperty("file.separator")
					+ sfiles[i];
			sfile = new File(sfilename);
			dfile = new File(dfilename);
			// filter
			if (tempFilterFolderNames != null) {
				for (int j = 0; j < tempFilterFolderNames.length; j++) {
					if (tempFilterFolderNames[j].trim().length() == 0) {
						continue;
					}
					if (sfilename.endsWith(tempFilterFolderNames[j])) {
						continue out;
					}
				}
			}
			if (sfile.isDirectory()) {
				// recursion
				synchronize(sfilename, dfilename, filterFolderNames,
						min_synchronization_break, synchType);
			} else if (sfile.isFile()) {
				smodi = sfile.lastModified();
				/*
				 * compare modify date, if the different is larger than the
				 * synchronization break, perform synchronization
				 */
				if (dfile.exists()) {
					// minumum synchronization - do not update existent files
					if (synchType == MINIMUM_SYNCHRONIZATION) {
						continue;
					}
					/*
					 * full synchronization - delete different files from source
					 * files. store same files into one container and compare
					 * with destination files later, if found different files,
					 * delete them.
					 */
					if (synchType == FileUtil.FULL_SYNCHRONIZATION) {
						sameFiles.add(dfile.getAbsolutePath());
					}
					dmodi = dfile.lastModified();
					modiDiff = smodi - dmodi;
					if (modiDiff <= min_synchronization_break) {
						continue;
					}
					log.debug("[Update file]" + dfilename);
				} else {
					log.debug("[New file]" + dfilename);
				}
				bResult = copyBytes(sfilename, dfilename, false);
				if (!bResult) {
					continue;
				}
			}
		}

		// basic synchronization - do not delete excrescent files
		if (synchType != FULL_SYNCHRONIZATION) {
			return true;
		}

		// delete different files
		if (dfiles != null && dfiles.length > 0) {
			for (int i = 0; i < dfiles.length; i++) {
				dfilename = droot + System.getProperty("file.separator")
						+ dfiles[i];
				dfile = new File(dfilename);
				if (dfile.isDirectory()) {
					continue;
				}
				if (!sameFiles.contains(dfile.getAbsolutePath())) {
					if (!deleteFiles(dfile.getAbsolutePath(), null)) {
						log.debug("[" + dfile + "] can not be deleted!");
					}
				}
			}
		}

		return bResult;
	}

	/**
	 * write specified string into specified file.
	 * 
	 * @param content
	 * @param destFileName
	 * @return
	 */
	public static boolean writeString(String content, String destFileName,
			String charset, boolean append) {
		if (!new File(destFileName).exists()) {
			createFile(destFileName);
		}
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(destFileName, append);
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(fo, charset);
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}
		try {
			writer.write(content);
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}
		try {
			writer.flush();
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}

		return true;
	}

	/**
	 * write specified bytes into destination file. User can specify the offset
	 * and length of the bytes array and if need to append to the destination
	 * file.
	 * 
	 * @param bs -
	 *            byte array, should not be null or have more than one element.
	 * @param start -
	 * @param length
	 * @param append
	 * @param destFileName
	 * @return boolean - true,write successfully;else,false
	 */
	public static boolean writeBytes(byte[] bs, int start, int length,
			boolean append, String destFileName) {
		/*
		 * check up arguments
		 */
		if (bs == null || bs.length == 0) {
			throw new IllegalArgumentException(
					bs
							+ " is invalid! It should not be null and has more than one element.");
		}
		if (start < 0 || start >= bs.length) {
			throw new IllegalArgumentException("start index:[" + start
					+ "] is invalid! It should be in the range from [0] to ["
					+ (bs.length - 1) + "] !");
		}
		if (length <= start || length > bs.length) {
			throw new IllegalArgumentException("length:[" + length
					+ "] is invalid! It should be in the range from [" + start
					+ "] to [" + (bs.length - 1) + "] !");
		}
		if (destFileName == null || destFileName.trim().length() == 0) {
			throw new IllegalArgumentException(destFileName
					+ "is invalid! It should be one valid file name!");
		}
		/*
		 * create new file is it does not exist.
		 */
		if (!new File(destFileName).exists()) {
			createFile(destFileName);
		}
		/*
		 * open inputstream to the destination file with 'append' argument.
		 * return if some exception rises.
		 */
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(destFileName, append);
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}
		/*
		 * write specified byte array to the destination file with specified
		 * offset and length.
		 */
		try {
			writer.write(bs, start, length);
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}
		/*
		 * flush the writer.
		 */
		try {
			writer.flush();
		} catch (IOException ioe) {
			log.error(ioe);
			return false;
		}

		/*
		 * return true, if everything is ok.
		 */
		return true;
	}

	/**
	 * write specified string into specified file ends with file separator.
	 * 
	 * @author wengm
	 * @param content
	 * @param destFileName
	 * @param charset
	 * @param append
	 * @return
	 * @see #writeString(String, String, String, boolean)
	 */
	public static boolean writeLine(String content, String destFileName,
			String charset, boolean append) {
		String line = new StringBuffer(content).append(
				SystemProperties.sLineSeparator).toString();
		return writeString(line, destFileName, charset, append);
	}

	/**
	 * write specified string to specified file with default encoding. old
	 * content of the file will be overwritten.
	 * 
	 * @author wengm
	 * @param content
	 * @param destFileName
	 * @return
	 * @see #writeLine(String, String, String, boolean)
	 */
	public static boolean writeString(String content, String destFileName) {
		return writeString(content, destFileName,
				SystemProperties.sFileEncoding, false);
	}

	/**
	 * get word count in specified plain file
	 * 
	 * @author wengm
	 * @param plainFileName
	 * @return
	 * @see #{@link #listWords(String)}
	 */
	public static int getWordsCount(String plainFileName) {
		return listWords(plainFileName).length;
	}

	/**
	 * get extension of the file
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i == -1) {
			return null;
		}
		return fileName.substring(i);
	}

	public static void main(String[] args) {
		FileUtil.renameFiles("B:\\Images\\Family");
	}

}
