/**
 * 
 */
package org.loocsij.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author wengm
 *
 */
public class WebHttpURLConnection extends HttpURLConnection {

	/**
	 * @param arg0
	 */
	public WebHttpURLConnection(URL arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#disconnect()
	 */
	public void disconnect() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see java.net.HttpURLConnection#usingProxy()
	 */
	public boolean usingProxy() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.net.URLConnection#connect()
	 */
	public void connect() throws IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
