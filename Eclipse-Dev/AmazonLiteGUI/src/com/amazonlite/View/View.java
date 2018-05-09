package com.amazonlite.View;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;
import com.amazonlite.model.Model;
import com.amazonlite.interfaces.Observer;

public class View extends JFrame implements Observer {

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
	
	private Model model;
	private Controller controller;
	private ItemType itemType;
	private InventoryItem item;
	
	public View() {
		
		super("AmazonLite");

		layout = new BorderLayout();
		
		mainJPanel = new JPanel(layout);
		
		// JRadioButton actionListener
		itemTypeRadioBtnListener rbal = new itemTypeRadioBtnListener();
		
		// Setup the item type radio buttons
		cdRadioBtn = new JRadioButton("CD");
		cdRadioBtn.addActionListener(rbal);
		dvdRadioBtn = new JRadioButton("DVD");
		dvdRadioBtn.addActionListener(rbal);
		bookRadioBtn = new JRadioButton("Book");
		bookRadioBtn.addActionListener(rbal);
		
		// Create a button group and add all buttons
		itemTypeBtnGroup = new ButtonGroup();
		itemTypeBtnGroup.add(cdRadioBtn);
		itemTypeBtnGroup.add(dvdRadioBtn);
		itemTypeBtnGroup.add(bookRadioBtn);
		
		// set default selection
		cdRadioBtn.setSelected(false);
		
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
		
		// Actions Radio Button ActionListener
		ActionsRadioBtnListener arbl = new ActionsRadioBtnListener();
		
		// Setup action radio buttons
		addRadioBtn = new JRadioButton("Add item");
		addRadioBtn.addActionListener(arbl);
		updateRadioBtn = new JRadioButton("Update item");
		updateRadioBtn.addActionListener(arbl);
		searchRadioBtn = new JRadioButton("Search item");
		searchRadioBtn.addActionListener(arbl);
		displayRadioBtn = new JRadioButton("Display item(s)");
		displayRadioBtn.addActionListener(arbl);
		
		// Create action group and add all actions
		actionBtnGroup = new ButtonGroup();
		actionBtnGroup.add(addRadioBtn);
		actionBtnGroup.add(updateRadioBtn);
		actionBtnGroup.add(searchRadioBtn);
		actionBtnGroup.add(displayRadioBtn);
		
		// default selection
		addRadioBtn.setSelected(true);
		
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
		componentsEnable(false, getComponents(actionsJPanel));
		
		mainJPanel.add(actionsJPanel, layout.CENTER);
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainJPanel);
	}
	
	/* Getters and Setters */
	public Model getModel() { 
		return model;
	}
	
	public final void setModel(Model model) {
		this.model = model;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public final void setController(Controller controller) {
		this.controller = controller;
	}
	
	public InventoryItem getItem() {
		return item;
	}
	
	public void setInventoryItem(InventoryItem item) {
		this.item = item;
	}

	public void setItemType(int selectedMenu) {
		itemType = ItemType.values()[selectedMenu];
	}
	
	public ItemType getItemType() {
 		return itemType;
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
	
	/**
	 * Method to enable or disable components in a container
	 * @param enabled boolean parameter to enable (true) or disable (false) the components
	 * @param components the Components array to loop through and disable
	 */
	private void componentsEnable(boolean enabled, Component[] components) {
		for (Component component: components) {
			component.setEnabled(enabled);
		}
	}
	
	/**
	 * Inner class for Items Radio Btn ActionListener
	 */
	private class itemTypeRadioBtnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Enumeration<AbstractButton> buttons = itemTypeBtnGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				
				if (button.isSelected()) {
					componentsEnable(true, getComponents(actionsJPanel));
				}
			}
		}
	} // End of JButton ActionListener
	
	/**
	 * Inner class for Actions Radio Btn ActionListener
	 */
	private class ActionsRadioBtnListener implements ActionListener {
		@Override
	 	public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(addRadioBtn)) {
				View.this.dispose();
				new AddGUI();
			}
			else if (e.getSource().equals(updateRadioBtn)) {
				View.this.dispose();
				new UpdateGUI().setSize(350, 300);
			}
			else if (e.getSource().equals(searchRadioBtn)) {
				View.this.dispose();
				new SearchGUI();
			}
			else if (e.getSource().equals(displayRadioBtn)) {
				View.this.dispose();
				new DisplayGUI();
			}
		}
	}  // End of Actions ActionListener

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
	}
}
