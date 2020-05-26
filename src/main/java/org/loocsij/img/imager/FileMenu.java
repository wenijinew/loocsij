package org.loocsij.img.imager;

import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FileMenu extends Menu {
	private static final long serialVersionUID = 258142405746424311L;

	private OpenMenuItem open;
	
	public FileMenu() throws HeadlessException {
		super("File",false);
		this.setShortcut(new MenuShortcut(KeyEvent.VK_F));
		addOpenMenuItem();
	}
	
	public void addActionListener(ActionListener actionListener){
		this.open.addActionListener(actionListener);
	}
	
	private void addOpenMenuItem(){
		open = new OpenMenuItem();
		this.add(open);
	}
}
