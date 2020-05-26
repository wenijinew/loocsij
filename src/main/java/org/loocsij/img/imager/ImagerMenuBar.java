package org.loocsij.img.imager;

import java.awt.HeadlessException;
import java.awt.MenuBar;
import java.awt.event.ActionListener;

public class ImagerMenuBar extends MenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2101663831807220476L;

	private FileMenu file;
	
	public ImagerMenuBar() throws HeadlessException {
		addFileMenu();
	}

	private void addFileMenu(){
		file = new FileMenu();
		this.add(file);
	}
	
	public void addActionListener(ActionListener actionListener){
		this.file.addActionListener(actionListener);
	}
}
