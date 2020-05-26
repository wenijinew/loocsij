/**
 *
 */
package org.loocsij.gui.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;
import org.loocsij.util.FileUtil;

/**
 * provide utility methods for GUI programming
 *
 * @author wengm
 *
 */
public class GUIUtil extends Component {
    private static Logger log = Log.getLogger(GUIUtil.class);

    /**
     *
     */
    private static final long serialVersionUID = 5273891434326119558L;

    /**
     * max alpha value of Color
     */
    private static final int MAX_COLOR_VALUE = 255;

    /**
     * random instance
     */
    private static Random random = new Random(new Date().getTime());

    /**
     * location types of component
     */
    public static final int TOP = 1;

    public static final int TOPLEFT = 2;

    public static final int TOPRIGHT = 3;

    public static final int BOTTOM = 4;

    public static final int BOTTOMLEFT = 5;

    public static final int BOTTOMRIGHT = 6;

    public static final int LEFT = 7;

    public static final int RIGHT = 8;

    public static final int CENTER = 9;

    /**
     * private instance
     */
    private static GUIUtil gu;

    /**
     * utilities objects
     */
    private static Toolkit toolkit;

    private static int screenWidth;

    private static int screenHeight;

    private static boolean isInitial = false;

    /**
     * constructor
     *
     */
    private GUIUtil() {
        super();
    }

    private static GUIUtil getInstance() {
        if (gu == null) {
            gu = new GUIUtil();
        }
        return gu;
    }

    static {
        if (!isInitial) {
            GUIUtil gu = getInstance();
            toolkit = gu.getToolkit();
            screenWidth = getScreenWidth();
            screenHeight = getScreenHeight();
            isInitial = true;
        }
    }

    /**
     * supported image file's extensions
     */
    public static final String[] extensions = { ".jpg", ".gif", ".png" };

