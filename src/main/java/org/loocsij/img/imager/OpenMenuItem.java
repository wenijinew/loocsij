package org.loocsij.img.imager;

import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

public class OpenMenuItem extends MenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpenMenuItem() throws HeadlessException {
		super("Open",new MenuShortcut(KeyEvent.VK_O));
	}
	
	
}
