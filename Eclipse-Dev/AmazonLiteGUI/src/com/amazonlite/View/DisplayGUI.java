package com.amazonlite.View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class DisplayGUI extends JPanel {

	private final JTextArea textArea;
	private final BorderLayout layout;
	private final JScrollPane scrollPane;
	private final JPanel panel;
	
	public DisplayGUI() {
	
		panel = new JPanel();
		layout = new BorderLayout();
		textArea = new JTextArea(5, 20);
		scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(true);
		panel.setLayout(layout);
		panel.add(scrollPane, layout.CENTER);
		
		add(panel);
	}

}
