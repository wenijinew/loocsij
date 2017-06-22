/**
 * 
 */
package org.loocsij.gui;

import java.awt.*;
import java.awt.event.*;

/**
 * @author wengm
 *
 */
public class SearchToolBar extends Panel implements ActionListener  {
	private TextField filePathField =null;
	private Button searchButton = null;
	
	private String filePath = null;
	private String fileExtension = null;
	private String target = null;
	private int matchType = -1;
	private String[] results = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2168815789381272646L;

	/**
	 * 
	 */
	public SearchToolBar() {
		super();
		this.filePathField = new TextField(20);
		this.add(filePathField);
		this.searchButton = new Button("Search");
		this.searchButton.addActionListener(this);
		this.add(searchButton);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	public void actionPerformed(ActionEvent e) {
		
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public TextField getFilePathField() {
		return filePathField;
	}

	public void setFilePathField(TextField filePathField) {
		this.filePathField = filePathField;
	}

	public String[] getResults() {
		return results;
	}

}
