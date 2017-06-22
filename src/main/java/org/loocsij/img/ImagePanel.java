package org.loocsij.img;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.image.BufferedImage;

import org.loocsij.gui.util.GUIUtil;

public class ImagePanel extends Panel {
	private static final long serialVersionUID = -3672846940140730492L;

	private int width;

	private int height;

	private int x;

	private int y;

	private BufferedImage bufferedImage;

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @param bufferedImage
	 *            the bufferedImage to set
	 */
	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public ImagePanel() {
		this.setAreaSize(0, 0);
	}

	public void setAreaSize(int width, int height) {
		Insets insets = getInsets();
		int maxW = GUIUtil.getScreenWidth() - insets.left - insets.right;
		int maxH = GUIUtil.getScreenHeight() - insets.top - insets.bottom;
		this.width = width > maxW ? maxW : width;
		this.height = height > maxH ? maxH : height;
		if (this.x == 0) {
			System.out.println(this.x);
			this.x = insets.left;
		}
		if (this.y == 0) {
			this.y = insets.top;
		}
		this.setSize(this.width, this.height);
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (this.bufferedImage == null) {
			System.out.println("A");
			return;
		}
		Insets insets = getInsets();
		g.drawImage(bufferedImage, insets.left + this.x, insets.top + this.y,
				this.width, this.height, this);
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int windth) {
		this.width = windth;
	}
}
