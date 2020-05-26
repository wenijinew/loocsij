/**
 * 
 */
package org.loocsij.web;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @author wengm
 *
 */
public class WebURLStreamHandler extends URLStreamHandler {

	/**
	 * 
	 */
	public WebURLStreamHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	protected URLConnection openConnection(URL arg0) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
