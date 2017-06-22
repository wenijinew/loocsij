package org.loocsij.img.tool;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.loocsij.gui.util.GUIUtil;

public class CheckCodeImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String name;

	private int width;

	private int height;

	private int length;

	private int interlinenum;

	private int maxfontsize;

	private int minfontsize;

	private String randomCode;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		name = config.getInitParameter("name");
		width = Integer.parseInt(config.getInitParameter("width"));
		height = Integer.parseInt(config.getInitParameter("height"));
		length = Integer.parseInt(config.getInitParameter("length"));
		maxfontsize = Integer.parseInt(config.getInitParameter("maxfontsize"));
		minfontsize = Integer.parseInt(config.getInitParameter("minfontsize"));
		interlinenum = Integer
				.parseInt(config.getInitParameter("interlinenum"));
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("image/jpeg");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "No-cache");
		res.setDateHeader("Expires", 0L);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics graph = image.getGraphics();
		graph.setColor(GUIUtil.getRandomColor(200, 55));
		graph.fillRect(0, 0, width, height);
		graph.setColor(GUIUtil.getRandomColor(100, 27));
		for (int i = 0; i < interlinenum; i++) {
			GUIUtil.drawRandomLine(graph, width, height);
		}
		graph.setColor(GUIUtil.getRandomColor(60, 3));
		drawRandomCode(graph, width, height, length);
		HttpSession session = req.getSession();
		session.setAttribute(name, this.randomCode);
		graph.dispose();
		try {
			ImageIO.write(image, "JPEG", res.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		doGet(req, res);
	}

	private void drawRandomCode(Graphics graph, int width, int height,
			int length) {
		StringBuffer sb = new StringBuffer();
		String eachCode = null;
		int fontsize = minfontsize;
		int y = 0;
		Random r = new Random(new Date().getTime());
		for (int index = 0; index < length; index++) {
			eachCode = String.valueOf(r.nextInt(10));
			sb.append(eachCode);
			fontsize = minfontsize + r.nextInt(maxfontsize - minfontsize);
			y = height / 2 + r.nextInt(height / 2);
			graph.setFont(new Font("Times New Roman", Font.PLAIN, fontsize));
			graph.drawString(eachCode,
					(int) (index * ((double) width / (double) length)), y);
		}
		this.randomCode = sb.toString();
	}

	public static String getName() {
		return name;
	}
}
