package com.amazonlite.View;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Book;
import com.amazonlite.model.CD;
import com.amazonlite.model.DVD;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;
import com.amazonlite.model.Model;
import com.amazonlite.interfaces.Observer;

public class View extends JFrame implements Observer {
	
	private final JTabbedPane tabbedPane;

	private final JRadioButton cdRadioBtn;
	private final JRadioButton dvdRadioBtn;
	private final JRadioButton bookRadioBtn;
	private final ButtonGroup itemTypeBtnGroup;
	private final JPanel itemTypeJPanel;
	
	private final JPanel mainJPanel;
	
	private BorderLayout layout;
	
	private String specialFieldLabel;
	
	private Model model;
	private Controller controller;
	private ItemType itemType;
	private InventoryItem item;
	
	private AddGUI addGUI;
	private SearchGUI searchGUI;
	private UpdateGUI updateGUI;
	private DisplayGUI displayGUI;
	
	private static View instance = null;
	
	public static View getInstance() {
		if (instance == null) {
			instance = new View();
		}
		
		return instance;
	}
	
	protected View() {
		
		super("AmazonLite");

		layout = new BorderLayout();
		
		mainJPanel = new JPanel(layout);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			
			private void changeSpecialLabel() {
				if (getItemType().name() == "CD") {
					addGUI.setSpecialFieldLabel("Hit Single");
					searchGUI.setSpecialFieldLabel("Hit Single");
				}
				else if (getItemType().name() == "DVD") { 
					addGUI.setSpecialFieldLabel("Bonus Scenes"); 
					searchGUI.setSpecialFieldLabel("Bonus Scenes");
					}
				else if (getItemType().name() == "Book") { 
					addGUI.setSpecialFieldLabel("Publisher");
					searchGUI.setSpecialFieldLabel("Publisher");
				}
			}
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if (sourceTabbedPane.getTitleAt(index).equals("Add") || 
						sourceTabbedPane.getTitleAt(index).equals("Update") ||
						sourceTabbedPane.getTitleAt(index).equals("Search")) {
					changeSpecialLabel();
				}
			}
		});
		
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
		cdRadioBtn.setSelected(true);
		
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
		
		// Add JPanels as tabs
		addGUI = new AddGUI();
		searchGUI = new SearchGUI();
		updateGUI = new UpdateGUI();
		displayGUI = new DisplayGUI();
				
		tabbedPane.add("Inventory", itemTypeJPanel);
		tabbedPane.add("Add", addGUI);
		tabbedPane.add("Search", searchGUI);
		tabbedPane.add("Update", updateGUI);
		tabbedPane.add("Display", displayGUI);
	
		add(tabbedPane);
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
			if (e.getSource() == cdRadioBtn) {
				// This gets the item type from the enum
				controller.setSelectedItem(ItemType.valueOf(cdRadioBtn.getText()).ordinal());
				specialFieldLabel = CD.getSpecialField();
			}
			else if (e.getSource() == dvdRadioBtn) {
				// This gets the item type from the enum
				controller.setSelectedItem(ItemType.valueOf(dvdRadioBtn.getText()).ordinal());
				specialFieldLabel = DVD.getSpecialField();
			}
			else if (e.getSource() == bookRadioBtn) {
				// This gets the item type from the enum
				controller.setSelectedItem(ItemType.valueOf(bookRadioBtn.getText()).ordinal());
				specialFieldLabel = Book.getSpecialField();
			}
			
			// This creates an object based on the selected enum
			controller.createNewInventoryItem(getItemType());	
 		}
	} // End of JButton ActionListener
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
	}	
	
	public static void main(String[] args) {
		View v = new View();
		v.setVisible(true);
		v.setSize(400,500);
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
