package org.loocsij.logger;

import java.io.*;
import java.util.Date;

import org.loocsij.SystemProperties;
import org.loocsij.util.DateUtil;
import org.loocsij.web.conf.AppInfo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * Provide simple log function. Instance will be created with the invoker's
 * class name such as com.foo.Base. The log file will be created with the class
 * name suffiexed with current date. The log content will be displaied at
 * console and stored in log file.
 * 
 * @author wengm
 * @see SimpleLogger
 */
public class Log implements SimpleLogger {
	/**
	 * log4j configuration file
	 */
	private static String configuration = "log4j.properties";

	public static String getConfiguration() {
		return configuration;
	}
	/**
	 * get instance of Logger of log4j framework
	 * 
	 * @param clazz
	 * @return org.apache.log4j.Logger
	 */
	public static Logger getLogger(Class clazz) {
		return getLogger(clazz,AppInfo.getConfPath()+SystemProperties.sFileSeparator+"log4j.properties");
	}

	public static Logger getLogger(Class clazz,String configuration) {
		Logger log = Logger.getLogger(clazz);
		PropertyConfigurator.configureAndWatch(configuration);
		return log;
	}
	/**
	 * get simple logger with specified Class
	 * 
	 * @param clazz
	 * @return SimpleLogger
	 */
	public static SimpleLogger getSimpleLogger(Class clazz) {
		return new Log(clazz);
	}
	public static void main(String[] strs) {
		SimpleLogger log = Log.getSimpleLogger(org.loocsij.util.StringUtil.class);
		log.info("Are you dying?");
	}

	public static void setConfiguration(String configuration){
		Log.configuration = configuration;
	}
	/**
	 * invoker class
	 */
	private Class clazz;
	
	/**
	 * writer
	 */
	private OutputStreamWriter out;
 
	/**
	 * exception occurred when create log file
	 */
	private boolean initFail = false;

	/**
	 * create new instance of Log with specified Class
	 * 
	 * @param clazz
	 */
	private Log(Class clazz) {
		FileOutputStream fo = null;
		if (out == null) {
			try {
				fo = new FileOutputStream("log"
						+ SystemProperties.sFileSeparator + clazz.getName()
						+ "_" + DateUtil.getSimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log", true);
				out = new OutputStreamWriter(fo);
			} catch (Exception ioe) {
				ioe.printStackTrace();
				initFail = true;
			}
		}
		this.clazz = clazz;
	}

	/**
	 * log exception information
	 */
	public void error(Exception e) {
		if (clazz == null) {
			throw new RuntimeException("clazz == null");
		}
		printStackTrace(e);
		e.printStackTrace();
	}

	/**
	 * log specified description and exception
	 */
	public void error(Object errorDes, Exception e) {
		if (clazz == null) {
			throw new RuntimeException("clazz == null");
		}
		info(errorDes.toString());
		printStackTrace(e);
		/*
		 * console
		 */
		e.printStackTrace();
	}

	/**
	 * log specified information
	 */
	public void info(String verbose) {
		if (clazz == null) {
			throw new RuntimeException("clazz == null");
		}
		verbose = DateUtil.getSimpleDateFormat("HH:mm:ss.SSS").format(new Date())+" - "+verbose;
		if (!initFail) {
			try {
				out.write(verbose);
				out.write(SystemProperties.sLineSeparator);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(verbose);
				return;
			}
		}
		/*
		 * console
		 */
		System.out.println(verbose);
	}
	/**
	 * log stack trace of specified exception
	 * 
	 * @param e
	 */
	private void printStackTrace(Exception e) {
		StackTraceElement[] stes = e.getStackTrace();
		StackTraceElement ste = null;
		for (int i = 0; i < stes.length; i++) {
			ste = stes[i];
			info(ste.toString());
		}
	}
}