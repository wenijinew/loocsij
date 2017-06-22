package org.loocsij.img.imager;

import java.awt.BorderLayout;
import java.awt.Panel;

import org.loocsij.gui.util.GUIUtil;

public class ToolbarPanel extends Panel {
	private static final long serialVersionUID = -2417463663623071152L;

	public ToolbarPanel() {
		super(new BorderLayout());
		this.setSize(GUIUtil.getScreenWidth(), 30);
	}
}
