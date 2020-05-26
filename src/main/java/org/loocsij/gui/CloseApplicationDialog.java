/**
 * 
 */
package org.loocsij.gui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Button;

/**
 * @author wengm
 * 
 */
public class CloseApplicationDialog extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CloseApplicationDialog(Frame frame) {
		super(frame);
	}

	public CloseApplicationDialog(Frame frame, String title, String text,
			int width, int height) {
		super(frame);
		this.setTitle(title);
		Label label = new Label(text);
		this.add(label);
		this.setSize(width, height);
		Button ok = new Button("Ok");
		this.add(ok);
		Button cancel = new Button("cancel");
		this.add(cancel);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
