package org.loocsij.gui.game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BallApplet extends Applet implements Runnable {
	int width = 500;

	int height = 500;

	/**
	 * x_pos and y_pos are the left and top point of Oval
	 */
	int x_pos = 0;

	int y_pos = height / 2;

	int x_bit = 2;

	int y_bit = 2;

	int radius = 50; // Radius des Balles

	private Image dbImage;

	private Graphics dbg;
	
	AudioClip bounce; 


	public void init() {
	}

	public void start() {
		// define a new thread
		Thread th = new Thread(this);
		// start this thread
		th.start();
	}
	
	public void s() throws IOException{
		throw new FileNotFoundException();
	}
	
	public void ss(){
		try {
			s();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
	}

	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#update(java.awt.Graphics)
	 */
	public void update(Graphics g) {
		// super.update(g);

		// initialize buffer
		if (dbImage == null) {
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}

		// clear screen in background
		dbg.setColor(getBackground());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// draw elements in background
		dbg.setColor(getForeground());
		paint(dbg);

		// draw image on the screen
		g.drawImage(dbImage, 0, 0, this);
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);

		g.drawLine(0, height / 2 - 4 * radius, width, height / 2 - 4 * radius);

		g.drawLine(0, height / 2 + radius, width, height / 2 + radius);

		g.drawLine(width, radius, width, height / 2 + radius);

		// set color
		g.setColor(Color.red);

		// paint a filled colored circle
		g.fillOval(x_pos, y_pos - radius, x_bit * radius, y_bit * radius);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8720367119312774600L;

	public void run() {
		try {
			// Stop thread for 20 milliseconds
			Thread.sleep(1500);

		} catch (InterruptedException ex) {
			// do nothing

		}
		int sleep = 5;

		int bottom = y_pos + radius;

		int top = y_pos - 3 * radius;
		// lower ThreadPriority
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		// run a long while (true) this means in our case "always"
		int d = 1;
		while (true) {
			while (x_pos < this.width - 2 * radius) {
				x_pos += 5;
				if (y_pos == top) {
					d = 1;
				}
				if (y_pos == (bottom - radius)) {
					d = -1;
				}
				// if (y_pos == top || y_pos == (bottom - radius)) {
				// y_bit = 1;
				// } else {
				// y_bit = 2;
				// }
				// if (x_pos == this.width - 2 * radius) {
				// x_bit = 1;
				// } else {
				// x_bit = 2;
				// }
				y_pos = y_pos + d * 5;
				System.out.println(x_pos);
				// repaint the applet
				repaint();
				if (y_bit == 1 || x_pos == 1) {
					try {
						// Stop thread for 20 milliseconds
						Thread.sleep(sleep * 10);

					} catch (InterruptedException ex) {
						// do nothing

					}
				}
				try {
					// Stop thread for 20 milliseconds
					Thread.sleep(sleep);

				} catch (InterruptedException ex) {
					// do nothing

				}
				// set ThreadPriority to maximum value
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

			}
			while (x_pos > 0) {
				x_pos -= 5;
				if (y_pos == top) {
					d = 1;
				}
				if (y_pos == (bottom - radius)) {
					d = -1;
				}
				// if (y_pos == top || y_pos == (bottom - radius)) {
				// y_bit = 1;
				// } else {
				// y_bit = 2;
				// }
				// if (x_pos == 0) {
				// x_bit = 1;
				// }else {
				// x_bit = 2;
				// }
				y_pos = y_pos + d * 5;
				System.out.println(x_pos);

				// repaint the applet
				repaint();
				if (y_bit == 1 || x_pos == 1) {
					try {
						// Stop thread for 20 milliseconds
						Thread.sleep(sleep * 10);

					} catch (InterruptedException ex) {
						// do nothing

					}
				}

				try {
					// Stop thread for 20 milliseconds
					Thread.sleep(sleep);

				} catch (InterruptedException ex) {
					// do nothing

				}
				// set ThreadPriority to maximum value
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

			}
		}
	}

}
