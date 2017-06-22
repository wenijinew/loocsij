package org.loocsij.tool;

import java.util.Properties;
import java.util.ArrayList;

import org.loocsij.logger.Log;
import org.loocsij.logger.SimpleLogger;
import org.loocsij.util.PropertiesUtil;

/**
 * display properties items : key = value.
 * 
 * @author wengm
 * @since 2007-11-28
 */
public class PropertiesShower {
	private static SimpleLogger log = Log
			.getSimpleLogger(PropertiesShower.class);

	/**
	 * Display all keys and elements in specified properties. If kfilter is
	 * specified, only those pairs matched with kfilter will be displayed; if
	 * vfilter is specified, only those pairs matched with vfilter will be
	 * displayed. User can specify if value matching should be exactly with
	 * argument isValueExactMatch. If not kfilter and vfilter specified, all
	 * items in properties will be displayed.
	 * 
	 * @param prop -
	 *            instance of java.util.Properties
	 * @param kfilter -
	 *            key filter, for example, only show pairs whose key contain
	 *            "java" or starts with "sun"
	 * @param vfilter -
	 *            value filter, for example, only show pairs whose value contain
	 *            "java" or starts with "sun"
	 * @param isValueExactMatch -
	 *            true or false
	 */
	public static void showProperties(Properties prop, String kfilter,
			String vfilter, boolean isValueExactMatch) {
		java.util.Enumeration en = prop.keys();
		int count = 0;
		int kcount=0;
		int vcount = 0;
		String key = null;
		String value = null;
		while (en.hasMoreElements()) {
			key = en.nextElement().toString();
			value = prop.getProperty(key);
			if (kfilter != null && kfilter.length() > 0) {
				if (isValueExactMatch) {
					if (key.equals(kfilter)) {
						log.info("[" + kcount++ + "]" + key + "=" + value);
					}
				} else if (key.equalsIgnoreCase(kfilter)) {
					log.info("[" + kcount++ + "]" + key + "=" + value);
				} else if (key.indexOf(kfilter) >= 0) {
					log.info("[" + kcount++ + "]" + key + "=" + value);
				}
			} else if (vfilter != null && vfilter.length() > 0) {
				if (isValueExactMatch) {
					if (value.equals(vfilter)) {
						log.info("[" + vcount++ + "]" + key + "=" + value);
					}
				} else if (value.equalsIgnoreCase(vfilter)) {
					log.info("[" + vcount++ + "]" + key + "=" + value);
				} else if (value.indexOf(vfilter) >= 0) {
					log.info("[" + vcount++ + "]" + key + "=" + value);
				}
			} else {
				log.info("[" + count++ + "]" + key + "=" + value);
			}
		}
	}

	/**
	 * show properties items
	 * 
	 * @param prop
	 */
	public static void showProperties(Properties prop) {
		java.util.Enumeration en = prop.keys();
		int count = 0;
		String key = null;
		String value = null;
		while (en.hasMoreElements()) {
			key = en.nextElement().toString();
			value = prop.getProperty(key);
			count++;
			log.info("[" + count + "]" + key + "=" + value);
		}
	}

	/**
	 * show difference between two properties
	 * 
	 * @param p1
	 * @param p2
	 */
	public static void showDifferenceOfMultipleProperties(Properties p1,
			Properties p2) {
		java.util.Enumeration keys1 = p1.keys();
		String key1 = null;

		java.util.Enumeration keys2 = p2.keys();
		String key2 = null;

		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();

		while (keys1.hasMoreElements()) {
			key1 = keys1.nextElement().toString();
			if (!p2.containsKey(key1)) {
				al1.add(key1);
			}
		}
		while (keys2.hasMoreElements()) {
			key2 = keys2.nextElement().toString();
			if (!p1.containsKey(key2)) {
				al2.add(key2);
			}
		}
		if (al1.size() > 0) {
			log
					.info("Properties 1's items which are not contained in Properties 2:");
			for (int i = 0; i < al1.size(); i++) {
				log.info("[" + i + "]" + al1.get(i) + "="
						+ p1.getProperty(al1.get(i).toString()));
			}
		}
		if (al2.size() > 0) {
			log
					.info("Properties 2's items which are not contained in Properties 1:");
			for (int i = 0; i < al2.size(); i++) {
				log.info("[" + i + "]" + al2.get(i) + "="
						+ p1.getProperty(al2.get(i).toString()));
			}
		}
	}

	public static String[] searchKeys(Properties prop, String kfilter,
			String vfilter, boolean isValueExactMatch) {
		java.util.Enumeration en = prop.keys();
		int count = 0;
		String key = null;
		String value = null;
		ArrayList al = new ArrayList();
		while (en.hasMoreElements()) {
			key = en.nextElement().toString();
			value = prop.getProperty(key);
			count++;
			if (kfilter != null && kfilter.length() > 0) {
				if (key.equals(kfilter)) {
					al.add(key);
					log.info("[" + count + "]" + key + "=" + value);
				}
			}
			if (vfilter != null && vfilter.length() > 0) {
				if (isValueExactMatch) {
					if (value.equals(vfilter)) {
						al.add(key);
						log.info("[" + count + "]" + key + "=" + value);
					}
				} else if (value.equalsIgnoreCase(vfilter)) {
					al.add(key);
					log.info("[" + count + "]" + key + "=" + value);
				} else if (value.indexOf(vfilter) >= 0) {
					al.add(key);
					log.info("[" + count + "]" + key + "=" + value);
				}
			}
		}
		if (al.size() == 0) {
			return new String[0];
		}
		String[] results = new String[al.size()];
		al.toArray(results);
		return results;
	}

	public static String[] searchPairs(Properties prop, String[] keys) {
		if (prop == null || prop.size() == 0 || keys == null
				|| keys.length == 0) {
			return new String[0];
		}
		ArrayList al = new ArrayList();
		String value = null;
		String pair = null;
		for (int i = 0; i < keys.length; i++) {
			value = prop.getProperty(keys[i]);
			if (value != null) {
				pair = keys[i] + "=" + value;
				al.add(pair);
				log.info(pair);
			}
		}
		if (al.size() == 0) {
			return new String[0];
		}
		String[] results = new String[al.size()];
		al.toArray(results);
		return results;
	}

	/**
	 * show properties
	 * 
	 * @param args -
	 *            args[0]:properties file path<br>
	 *            args[1]:key filter<br>
	 *            args[2]:value filter<br>
	 *            args[3]:boolean value used to specify if value filter is exact
	 *            matching<br>
	 *            args[4]:properties file path2, used to compare items
	 */
	public static void main(String[] args) {
		Properties p1 = PropertiesUtil.getPropertiesFromFile(args[0]);
		showProperties(p1,"Are you sure","",false);
	}
}
