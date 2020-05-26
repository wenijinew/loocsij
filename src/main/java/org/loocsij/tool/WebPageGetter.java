package org.loocsij.tool;

import org.loocsij.web.WebAccessor;

public class WebPageGetter {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = args[0];
//		String targetFile = args[1];
//		WebAccessor.storeWebPage(url, targetFile);
		String str = WebAccessor.getWebResponse(url);
		System.out.println(str);
//		System.out.println(org.loocsij.util.EncodingUtil.toUnicodeFormat(str));
	}
}
 