package org.loocsij.img;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
	public static final int MAX_COLOR_VALUE = 255;
	private static Random r = new Random(new Date().getTime());
	
	/**
	 * ��ȡ�漴ɫ���漴ɫ�����ɻ��ƣ�������ɫ��������ɫ����������ɡ���ɫ������ù��췽����Color(int r,int g,int b)
	 * @param base ȷ�������ɫ�Ļ�����Χ��
	 *  
	 * @return Color �漴ɫ
	 */
	public static Color getRandomColor(int base,int range) {
		if(range > MAX_COLOR_VALUE){
			range = MAX_COLOR_VALUE;
		}
		int red = base + r.nextInt(range);
		int green = base + r.nextInt(range);
		int blue = base + r.nextInt(range);
		return new Color(red, green, blue);
	}

	/**
	 * ��ָ��ͼ�������Ļ����л����漴�ߡ�����ߵ����ɻ��ƣ��ߵ������յ��������
	 * 
	 * @param graph graphics context
	 * @param width width of the range of the graphics context
	 * @param height height of the range of the graphics context
	 */
	public static void drawRandomLine(Graphics graph, int width, int height) {
		int sx = r.nextInt(width);
		int ex = r.nextInt(width);
		int sy = r.nextInt(height);
		int ey = r.nextInt(height);
		graph.drawLine(sx, sy, ex, ey);
	}
}
