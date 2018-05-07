package com.amazonlite.View;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

public class UpdateGUI extends template {
	
	private final JPanel mainJPanel;
	private final GroupLayout layout;
	
	private final JLabel propToModifyLabel;
	private final JLabel oldValueLabel;
	private final JLabel newValueLabel;
	private final JLabel titleLabel;
	private final JLabel authorLabel;
	private final JLabel releaseDateLabel;
	private final JLabel lengthLabel;
	private final JLabel specialFieldLabel;

	
	private final JTextField propToModifyTextField;
	private final JTextField oldValueTextField;
	private final JTextField newValueTextField;
	private final JTextField titleTextField;
	private final JTextField authorTextField;
	private final JTextField releaseDateTextField;
	private final JTextField lengthTextField;
	private final JTextField specialFieldTextField;
	
	public UpdateGUI() {
		setTitle("Update GUI");
		
		mainJPanel = new JPanel();
		layout = new GroupLayout(mainJPanel);
		mainJPanel.setLayout(layout);
	
		propToModifyLabel = new JLabel("Property to Modify");
		titleLabel = new JLabel("Title");
		authorLabel = new JLabel("Author");
		releaseDateLabel = new JLabel("Release Date");
		lengthLabel = new JLabel("Length");
		specialFieldLabel = new JLabel("Special Field");
		oldValueLabel = new JLabel("Old value");
		newValueLabel = new JLabel("New value");
		
		propToModifyTextField = new JTextField(20);
		titleTextField = new JTextField(20);
		authorTextField = new JTextField(20);
		releaseDateTextField = new JTextField(20);
		lengthTextField = new JTextField(20);
		specialFieldTextField = new JTextField(20);
		oldValueTextField = new JTextField(20);
		newValueTextField = new JTextField(20);
		
		/*  CREATE GUI   */
		
		// Automatically add gaps between components
		layout.setAutoCreateGaps(true);
		
		// Automatically add gaps between frame edge components
		layout.setAutoCreateContainerGaps(true);
		
		// Sequential group for horizontal axis
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(propToModifyLabel)
				.addComponent(titleLabel)
				.addComponent(authorLabel)
				.addComponent(releaseDateLabel)
				.addComponent(lengthLabel)
				.addComponent(specialFieldLabel)
				.addComponent(oldValueLabel)
				.addComponent(newValueLabel));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(propToModifyTextField)
				.addComponent(titleTextField)
				.addComponent(authorTextField)
				.addComponent(releaseDateTextField)
				.addComponent(lengthTextField)
				.addComponent(specialFieldTextField)
				.addComponent(oldValueTextField)
				.addComponent(newValueTextField));
		layout.setHorizontalGroup(hGroup);
		
		// Sequential group for the vertical axis
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(propToModifyLabel)
				.addComponent(propToModifyTextField));
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
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(oldValueLabel)
				.addComponent(oldValueTextField));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(newValueLabel)
				.addComponent(newValueTextField));
		layout.setVerticalGroup(vGroup);
		
		add(mainJPanel);
	}
	
	
	public static void main(String[] args) {
		UpdateGUI ug = new UpdateGUI();
		ug.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ug.setVisible(true);
		ug.setSize(300, 200);
	}
}
