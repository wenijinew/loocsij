/**
 * 
 */
package org.loocsij.img.imager;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import org.loocsij.gui.util.GUIUtil;
import org.loocsij.img.ImagePanel;

/**
 * @author wengm
 * 
 */
public class Imager extends Frame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7050931594582160280L;

	private static final String title = "Imager";

	// private BufferedImage bimage;

	private ImagerMenuBar menubar;

	private ImagePanel imagePanel;

	private ToolbarPanel toolbarPanel;

	private Label labelDes;

	/**
	 * 
	 */
	public Imager() {
		this.initial();
		this.display();
	}

	private void initial() {
		this.setTitle(title);
		this.setBackground(GUIUtil.getRandomColor());
		
		this.setMenuBar();
		this.setImagePanel();
		this.setToolbarPanel();

		// Terminate the application if the user closes the window.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}

	private void setMenuBar() {
		menubar = new ImagerMenuBar();
		menubar.addActionListener(this);
		this.setMenuBar(menubar);
	}

	public void setImagePanel() {
		imagePanel = new ImagePanel();
		imagePanel.setBackground(GUIUtil.getRandomColor());
		this.add(imagePanel, BorderLayout.CENTER);
	}

	public void setToolbarPanel() {
		toolbarPanel = new ToolbarPanel();
		labelDes = new Label();
		toolbarPanel.add(labelDes);
		toolbarPanel.setBackground(GUIUtil.getRandomColor());
		this.add(toolbarPanel, BorderLayout.SOUTH);
	}

	private void display() {
		GUIUtil.setSize(this, 0.3f);
		GUIUtil.setLocation(this);
		this.setVisible(true);
	}

	public void repaint() {
		super.repaint();
		repaintImage();
	}
	
	public void update(Graphics g){
		super.update(g);
		repaintImage();
	}

	private void repaintImage() {
		repaintImage(null);
	}

	int i=0;
	private void repaintImage(BufferedImage bimage) {
		i++;
		System.out.println(i);
		if (bimage != null) {
			this.imagePanel.setBufferedImage(bimage);
		}
		if (this.imagePanel.getBufferedImage() == null) {
			return;
		}
		int iwidth = this.imagePanel.getBufferedImage().getWidth();
		int iheight = this.imagePanel.getBufferedImage().getHeight();
		int properImageWindth = this.getWidth() > iwidth ? iwidth : this
				.getWidth();
		double bit = (properImageWindth + 0.0) / (iwidth + 0.0);
		int properImageHeight = (int) ((iheight * bit) - this.toolbarPanel.getPreferredSize().getHeight());
		int x = (this.getWidth() - properImageWindth) / 2;
		int y = (this.getHeight() - this.toolbarPanel.getHeight() - properImageHeight) / 2;
		this.imagePanel.setX(x);
		this.imagePanel.setY(y);
		this.imagePanel.setAreaSize(properImageWindth, properImageHeight);
		this.imagePanel.repaint();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Imager();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof OpenMenuItem) {
			performOpen();
		}
	}

	private void performOpen() {
		FileDialog fd = new FileDialog(this);
		fd.setVisible(true);
		if (fd.getFile() == null)
			return;
		String path = fd.getDirectory() + fd.getFile();
		labelDes.setText(path);
		BufferedImage bimage = GUIUtil.loadBufferedImage(path);
		repaintImage(bimage);
	}
}
