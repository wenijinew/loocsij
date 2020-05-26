/**
 * 
 */
package org.loocsij.gui.awt;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author wengm
 * 
 */
public class Window extends Frame implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1730294374747864855L;
	/**
	 * default properties: width,height,visible,title
	 */
	private static final int width = 400;
	private static final int height = 300;
	private static final String title = "IIS";
	
	private static Rectangle rec;
	private static double maxX;
	private static double maxY;
	private static Point initialPoint;
	
	/**
	 * 
	 *
	 */
	private void initial() {
		this.setTitle(title);
		this.setSize(width, height);
		this.setLocation(Frame.MAXIMIZED_HORIZ/2, Frame.MAXIMIZED_VERT/2);
		rec = this.getMaximizedBounds();
		if(rec!=null){
			maxX = rec.getMaxX();
			maxY = rec.getMaxY();
			initialPoint = new Point((int)(maxX/2.0),(int)(maxY/2.0));
			this.setLocation(initialPoint);
		}
		this.addWindowListener(this);
	}

	/**
	 * @throws HeadlessException
	 */
	public Window() throws HeadlessException {
		initial();
	}

	/**
	 * @param gc
	 */
	public Window(GraphicsConfiguration gc) {
		super(gc);
		initial();
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public Window(String title) throws HeadlessException {
		super(title);
		initial();
	}

	/**
	 * @param title
	 * @param gc
	 */
	public Window(String title, GraphicsConfiguration gc) {
		super(title, gc);
		initial();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		this.dispose();
	}

	public void windowDeactivated(WindowEvent e) {

	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

}
