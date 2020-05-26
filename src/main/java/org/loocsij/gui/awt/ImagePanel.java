/**
 * 
 *//*

package org.loocsij.gui.awt;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import org.loocsij.gui.util.GUIUtil;
import org.loocsij.util.*;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

*/
/**
 * @author wengm
 * 
 *//*

public class ImagePanel extends Panel {
	private static Logger log = Logger.getLogger(ImagePanel.class);
	*
	 * 
	private static final long serialVersionUID = -3672846940140730492L;

	*/
/**
	 * @param args
	 * @throws IOException
	 * @throws ImageFormatException
	 *//*

	public static void main(String[] args) throws ImageFormatException,
			IOException {
	}

	*/
/**
	 * image file path
	 *//*

	private String imagePath;

	private int width;

	private int height;

	private Image img;

	*/
/**
	 * constructor - initialize with image file path
	 * 
	 * @param imagePath
	 * @throws IOException
	 * @throws ImageFormatException
	 *//*

	public ImagePanel(String imagePath) throws ImageFormatException,
			IOException {
		super();
		this.setImagePath(imagePath);
		this.setBackground(GUIUtil.getRandomColor(150, 100));
		if (this.getImagePath() == null) {
			return;
		}
		img = ImageUtil.loadImage(imagePath);

		// //Image's methods
		img.flush();
		MediaTracker tracker = new MediaTracker(this);
		int id = NumberUtil.randomInt();
		tracker.addImage(img, id);
		try {
			tracker.waitForID(id);
		} catch (Exception e) {
			log.error("",e);
			return;
		}
		
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.setSize(width, height);
	}

	public void generateAbbrImage() {
		File f = new File(imagePath);
		String dir = f.getParent();
		String filename = f.getName();
		String extension = FileUtil.getFileExtension(filename);
		String name = filename.substring(0, filename.indexOf(extension));

		int width = this.img.getWidth(null);
		int height = this.img.getHeight(null);
		BufferedImage bi = new BufferedImage(width / 3, height / 3,
				BufferedImage.TYPE_INT_RGB);
		bi.getGraphics().drawImage(img, 0, 0, width / 3, height / 3,
				null);
		FileOutputStream out;
		try {
			out = new FileOutputStream(dir
					+ System.getProperty("file.separator")
					+ name
					+ java.util.Calendar.getInstance().get(
							java.util.Calendar.DATE) + extension);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			try {
				encoder.encode(bi);
			} catch (ImageFormatException e) {
				log.error("",e);
			} catch (IOException e) {
				log.error("",e);
			}
			// System.out.print(width+"*"+height);
			try {
				out.close();
			} catch (IOException e) {
				log.error("",e);
			}
		} catch (FileNotFoundException e) {
			log.error("",e);
		}
	}

	*/
/**
	 * @return the imagePath
	 *//*

	public String getImagePath() {
		return imagePath;
	}

	public Image getImg() {
		return img;
	}

	*/
/**
	 * paint - draw image on this panel
	 *//*

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 0, 0, this);
	}

	*/
/**
	 * @param imagePath
	 *            the imagePath to set
	 *//*

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}
*/
