package org.loocsij.img.tool;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
public class ChartImage {
    private BufferedImage image;
    public ChartImage() {
    }
    public void createImage(String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void graphicsGeneration(int h1, int h2, int h3, int h4, int h5) {
        final int X = 10;
        int imageWidth = 300;
        int imageHeight = 300;
        int columnWidth = 30;
        int columnHeight = 200;
        ChartImage chartGraphics = new ChartImage();
        chartGraphics.image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = chartGraphics.image.getGraphics();
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
        chartGraphics.createImage("D:\\temp\\chart.jpg");
    }
    public static void main(String[] args) {
        new ChartImage().graphicsGeneration(10, 20, 30, 40, 50);
    }
}
