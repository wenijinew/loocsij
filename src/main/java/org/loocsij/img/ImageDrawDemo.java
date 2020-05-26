package org.loocsij.img;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

import org.loocsij.gui.util.GUIUtil;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

public class ImageDrawDemo extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	double totalAngle; // Current angular position of sprite

	double spriteAngle; // Rotation angle of sprite about its center

	//ImagePanel imagePanel; // Panel to display animation

	BufferedImage sprite; // Stores reference to the sprite

	int spriteSize = 100; // Diameter of the sprite

	Ellipse2D.Double circle; // A circle - part of the sprite

	Line2D.Double line; // A line - part of the sprite

	// Colors used in sprite
	Color[] colors = { Color.red, Color.yellow, Color.green, Color.blue,
			Color.cyan, Color.pink, Color.magenta, Color.orange };

	java.util.Timer timer; // Timer for the animation

	long interval = 50; // Time interval msec between repaints

	public static BufferedImage createSprite(int spriteSize) {
		// Create image with RGB and alpha channel
		BufferedImage sprite = new BufferedImage(spriteSize, spriteSize,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2D = sprite.createGraphics(); // Context for buffered
													// image
		//		 Set best alpha interpolation quality
		  g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, 
		RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		
		// Clear image with transparent alpha by drawing a rectangle
		g2D
				.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,
						0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, spriteSize,
				spriteSize);
		g2D.fill(rect);
		// plus the rest of the method...
		return sprite;
	}
	
	public static void captureScreenWithTitle(String title, int titleHeight,Font titleFont, Color titleBackgroundColor, Color titleFontColor){
		BufferedImage screenImage = GUIUtil.captureScreen();
		BufferedImage titleImage = new BufferedImage(screenImage.getWidth(),titleHeight,BufferedImage.TYPE_INT_RGB);
		Graphics2D titleGraph = titleImage.createGraphics();
		titleGraph.setBackground(titleBackgroundColor);
		titleGraph.setColor(titleFontColor);
		titleGraph.setFont(titleFont);
		titleGraph.drawString(title, 5, (int)(titleHeight*2/3.0));
		titleGraph.dispose();
		BufferedImage whole = new BufferedImage(screenImage.getWidth(),screenImage.getHeight()+titleHeight,BufferedImage.TYPE_INT_RGB);
		Graphics2D wholeGraph = whole.createGraphics();
		wholeGraph.drawImage(titleImage, 0, 0, null);
		wholeGraph.drawImage(screenImage, 0, titleHeight, null);
		wholeGraph.dispose();
		try {
			ImageIO.write(whole, "jpg", new File("B:\\whole.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] strs){
//		try {
			captureScreenWithTitle("I love java - Loocsij Technology",50,new Font("Arial",Font.PLAIN,30),new Color(0,0,0), new Color(255,0,0));
//			BufferedImage image =ImageIO.read(new File("B:\\Images\\Family\\10.jpg"));
//			Graphics graph = image.getGraphics();
//			graph.setColor(ImageUtil.getRandomColor(200, 55));
//			graph.fillRect(0, 0, 30, 30);
//			graph.dispose();
//			Graphics2D graph = image.createGraphics();
//			graph.setColor(ImageUtil.getRandomColor(50, 55));
//			graph.setColor(new Color(255,0,0));
//			graph.fillRect(0, 0, 200, 50);
//			graph.setColor(new Color(0,0,0));
//			graph.setFont(new Font("Arial",Font.BOLD,30));
//			graph.drawString("Laopo, I love you!", 0, 20);
//			graph.drawString("The boy is very strong!", 0, 30);
//			graph.dispose();
//			ImageIO.write(image, "jpg", new File("b:\\screen.jpg"));
//			ImageIO.write(image, "jpg", new File("b:\\boy.jpg"));
//			ImageIO.write(image, "jpg", new File("B:\\Images\\Family\\laopo101.jpg"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
}
