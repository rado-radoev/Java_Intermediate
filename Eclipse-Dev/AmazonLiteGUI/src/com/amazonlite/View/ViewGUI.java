package com.amazonlite.View;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import oracle.jrockit.jfr.JFR;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;

import javafx.scene.layout.Border;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	private BorderLayout layout;
	
	public ViewGUI() {
	
		setTitle("Inventory");
		layout = new BorderLayout();
		
		mainJPanel = new JPanel(layout);
		
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
		
		// Set up items JPanel layout
		itemTypeJPanel.setLayout(new BoxLayout(itemTypeJPanel, BoxLayout.PAGE_AXIS));
		itemTypeJPanel.add(Box.createVerticalGlue());
		itemTypeJPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		// Set title and border on the items JPanel
		TitledBorder itemsTitle;
		itemsTitle = BorderFactory.createTitledBorder("Items");
		itemsTitle.setTitleJustification(TitledBorder.CENTER);
		itemTypeJPanel.setBorder(itemsTitle);
		
		// add the item Jpanel to the main JPanel		
		mainJPanel.add(itemTypeJPanel, layout.PAGE_START);
		
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

		// Set actions JPanel layout
		actionsJPanel.setLayout(new BoxLayout(actionsJPanel, BoxLayout.PAGE_AXIS));
		actionsJPanel.add(Box.createVerticalGlue());
		
		// Set title and border on the actions JPanel
		TitledBorder actionsTitle;
		actionsTitle = BorderFactory.createTitledBorder("Actions");
		actionsTitle.setTitleJustification(TitledBorder.CENTER);
		actionsJPanel.setBorder(actionsTitle);
		
		// Disable all actions until an Item has been selected
		actionsJPanel.setEnabled(false);
		for (Component component : getComponents(actionsJPanel))
			component.setEnabled(false);
		
		mainJPanel.add(actionsJPanel, layout.CENTER);
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainJPanel);
	}
	
	/**
	 * Method that all components in a specific container
	 * @param container container to check for and return components
	 * @return Component(s) contained in the container
	 */
	private Component[]	getComponents(Component container) {
		ArrayList<Component> list = null;
		
		// Get every component in the container and add it to the array
		try {
			list = new ArrayList<Component>(Arrays.asList(
					((Container)container).getComponents()));
			for (int index = 0; index < list.size(); index++) {
				for (Component currentComponent : getComponents(list.get(index))) {
					list.add(currentComponent);
				}
			}
		} catch (ClassCastException e) {
			list = new ArrayList<Component>();
		}
		
		return list.toArray(new Component[list.size()]);
	}
 	
	public static void main(String[] args) {
		ViewGUI vg = new ViewGUI();
		vg.setVisible(true);
		vg.setSize(200,300);
	}
	
}
