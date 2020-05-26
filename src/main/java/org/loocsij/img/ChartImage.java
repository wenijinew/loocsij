/*
 * @#ChartImage.java 
 */
package org.loocsij.img;

import java.io.*;
import java.awt.image.*;
import java.awt.*;

/**
 *  
 * @author wengm
 * @version 1.0.1
 */
public class ChartImage {

    /**
     * Do nothing
     */
    public ChartImage() {
        // TODO Auto-generated constructor stub
    }

    public static void createImage(BufferedImage image,String fileLocation) {
    //    try {
    //        FileOutputStream fos = new FileOutputStream(fileLocation);
    //        BufferedOutputStream bos = new BufferedOutputStream(fos);
    //        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
    //        encoder.encode(image);
    //        bos.close();
    //    } catch (Exception e) {
    //        System.out.println(e);
    //    }
    }

    public static void graphicsGeneration(int h1, int h2, int h3, int h4, int h5) {

        final int X = 10;
        int imageWidth = 300; 
        int imageHeight = 300; 
        int columnWidth = 30; 
        int columnHeight = 200; 

        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.red);
        graphics.drawRect(X + 1 * columnWidth, columnHeight - h1, columnWidth,
                h1);
        graphics.drawRect(X + 2 * columnWidth, columnHeight - h2, columnWidth,
                h2);
        graphics.drawRect(X + 3 * columnWidth, columnHeight - h3, columnWidth,
                h3);
        graphics.drawRect(X + 4 * columnWidth, columnHeight - h4, columnWidth,
                h4);
        graphics.drawRect(X + 5 * columnWidth, columnHeight - h5, columnWidth,
                h5);
        ChartImage.createImage(image, "c:\\chart.jpg");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        graphicsGeneration(123,34,45,56,6767);
    }

}
