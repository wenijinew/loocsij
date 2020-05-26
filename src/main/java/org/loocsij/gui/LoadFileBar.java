/**
 * 
 */
package org.loocsij.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import org.loocsij.util.FileUtil;

/**
 * @author wengm
 * 
 */
public class LoadFileBar extends Panel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3488226973728115409L;

	private FileDialog fd = null;

	private Frame window = null;

	private Button openButton = null;

	private TextField tf = null;

	private String filePath = null;

	private TextArea textArea = null;
	
	/**
	 * create new load file bar
	 */
	public LoadFileBar(Frame window, TextArea textArea) {
		super();
		this.window = window;
		this.textArea = textArea;
		this.tf = new TextField(20);
		this.add(tf);
		this.openButton = new Button("open...");
		this.openButton.addActionListener(this);
		this.add(openButton);
	}

	public void actionPerformed(ActionEvent ae) {
		if(this.filePath==null) {
			setFilePath();
			loadFile();
		}else {
			loadFile();
		}
	}
	
	private void setFilePath() {
		fd = new FileDialog(this.window, "Open File", FileDialog.LOAD);
		fd.setFile(".java");
		fd.setDirectory("");
		fd.setLocation(50, 50);
		fd.setVisible(true);
		if (fd.getFile() != null) {
			File f = new File(fd.getDirectory(), fd.getFile());
			this.filePath = f.getAbsolutePath();
			tf.setText(this.filePath);
		}
	}
	
	private void loadFile() {
		this.textArea.setText(FileUtil.getContent(this.filePath));
		this.filePath = null;
		tf.setText("");
	}
	
	public void alertToSelectFile() {
		Dialog d = new Dialog(this.window);
		d.setLayout(new BorderLayout());
		d.add(new Label("Please select one file!"));
		Button b1 = new Button("Ok");
		d.add(b1,BorderLayout.SOUTH);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dialog d = (Dialog)arg0.getSource();
				d.dispose();
			}
		});
	}

	public FileDialog getFileDialog() {
		return fd;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public FileDialog getFd() {
		return fd;
	}

	public void setFd(FileDialog fd) {
		this.fd = fd;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Button getOpenButton() {
		return openButton;
	}

	public void setOpenButton(Button openBt) {
		this.openButton = openBt;
	}

	public TextField getTf() {
		return tf;
	}

	public void setTf(TextField tf) {
		this.tf = tf;
	}

	public Frame getWindow() {
		return window;
	}

	public void setWindow(Frame window) {
		this.window = window;
	}

}
