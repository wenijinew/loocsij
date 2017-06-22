package org.loocsij.gui.game;

import java.applet.Applet;
import java.awt.Graphics;

public class FirstApplet extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see java.applet.Applet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("destroy");
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		// TODO Auto-generated method stub
		super.init();System.out.println("init");
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	public void start() {
		// TODO Auto-generated method stub
		super.start();System.out.println("start");
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();System.out.println("stop");
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);System.out.println("paint");
	}

	
}
