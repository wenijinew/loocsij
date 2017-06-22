/**
 * 
 */
package org.loocsij.gui;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * @author wengm
 * 
 */
public class CommonWindowListener extends WindowAdapter {

	/**
	 * 
	 */
	public CommonWindowListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowActivated(arg0);
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowClosed(arg0);
	}

	public void windowClosing(WindowEvent we) {
		super.windowClosing(we);
		Frame source = (Frame)we.getSource();
		if(source.isDisplayable()) {
			source.dispose();
		}
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowDeactivated(arg0);
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowDeiconified(arg0);
	}

	public void windowGainedFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowGainedFocus(arg0);
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowIconified(arg0);
	}

	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowLostFocus(arg0);
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowOpened(arg0);
	}

	public void windowStateChanged(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowStateChanged(arg0);
	}

	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