    /**
     * judge if the given image file is supported by jdk
     *
     * @param fileName
     * @return - boolean, false, if not supported;else, true
     */
    public static boolean isSupportedImage(String fileName) {
        String extension = FileUtil.getFileExtension(fileName);
        for (int i = 0; i < extensions.length; i++) {
            if (extensions[i].equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * generate Color instance with random color
     *
     * @param base
     * @param range
     * @return
     */
    public static Color getRandomColor(int base, int range) {
        if (range > MAX_COLOR_VALUE) {
            range = MAX_COLOR_VALUE;
        }
        int red = base + random.nextInt(range);
        int green = base + random.nextInt(range);
        int blue = base + random.nextInt(range);
        return new Color(red, green, blue);
    }

    public static Color getRandomColor() {
        return getRandomColor(0, 255);
    }

    public static Color getRandomDarkColor() {
        return getRandomColor(35, 50);
    }

    public static Color getRandomLightColor() {
        return getRandomColor(205, 50);
    }

    /**
     * draw line on given graphics with random length
     *
     * @param graph
     * @param width
     * @param height
     */
    public static void drawRandomLine(Graphics graph, int width, int height) {
        int sx = random.nextInt(width);
        int ex = random.nextInt(width);
        int sy = random.nextInt(height);
        int ey = random.nextInt(height);
        graph.drawLine(sx, sy, ex, ey);
    }

    public static int getScreenWidth() {
        return getScreenSize().width;
    }

    public static int getScreenHeight() {
        return getScreenSize().height;
    }

    public static Dimension getScreenSize() {
        return toolkit.getScreenSize();
    }

    public static void setLocation(Container window) {
        setLocation(window, CENTER);
    }

    public static void setSize(Container window){
        window.setSize(getScreenWidth(), getScreenHeight());
    }

    public static void setSize(Container window, float bit){
        window.setSize((int)(getScreenWidth()*bit), (int)(getScreenHeight()*bit));
    }

    /**
     * set location of given window
     *
     * @param topWindow
     * @param locationType
     */
    public static void setLocation(Container topWindow, int locationType) {
        Point p = null;
        int sw = screenWidth;
        int sh = screenHeight;
        int ww = topWindow.getWidth();
        int wh = topWindow.getHeight();
        switch (locationType) {
        case TOP:
            p = new Point((sw - ww) / 2, 0);
            break;
        case TOPLEFT:
            p = new Point(0, 0);
            break;
        case TOPRIGHT:
            p = new Point(sw, 0);
            break;
        case BOTTOM:
            p = new Point((sw - ww) / 2, sh - wh);
            break;
        case BOTTOMLEFT:
            p = new Point(0, sh - wh);
            break;
        case BOTTOMRIGHT:
            p = new Point(sw - ww, sh - wh);
            break;
        case LEFT:
            p = new Point(0, (sh - wh) / 2);
            break;
        case RIGHT:
            p = new Point(sw - ww, sh - wh);
            break;
        case CENTER:
            p = new Point((sw - ww) / 2, (sh - wh) / 2);
            break;
        default:
            p = new Point((sw - ww) / 2, (sh - wh) / 2);
        }
        topWindow.setLocation(p);
    }

    /**
     * load image by given image file path
     *
     * @param imageFilePath
     * @return
     */
    public static Image loadImage(String imageFilePath) {
        Image image = new Component() {
            private static final long serialVersionUID = -1086242631525898677L;
        }.getToolkit().getImage(imageFilePath);
        MediaTracker mt = new MediaTracker(null);
        mt.addImage(image, 0);
        try {
            mt.waitForID(0);
        } catch (InterruptedException ie) {
            return null;
        }
        if (mt.isErrorID(0))
            return null;
        return image;
    }

    /**
     * load BufferedImage by given image file path
     *
     * @param imageFilePath
     * @return
     */
    public static BufferedImage loadBufferedImage(String imageFilePath) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File(imageFilePath));
            MediaTracker mt = new MediaTracker(null);
            mt.addImage(bimg, 0);
            try {
                mt.waitForID(0);
            } catch (InterruptedException ie) {
                return null;
            }
            if (mt.isErrorID(0))
                return null;
        } catch (Exception e) {
            log.error("Can not read image file:" + imageFilePath, e);
        }
        return bimg;
    }

    /**
     * draw given image with default graphics object onto given container object
     *
     * @param container
     * @param image
     */
    public static void drawImage(Container container, Image image) {
        new Component() {
            private static final long serialVersionUID = 0L;
        }.getGraphics().drawImage(image, 0, 0, container);
    }

    /**
     * draw given image with given graphics object onto given container object
     *
     * @param graphics
     * @param container
     * @param image
     */
    public static void drawImage(Graphics graphics, Container container,
            Image image) {
        graphics.drawImage(image, 0, 0, container);
    }

    /**
     *
     * @param source
     * @param iTargetWidth
     * @param iTargetHeight
     */
    //public static void generateAbbrImage(String source, String target,
    //        int iTargetWidth, int iTargetHeight) {
    //    Image image = loadImage(source);

    //    BufferedImage bi = new BufferedImage(iTargetWidth, iTargetHeight,
    //            BufferedImage.TYPE_INT_RGB);
    //    bi.getGraphics().drawImage(image, 0, 0, bi.getWidth(), bi.getHeight(),
    //            null);
    //    try {
    //        FileOutputStream out = new FileOutputStream(target);
    //        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    //        encoder.encode(bi);
    //        out.close();
    //    } catch (Exception e) {
    //        log.error("Error in generateAbbrImage(...)", e);
    //    }
    //}

    /**
     * capture screen.
     * @return BufferedImage
     * @author wengm
     * @since 2008-12-8
     */
    public static BufferedImage captureScreen(){
        Dimension dim = GUIUtil.getScreenSize();
        Rectangle screenRect = new Rectangle(dim);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            log.error("Fail to create robot:",e);
            return null;
        }
        return robot.createScreenCapture(screenRect);
    }
}
