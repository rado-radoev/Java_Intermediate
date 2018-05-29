package com.amazonlite.View;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class DisplayGUI_OLD extends JPanel {

	private final JTextArea textArea;
	private final JScrollPane scrollPane;
	
	public DisplayGUI_OLD() {
	
		// Empty non-editable text area
		textArea = new JTextArea(5, 20);
		scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		setLayout(new BorderLayout());
		
		add(scrollPane);
	}
	
	/**
	 * Setter method to display text in the text area
	 * @param text the String to be displayed in the text area
	 */
	public void setTextArea(String text) {
		textArea.setText(text);
	}
	
	/**
	 * Setter method to display ArrayList<String> elements in the text area
	 * @param text the ArrayList<String> to be displayed in the text area
	 */
	public void setTextArea(ArrayList<String> text) {
		for (String string : text) {
			textArea.setText(textArea.getText() + string + "\n");
		}
	}

}
