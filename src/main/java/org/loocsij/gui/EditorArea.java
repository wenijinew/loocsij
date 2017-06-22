/**
 * 
 */
package org.loocsij.gui;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.Font;
import java.util.Locale;
/**
 * Editor area component.
 * @author wengm
 * @since 2007/12/16
 */
public class EditorArea extends TextArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2189742705548141676L;
	private static int defaultRows = 100;
	private static int defaultColumns = 50;
	private static Color defaultBackgourndColor = Color.white;
	private static Locale defaultLocale = Locale.CHINESE;
	private static Font defaultFont = new Font("Arial",Font.PLAIN,16);
	
	public static Color getDefaultBackgourndColor() {
		return defaultBackgourndColor;
	}

	public static void setDefaultBackgourndColor(Color defaultBackgourndColor) {
		EditorArea.defaultBackgourndColor = defaultBackgourndColor;
	}

	public static int getDefaultColumns() {
		return defaultColumns;
	}

	public static void setDefaultColumns(int defaultColumns) {
		EditorArea.defaultColumns = defaultColumns;
	}

	public static Font getDefaultFont() {
		return defaultFont;
	}

	public static void setDefaultFont(Font defaultFont) {
		EditorArea.defaultFont = defaultFont;
	}

	public static Locale getDefaultLocale() {
		return defaultLocale;
	}

	public static void setDefaultLocale(Locale defaultLocale) {
		EditorArea.defaultLocale = defaultLocale;
	}

	public static int getDefaultRows() {
		return defaultRows;
	}

	public static void setDefaultRows(int defaultRows) {
		EditorArea.defaultRows = defaultRows;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * constructor. in default, 
	 */
	public EditorArea() {
		super();
		this.setBackground(defaultBackgourndColor);
		this.setRows(defaultRows);
		this.setColumns(defaultColumns);
		this.setFont(defaultFont);
		this.setLocale(defaultLocale);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
