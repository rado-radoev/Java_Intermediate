package com.amazonlite.View;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;

public class AddGUI extends JFrame {
	
	private final JPanel mainJPanel;
	private final GroupLayout layout;
	
	private final JLabel titleLabel;
	private final JLabel authorLabel;
	private final JLabel releaseDateLabel;
	private final JLabel lengthLabel;
	private final JLabel specialFieldLabel;
	
	private final JTextField titleTextField;
	private final JTextField authorTextField;
	private final JTextField releaseDateTextField;
	private final JTextField lengthTextField;
	private final JTextField specialFieldTextField;
	
	private String title;
	private String author;
	private Date releaseDate;
	private double Length;
	private String specialField;
	
	public AddGUI() {
		setTitle("Add item");
		
		mainJPanel = new JPanel();
		layout = new GroupLayout(mainJPanel);
		mainJPanel.setLayout(layout);
	
		titleLabel = new JLabel("Title");
		authorLabel = new JLabel("Author");
		releaseDateLabel = new JLabel("Release Date");
		lengthLabel = new JLabel("Length");
		specialFieldLabel = new JLabel("Special Field");
		
		titleTextField = new JTextField(20);
		authorTextField = new JTextField(20);
		releaseDateTextField = new JTextField(20);
		lengthTextField = new JTextField(20);
		specialFieldTextField = new JTextField(20);
		
		
		/*  CREATE GUI   */
		
		// Automatically add gaps between components
		layout.setAutoCreateGaps(true);
		
		// Automatically add gaps between frame edge components
		layout.setAutoCreateContainerGaps(true);
		
		// Sequential group for horizontal axis
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(titleLabel)
				.addComponent(authorLabel)
				.addComponent(releaseDateLabel)
				.addComponent(lengthLabel)
				.addComponent(specialFieldLabel));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(titleTextField)
				.addComponent(authorTextField)
				.addComponent(releaseDateTextField)
				.addComponent(lengthTextField)
				.addComponent(specialFieldTextField));
		layout.setHorizontalGroup(hGroup);
		
		// Sequential group for the vertical axis
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(titleLabel)
				.addComponent(titleTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(authorLabel)
				.addComponent(authorTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(releaseDateLabel)
				.addComponent(releaseDateTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(lengthLabel)
				.addComponent(lengthTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(specialFieldLabel)
				.addComponent(specialFieldTextField));
		layout.setVerticalGroup(vGroup);
		
		add(mainJPanel);
		
	}
	
	private class TextFieldsActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == titleTextField) {
				
			}
		}
		
	}
	

	public static void main(String[] args) {
		AddGUI addg = new AddGUI();
		addg.setVisible(true);
		addg.setSize(200,300);

	}

	
}