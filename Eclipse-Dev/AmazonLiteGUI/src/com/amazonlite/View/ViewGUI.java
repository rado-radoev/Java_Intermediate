package com.amazonlite.View;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.GroupLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewGUI {

	private final JRadioButton cdRadioBtn;
	private final JRadioButton dvdRadioBtn;
	private final JRadioButton bookRadioBtn;
	private final ButtonGroup itemTypeBtnGroup;
	private final JPanel itemTypeJPanel;
	
	
	private final JRadioButton addRadioBtn;
	private final JRadioButton updateRadioBtn;
	private final JRadioButton searchRadioBtn;
	private final JRadioButton displayRadioBtn;
	private final JPanel actionsJPanel;
	
	private final JPanel mainJPanel;
	
	private GroupLayout groupLayout;
	
	
	
	public ViewGUI() {
		
		// Setup the radio buttons
		cdRadioBtn = new JRadioButton("CD");
		dvdRadioBtn = new JRadioButton("DVD");
		bookRadioBtn = new JRadioButton("Book");
		
		// Create a button group and add all buttons
		itemTypeBtnGroup = new ButtonGroup();
		itemTypeBtnGroup.add(cdRadioBtn);
		itemTypeBtnGroup.add(dvdRadioBtn);
		itemTypeBtnGroup.add(bookRadioBtn);
		
		// add all items to JPannel
		itemTypeJPanel = new JPanel();
		itemTypeJPanel.add(cdRadioBtn);
		itemTypeJPanel.add(dvdRadioBtn);
		itemTypeJPanel.add(bookRadioBtn);
		
		// setting the JPanel layout
		groupLayout = new GroupLayout(itemTypeJPanel);
		itemTypeJPanel.setLayout(groupLayout);
		
		// automatic gap insertion
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		
		groupLayout.setHorizontalGroup(
				groupLayout.createSequentialGroup()
				.addComponent(cdRadioBtn)
				.addComponent(dvdRadioBtn)
				.addComponent(bookRadioBtn));
		
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addComponent(cdRadioBtn)
				.addComponent(dvdRadioBtn)
				.addComponent(bookRadioBtn));
		
	}
 	
	
}
