/**
 * 
 */
package org.loocsij.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wengm
 * 
 */
public class MainWindow extends Frame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2111609549606310074L;

	private static CommonWindowListener listener = new CommonWindowListener();

	private String defaultTitle = "New Main Window";

	private int defaultWidth = 600;

	private int defaultHeight = 450;

	private Color defaultColor = new Color(200, 200, 200);

	public static final int BORDER_LAYOUT = 0;

	private LoadFileBar loadFilebar = null;

	private Button loadButton = null;

//	private String filePath = null;
//
//	private String content = null;

	private EditorArea contentShower = null;

	private EditorArea resultShower = null;

	/**
	 * 
	 */
	public MainWindow() {
		super();
		this.addWindowListener(listener);
		this.setTitle(defaultTitle);
		this.setSize(defaultWidth, defaultHeight);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int leftTopX = (dimension.width - this.getWidth()) / 2;
		int leftTopY = (dimension.height - this.getHeight()) / 2;
		this.setLocation(leftTopX, leftTopY);
		this.setBackground(defaultColor);
		// this.setVisible(true);
	}

	public void setLayout(int layoutIndex) {
		switch (layoutIndex) {
		case BORDER_LAYOUT:
			this.setLayout(new BorderLayout());
		}
	}

	public static String loadFile(Frame f, String title, String defDir,
			String fileType) {
		FileDialog fd = new FileDialog(f, title, FileDialog.LOAD);
		fd.setFile(fileType);
		fd.setDirectory(defDir);
		fd.setLocation(50, 50);
		fd.setVisible(true);
		return fd.getFile();
	}

	public void init() {
		this.setResizable(false);

		BorderLayout lm = (BorderLayout) this.getLayout();
		lm.setHgap(1);
		lm.setVgap(1);

		this.contentShower = new EditorArea();
		this.contentShower.setRows(10);
		this.resultShower = new EditorArea();
		this.resultShower.setRows(10);
		Panel p = new Panel();
		this.loadFilebar = new LoadFileBar(this, contentShower);
		p.add(this.loadFilebar);
		this.loadButton = new Button("load");
		this.loadButton.addActionListener(this);
		p.add(this.loadButton);

		this.add(p, BorderLayout.NORTH);
		this.add(contentShower, BorderLayout.CENTER);
		this.add(resultShower, BorderLayout.SOUTH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final MainWindow mw = new MainWindow();
		mw.init();
		mw.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	// public void search() {
	// String[] strs = SearchString.doSearch(args);
	// if(strs!=null && strs.length>0) {
	// for(int i=0;i<strs.length;i++) {
	// ta.append(strs[i]+SystemProperties.sLineSeparator);
	// }
	// }
	// }
}
