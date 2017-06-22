/**
 * 
 */
package org.loocsij.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import org.loocsij.SystemProperties;
import org.loocsij.logger.Log;

/**
 * Create zipfile and compress files in specified root
 * 
 * @author wengm
 * 
 */
public class ZipUtil {
	private static Logger log = Log.getLogger(ZipUtil.class);

	/**
	 * 
	 */
	public ZipUtil() {
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		compress(args[0], args[1]);
	}

	public static void compress(String directory, String zipFileName) {
		compress(directory, zipFileName, null);
	}

	/**
	 * compress all extracted resource properties into one zip file.
	 * 
	 * @author wengm
	 */
	public static void compress(String directory, String zipFileName,
			String destiDirectory) {
		if (directory == null || zipFileName == null) {
			throw new RuntimeException(
					"Usage:java util.ZipFiles [directory] [zipFileName]");
		}

		/*
		 * create new zip file, if it exists before, delete it
		 */
		File fdir = new File(directory);
		if (!fdir.exists()) {
			log.error("Can not find directory:" + directory);
			return;
		}
		File f = new File(zipFileName);
		if (f.getParent() == null) {
			zipFileName = (destiDirectory == null ? fdir.getParent()
					: destiDirectory) + SystemProperties.sFileSeparator
							+ zipFileName;
			f = new File(zipFileName);
		}
		if (!f.exists()) {
			String p = f.getParent();
			File pf = new File(p);
			if (!pf.exists()) {
				pf.mkdirs();
			}
		} else {
			f.delete();
		}
		/*
		 * get source files, then create zip file
		 */
		File dirF = new File(directory);
		File[] files = dirF.listFiles();
		try {
			f.createNewFile();
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		}

		log.debug("[COMPRESS] - [START] - From [" + directory + "] To ["
				+ f.getName() + "]");

		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		/*
		 * create writers
		 */
		try {
			fos = new FileOutputStream(zipFileName);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		}
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
					return;
				}
			}
		}

		/*
		 * set compress level
		 */
		zos.setLevel(6);
		zipFiles(files, dirF.getName(), zos);

		try {
			zos.close();
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		}
		try {
			fos.close();
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		}
		log.debug("[COMPRESS] - [END] - From [" + directory + "] To ["
				+ f.getName() + "]");
	}

	/**
	 * compress specified file into specified zip file
	 * 
	 * @param fileName
	 * @param zipFileName
	 * @author wengm
	 */
	private static void zipFiles(File[] files, String parentName,
			ZipOutputStream zos) {
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				log.debug("[COMPRESS] - " + files[i]);
				zipFile(files[i], parentName, zos);
			}
			if (files[i].isDirectory()) {
				File[] subfs = files[i].listFiles();
				zipFiles(subfs, parentName + SystemProperties.sFileSeparator
						+ files[i].getName(), zos);
			}
		}
	}

	private static void zipFile(File file, String parentName,
			ZipOutputStream zos) {
		FileInputStream fis = null;
		/*
		 * create reader
		 */
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException fnfe) {
			log.error(fnfe.getMessage(), fnfe);
			return;
		}
		ZipEntry entry = new ZipEntry(parentName
				+ SystemProperties.sFileSeparator + file.getName());
		/*
		 * set next entry location
		 */
		try {
			zos.putNextEntry(entry);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		}
		byte[] buf = new byte[1024];
		/*
		 * write next entry
		 */
		try {
			while (fis.read(buf) != -1) {
				zos.write(buf);
			}
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
					return;
				}
			}
		}
	}

	/**
	 * decompress specified zipfile to specified target path.
	 * 
	 * @author wengm
	 * @param zipFileName
	 * @param target
	 */
	public static void decompress(String zipFileName, String target) {
		int buffer = 2048;

		FileInputStream fi = null;
		try {
			fi = new FileInputStream(zipFileName);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		}
		ZipInputStream zi = new ZipInputStream(new BufferedInputStream(fi));
		ZipEntry entry = null;
		FileOutputStream fo = null;
		BufferedOutputStream bo = null;
		byte[] bs = new byte[buffer];
		int count = -1;
		try {
			while ((entry = zi.getNextEntry()) != null) {
				fo = new FileOutputStream(target
						+ SystemProperties.sFileSeparator + entry.getName());
				bo = new BufferedOutputStream(fo, buffer);
				while ((count = zi.read(bs, 0, buffer)) != -1) {
					bo.write(bs, 0, count);
				}
				bo.flush();
				bo.close();
				fo.close();
			}
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			return;
		} finally {
			if (zi != null) {
				try {
					zi.close();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
					return;
				}
			}
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
					return;
				}
			}
		}
	}

	/**
	 * get properties from compressed properties file
	 * 
	 * @param zipFileName
	 * @param locale
	 * @return
	 */
	public static ArrayList getPropertiesFromZip(String zipFileName) {
		if (zipFileName == null) {
			throw new RuntimeException(
					"Usage:getProperties(String z,String l),both z and l can not be NULL!");
		}
		ArrayList props = new ArrayList();
		ZipFile zip = null;
		try {
			zip = new ZipFile(zipFileName);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		// load from file:following code only run one time for each locale
		Properties prop = new Properties();
		Enumeration e = zip.entries();
		ZipEntry entry = null;
		InputStream in = null;

		while (e.hasMoreElements()) {
			entry = (ZipEntry) e.nextElement();
			try {
				in = zip.getInputStream(entry);
				prop.load(in);
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
			props.add(prop);
		}

		return props;
	}

}
