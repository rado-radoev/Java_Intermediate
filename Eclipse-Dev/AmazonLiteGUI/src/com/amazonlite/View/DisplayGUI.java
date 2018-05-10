package com.amazonlite.View;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class DisplayGUI extends JPanel {

	private final JTextArea textArea;
	private final JScrollPane scrollPane;
	
	public DisplayGUI() {
	
		textArea = new JTextArea(5, 20);
		scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(true);
		setLayout(new BorderLayout());
		
		add(scrollPane);
	}
	
	public void setTextArea(ArrayList<String> text) {
		for (String string : text) {
			textArea.setText(textArea.getText() + string + "\n");
		}
	}

}
