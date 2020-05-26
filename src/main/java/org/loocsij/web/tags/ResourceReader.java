package org.loocsij.web.tags;

import org.loocsij.util.PropertiesUtil;

public class ResourceReader {

	public ResourceReader() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return String
	 * @author wengm
	 * @since 2008-11-29
	 */
	public static String getResourceValue(String locale,String key){
		String resourceFileName = getResourceFileName(locale);
		if(resourceFileName==null){
			return null;
		}
		return PropertiesUtil.getProperty(resourceFileName, key);
	}

	public static String getResourceFileName(String locale) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return void
	 * @author wengm
	 * @since 2008-11-29
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
