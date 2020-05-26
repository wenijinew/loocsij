package org.loocsij;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

public abstract class SystemProperties {
	private static Logger log = LogManager.getLogger(SystemProperties.class);
	
	public static String sFileSeparator = System.getProperty("file.separator");

	public static String sLineSeparator = System.getProperty("line.separator");

	public static String sFileEncoding = System.getProperty("file.encoding");

	public static String sUserHome = System.getProperty("user.home");
	
	public static void showSystemProperties(){
		Properties properties = System.getProperties();
		Enumeration enums = properties.keys();
		Object key = null;
		while(enums.hasMoreElements()){
			key = enums.nextElement();
			log.info(key+"="+System.getProperty(key.toString()));
		}
	}
	
	public static void main(String[] strs){
		showSystemProperties();
	}
}
