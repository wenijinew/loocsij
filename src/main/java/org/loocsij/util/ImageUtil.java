/**
 * 
 *//*

package org.loocsij.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import net.jmge.gif.Gif89Encoder;

import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;

*/
/**
 * @author wsxspring
 * 
 *//*

public class ImageUtil {
	private static Logger log = Log.getLogger(ImageUtil.class);

	*/
/**
	 * 
	 *//*

	public ImageUtil() {
		// TODO Auto-generated constructor stub
	}

	*/
/**
	 * load image by given image file path
	 * 
	 * @param imageFilePath
	 * @return
	 *//*

	public static Image loadImage(String imageFilePath) {
		return new Component() {
			*/
/**
			 * 
			 *//*

			private static final long serialVersionUID = -1086242631525898677L;
		}.getToolkit().getImage(imageFilePath);
	}

	*/
/**
	 * load BufferedImage by given image file path
	 * 
	 * @param imageFilePath
	 * @return
	 *//*

	public static BufferedImage loadBufferedImage(String imageFilePath) {
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File(imageFilePath));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Can not read image file:" + imageFilePath, e);
		}
		return bimg;
	}

	*/
/**
	 * draw given image with default graphics object onto given container object
	 * 
	 * @param container
	 * @param image
	 *//*

	public static void drawImage(Container container, Image image) {
		new Component() {
			*/
/**
			 * 
			 *//*

			private static final long serialVersionUID = 0L;
		}.getGraphics().drawImage(image, 0, 0, container);
	}

	*/
/**
	 * draw given image with given graphics object onto given container object
	 * 
	 * @param graphics
	 * @param container
	 * @param image
	 *//*

	public static void drawImage(Graphics graphics, Container container,
			Image image) {
		graphics.drawImage(image, 0, 0, container);
	}

	public static final String TYPE_GIF = "gif";

	public static final String TYPE_JPEG = "jpeg";

	public static final String TYPE_PNG = "png";

	public static final String TYPE_BMP = "bmp";

	public static final String TYPE_NOT_AVAILABLE = "na";

	private static ColorModel getColorModel(Image image)
			throws InterruptedException, IllegalArgumentException {
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		if (!pg.grabPixels())
			throw new IllegalArgumentException();
		return pg.getColorModel();
	}

	private static void loadImage(Image image) throws InterruptedException,
			IllegalArgumentException {
		Component dummy = new Component() {
			private static final long serialVersionUID = 1L;
		};
		MediaTracker tracker = new MediaTracker(dummy);
		tracker.addImage(image, 0);
		tracker.waitForID(0);
		if (tracker.isErrorID(0))
			throw new IllegalArgumentException();
	}

	public static BufferedImage createBufferedImage(Image image)
			throws InterruptedException, IllegalArgumentException {
		loadImage(image);
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		ColorModel cm = getColorModel(image);
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage bi = gc.createCompatibleImage(w, h, cm.getTransparency());
		Graphics2D g = bi.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bi;
	}

	public static BufferedImage readImage(InputStream is) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	public static BufferedImage readImage(byte[] imageByte) {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageByte);
		BufferedImage image = readImage(bais);
		return image;
	}

	public static void encodeGIF(BufferedImage image, OutputStream out)
			throws IOException {
		Gif89Encoder encoder = new Gif89Encoder(image);
		encoder.encode(out);
	}

	*/
/**
	 * 
	 * @param bi
	 * @param type
	 * @param out
	 *//*

	public static void printImage(BufferedImage bi, String type,
			OutputStream out) {
		try {
			if (type.equals(TYPE_GIF))
				encodeGIF(bi, out);
			else
				ImageIO.write(bi, type, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	*/
/**
	 * Get image type from byte[]
	 * 
	 * @param textObj
	 *            image byte[]
	 * @return String image type
	 *//*

	public static String getImageType(byte[] textObj) {
		String type = TYPE_NOT_AVAILABLE;
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			bais = new ByteArrayInputStream(textObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				if (reader instanceof GIFImageReader) {
					type = TYPE_GIF;
				} else if (reader instanceof JPEGImageReader) {
					type = TYPE_JPEG;
				} else if (reader instanceof PNGImageReader) {
					type = TYPE_PNG;
				} // else if (reader instanceof BMPImageReader) {
				// type = TYPE_BMP;
				// }
				reader.dispose();
			}
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ioe) {
					log.error("", ioe);
				}
			}
			if (mcis != null) {
				try {
					mcis.close();
				} catch (IOException ioe) {
					log.error("", ioe);
				}
			}
		}

		return type;
	}

	*/
/**
	 * @param args
	 *//*

	public static void main(String[] args) {

	}

}
*/
