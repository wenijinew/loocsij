/**
 * 
 */
package org.loocsij.gui.awt;

import java.awt.Font;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.loocsij.gui.util.GUIUtil;

/**
 * @author wengm
 * 
 */
public class ListPanel extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2162701054807030552L;

	protected static final int itemHeight = 18;

	private List list;

	private int itemNumber;

	private String[] items;

	/**
	 * @return the itemNumber
	 */
	public int getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber
	 *            the itemNumber to set
	 */
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the items
	 */
	public String[] getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(String[] items) {
		this.items = items;
	}

	/**
	 * @return the list
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * 
	 */
	public ListPanel(int itemNumber, String[] items, boolean multipleMode) {
		super();
		boolean bInvalidArguments = itemNumber <= 0 || items == null
				|| items.length == 0;
		this.itemNumber = itemNumber;
		this.items = items;
		if (!bInvalidArguments) {
			list = new List(itemNumber, multipleMode);
			for (int i = 0; i < items.length; i++) {
				list.add(items[i]);
			}
			this.add(list);
		}
	}

	/**
	 * 
	 */
	public ListPanel(List list, int rows) {
		super();
		if (list != null) {
			this.setList(list);
			this.add(list);
		} else {
			List listTemp = new List(rows);
			this.setList(listTemp);
			this.add(listTemp);
		}
	}

	/**
	 * @param layout
	 */
	public ListPanel(List list, LayoutManager layout) {
		super(layout);
		this.add(list);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.changeBackgournd();
	}
}

class Demo {
	Window w = new Window();

	ListPanel lp;

	int itemCount;

	/**
	 * color example
	 */
	Color[] colors;

	String[] strColors;

	Label labelDes;
	Label labelColor;

	public Demo() {

	}

	public void changeBackgournd() {
		//list items
		itemCount = w.getHeight() / 18;
		colors = new Color[itemCount];
		strColors = new String[itemCount];
		for (int i = 0; i < itemCount; i++) {
			colors[i] = GUIUtil.getRandomColor();
		}
		for (int i = 0; i < itemCount; i++) {
			strColors[i] = "Color" + i;
		}
		
		//list panel
		lp = new ListPanel(itemCount, strColors, false);
		class ItemListenerImpl implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				Color color = colors[lp.getList().getSelectedIndex()];
				w.setBackground(color);
				lp.setBackground(color);
				labelColor.setText(lp.getList().getSelectedItem());
				labelColor.setBackground(color);
				labelColor.getParent().setBackground(color);
			}
		}
		lp.getList().addItemListener(new ItemListenerImpl());
		
		//label des
		labelDes = new Label(
				"Change window's background by change selected item of the left list.");
		labelDes.setBackground(Color.black);
		labelDes.setForeground(Color.white);
		//label color
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.setFont(new Font(null,Font.BOLD,60));
		labelColor = new Label("						");
		p.add(labelColor,BorderLayout.CENTER);
		
		//layout set
		w.setBackground(GUIUtil.getRandomColor());
		w.add(lp, BorderLayout.WEST);
		w.add(p,BorderLayout.CENTER);
		w.add(labelDes,BorderLayout.NORTH);
		w.setResizable(false);
		
		//set location
		GUIUtil.setLocation(w, GUIUtil.CENTER);
		w.setVisible(true);
	}
}
