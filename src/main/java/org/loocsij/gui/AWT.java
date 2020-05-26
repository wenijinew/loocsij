/**
 * 
 */
package org.loocsij.gui;

import java.applet.Applet;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;

/**
 * @author wengm
 * 
 */
public class AWT extends Applet implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Button buy, sell;

	private Label title, emailLabel, commentsLabel,

	makeLabel, colourLabel, optionsLabel, yearLabel;

	private Choice make, year;

	private Checkbox seatbelt, radio, roof, doors;

	private CheckboxGroup colours;

	private Checkbox red, blue, green, black;

	private Panel colourPanel, optionsPanel, buttonPanel,

	commentsPanel, emailPanel, makePanel, imagePanel, yearPanel;

	private TextArea comments, order;

	private TextField email;

	/**
	 * @throws HeadlessException
	 */
	public AWT() throws HeadlessException {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		String orderStr = new String();
		Checkbox selectedColour = colours.getSelectedCheckbox();

	      String colourStr = selectedColour.getLabel();

	      String makeStr = make.getSelectedItem();

	      String yearString = year.getSelectedItem();
	      orderStr += "You ";

	      

	      if(action.equals("Buy")){

	          orderStr += "have looking for ";

	      }

	 

	      if(action.equals("Sell")){

	          orderStr += "are selling ";

	      }

	 

	      orderStr += "a " + colourStr.toLowerCase() + " " + yearString

	          + " " + " " + makeStr + " with ";
	      if(seatbelt.getState()) orderStr += "seatbelts, ";

	      if(radio.getState()) orderStr += "a radio, ";

	      if(roof.getState()) orderStr += "a roof, ";

	      if(doors.getState()) orderStr += "doors. ";
	      if(!(comments.getText()).equals("")){

	          orderStr += "You further commented that \"" + comments.getText()

	            + "\".";         

	      }

	 

	      if(!(email.getText()).equals("")){

	          orderStr += "You can be reached at " + email.getText();

	      }
	      
	      order.setText(orderStr);
	}

	public void init() {
		setBackground(Color.orange);
		setLayout(new GridLayout(10, 0));
		setSize(400, 800);
		
		/* Fonts */

		Font titleFont = new Font("TimesRoman", Font.BOLD, 36);

		Font dataFont = new Font("TimesRoman", Font.ITALIC, 18);

//		Font orderFont = new Font("TimesRoman", Font.PLAIN, 24);
		/* Label */

		title = new Label("John's Car Lot", Label.CENTER);

		title.setFont(titleFont);

		add(title);
		/* Image */

		ImagePanel image = new ImagePanel("car.gif");

		imagePanel = new Panel();

		imagePanel.add(image);

		add(imagePanel);
		/* Choice (Combo Boxes) */

		make = new Choice();

		make.add("Ford");

		make.add("Toyota");

		make.add("Honda");

		make.add("Saturn");

		make.add("Mazda");

		make.add("Nissan");

		makeLabel = new Label("Make: ");

		makeLabel.setFont(dataFont);

		makePanel = new Panel();

		makePanel.add(makeLabel);

		makePanel.add(make);

		add(makePanel);

		year = new Choice();

		year.add("1999");

		year.add("2000");

		year.add("2001");

		year.add("2002");

		year.add("2003");

		yearLabel = new Label("Year: ");

		yearPanel = new Panel();

		yearPanel.add(yearLabel);

		yearPanel.add(year);

		add(yearPanel);
		/* radiobuttons */

		colours = new CheckboxGroup();

		red = new Checkbox("Red", colours, true);

		blue = new Checkbox("Blue", colours, false);

		green = new Checkbox("Green", false, colours);

		black = new Checkbox("Black", false, colours);

		colourLabel = new Label("Colour: ");

		colourLabel.setFont(dataFont);

		colourPanel = new Panel();

		colourPanel.add(colourLabel);

		colourPanel.add(red);

		colourPanel.add(blue);

		colourPanel.add(green);

		colourPanel.add(black);

		add(colourPanel);
		/* Check Boxes */

		seatbelt = new Checkbox("Seatbelt", true);

		radio = new Checkbox("Radio", false);

		doors = new Checkbox("Doors", false);

		roof = new Checkbox("Roof", true);

		optionsLabel = new Label("Options: ");

		optionsLabel.setFont(dataFont);

		optionsPanel = new Panel();

		optionsPanel.add(optionsLabel);

		optionsPanel.add(seatbelt);

		optionsPanel.add(radio);

		optionsPanel.add(roof);

		optionsPanel.add(doors);

		add(optionsPanel);
		/* Text Boxes and Fields */

		comments = new TextArea("", 1, 40, TextArea.SCROLLBARS_NONE);

		commentsLabel = new Label("Comments: ");

		commentsLabel.setFont(dataFont);

		commentsPanel = new Panel();

		commentsPanel.add(commentsLabel);

		commentsPanel.add(comments);

		add(commentsPanel);

		email = new TextField(40);

		emailLabel = new Label("Email address: ");

		emailLabel.setFont(dataFont);

		emailPanel = new Panel();

		emailPanel.add(emailLabel);

		emailPanel.add(email);

		add(emailPanel);

		order = new TextArea("", 3, 40, TextArea.SCROLLBARS_NONE);

		order.setFont(dataFont);

		order.setEditable(false);

		add(order);
		/* Buttons */

		buy = new Button("Buy");

		sell = new Button("Sell");

		buy.addActionListener(this);

		sell.addActionListener(this);

		buttonPanel = new Panel();

		buttonPanel.add(buy);

		buttonPanel.add(sell);

		add(buttonPanel);
	}

	class ImagePanel extends Canvas {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Image i;

		public ImagePanel(String file) {

			i = getImage(getCodeBase(), file);

			MediaTracker m = new MediaTracker(this);

			m.addImage(i, 0);

			try {

				m.waitForID(0);

			} catch (Exception e) {
			}
			;

			setSize(i.getWidth(null), i.getHeight(null));

		}

		public void paint(Graphics g) {

			g.drawImage(i, 0, 0, this);

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
