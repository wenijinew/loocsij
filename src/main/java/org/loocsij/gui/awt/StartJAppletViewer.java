/**
 * 
 */
package org.loocsij.gui.awt;

import javax.swing.*;

import java.awt.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;

/**
 * Illustrates how a JFrame subclass can implement the AppletStub &
 * AppletContext interfaces to build a rudimentary appletviewer.
 * 
 * Here the getDocumentBase (), getImage (URL url), and getCodeBase () were
 * implemented in detail. You will need to implement the other methods as
 * needed.
 */
public class StartJAppletViewer extends JFrame implements ActionListener,
		AppletStub, AppletContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Create a menubar for the frame and a File menu. * */
	StartJAppletViewer() {
		setTitle("MyAppletViewer");
		JMenu m = new JMenu("File");
		JMenuItem mi = new JMenuItem("Quit");
		mi.addActionListener(this);
		m.add(mi);
		JMenuBar mb = new JMenuBar();
		mb.add(m);
		setJMenuBar(mb);

	} // ctor

	// AppletStub methods

	/**
	 * Use the File class (See lecture 9) and the System properties to create a
	 * URL from the current working directory.
	 */
	public URL getDocumentBase() {
		URL url = null;
		try {
			File file = new File(System.getProperty("user.dir"));
			url = file.toURL();
		} catch (MalformedURLException e) {
			System.out.println("Bad URL");
			url = null;
		}
		return url;
	} // getDocumentBase

	/**
	 * Provide getCodeBae that returns a URL that points to the local directory
	 * where this program is running.
	 */
	public URL getCodeBase() {
		URL url = null;
		try {
			File file = new File(System.getProperty("user.dir"));
			url = file.toURL();
			System.out.println("url=" + url);
		} catch (MalformedURLException e) {
			System.out.println("Bad URL");
			url = null;
		}
		return url;

	} // getCodeBase

	// Note: ignore the warning that there is also a private isActive
	// method in the java.awt.Window class.
	public boolean isActive() {
		return true;
	}

	public String getParameter(String name) {
		return "";
	}

	public AppletContext getAppletContext() {
		return this;
	}

	public void appletResize(int width, int height) {
	}

	// AppletContext methods

	// Use the toolkit to get the image from the local directory
	public Image getImage(URL url) {
		String imgName = url.getFile();
		return Toolkit.getDefaultToolkit().getImage(imgName);
	}

	public AudioClip getAudioClip(URL url) {
		return null;
	}

	public Applet getApplet(String name) {
		return null;
	}

	public Enumeration getApplets() {
		return null;
	}

	public void showDocument(URL url) {
	}

	public void showDocument(URL url, String target) {
	}

	public void showStatus(String status) {
	}

	// New AppletContext methods added in vers.1.4
	public void setStream(String key, InputStream stream) {
	}

	public Iterator getStreamKeys() {
		return null;
	}

	public InputStream getStream(String key) {
		return null;
	}

	/**
	 * Create the viwer and load the applet into it.
	 */
	public static void main(String[] args) {

		// Create an instance of this frame class for holding the applet
		StartJAppletViewer viewer = new StartJAppletViewer();

		// Replace "_JApplet" with the applet of interest.
		JApplet applet = new JApplet();

		applet.setStub(viewer);

		// Call init () to do whatever initialization can be
		// done outside of a browser environment.
		applet.init();

		viewer.getContentPane().add("Center", applet);

		viewer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		viewer.pack();
		viewer.setSize(300, 300);
		viewer.setVisible(true);

	} // main

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Quit"))
			dispose();
		System.exit(0);
	} // actionPerformed
} // class StartJAppletViewer
