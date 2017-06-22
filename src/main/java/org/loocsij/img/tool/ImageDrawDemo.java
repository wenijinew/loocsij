package org.loocsij.img.tool;

import javax.swing.JApplet;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public class ImageDrawDemo extends JApplet {
	private static final long serialVersionUID = 1L;

	double totalAngle; // Current angular position of sprite

	double spriteAngle; // Rotation angle of sprite about its center

	BufferedImage sprite; // Stores reference to the sprite

	int spriteSize = 100; // Diameter of the sprite

	Ellipse2D.Double circle; // A circle - part of the sprite

	Line2D.Double line; // A line - part of the sprite

	Color[] colors = { Color.red, Color.yellow, Color.green, Color.blue,
			Color.cyan, Color.pink, Color.magenta, Color.orange };

	java.util.Timer timer; // Timer for the animation

	long interval = 50; // Time interval msec between repaints

	BufferedImage createSprite(int spriteSize) {
		BufferedImage sprite = new BufferedImage(spriteSize, spriteSize,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = sprite.createGraphics(); // Context for buffered
		g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2D
				.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,
						0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, spriteSize,
				spriteSize);
		g2D.fill(rect);
		return sprite;
	}
}
