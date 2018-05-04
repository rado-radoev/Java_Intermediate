package com.amazonlite.View;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import oracle.jrockit.jfr.JFR;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewGUI extends JFrame {

	private final JRadioButton cdRadioBtn;
	private final JRadioButton dvdRadioBtn;
	private final JRadioButton bookRadioBtn;
	private final ButtonGroup itemTypeBtnGroup;
	private final JPanel itemTypeJPanel;
	
	private final JRadioButton addRadioBtn;
	private final JRadioButton updateRadioBtn;
	private final JRadioButton searchRadioBtn;
	private final JRadioButton displayRadioBtn;
	private final ButtonGroup actionBtnGroup;
	private final JPanel actionsJPanel;
	
	private final JPanel mainJPanel;
	
	private GroupLayout groupLayout;
	
	public ViewGUI() {
	
		mainJPanel = new JPanel();
		
		// Setup the item type radio buttons
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
		
		mainJPanel.add(itemTypeJPanel);
		

		// Setup action radio buttons
		addRadioBtn = new JRadioButton("Add item");
		updateRadioBtn = new JRadioButton("Update item");
		searchRadioBtn = new JRadioButton("Search item");
		displayRadioBtn = new JRadioButton("Display item(s)");
		
		// Create action group and add all actions
		actionBtnGroup = new ButtonGroup();
		actionBtnGroup.add(addRadioBtn);
		actionBtnGroup.add(updateRadioBtn);
		actionBtnGroup.add(searchRadioBtn);
		actionBtnGroup.add(displayRadioBtn);
		
		// Create Actions JPanel
		actionsJPanel = new JPanel();
		
		actionsJPanel.add(addRadioBtn);
		actionsJPanel.add(updateRadioBtn);
		actionsJPanel.add(searchRadioBtn);
		actionsJPanel.add(displayRadioBtn);
		
		mainJPanel.add(actionsJPanel);
		
		
		// setting the JPanel layout
		groupLayout = new GroupLayout(mainJPanel);
		mainJPanel.setLayout(groupLayout);
		
		// automatic gap insertion
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		
		groupLayout.setHorizontalGroup(
			groupLayout.createSequentialGroup()
			.addGroup(createParallelGroup(GroupLayout.Alignment.LEADING))
				.addComponent(itemTypeJPanel)
				.addComponent(actionsJPanel));
		
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
				.addGroup(createParallelGroup(GroupLayout.Alignment.BASELINE))
					.addComponent(itemTypeJPanel)
					.addComponent(actionsJPanel));
				
		add(mainJPanel);
		
	}
 	
	private Group createParallelGroup(Alignment leading) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		ViewGUI vg = new ViewGUI();
		vg.setVisible(true);
		vg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vg.setSize(200,300);
	}
	
}
