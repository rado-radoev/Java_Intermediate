package com.amazonlite.View;

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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Book;
import com.amazonlite.model.CD;
import com.amazonlite.model.DVD;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;
import com.amazonlite.model.Model;
import com.amazonlite.interfaces.Observer;

public class View extends JFrame {
	
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
	
	// Only one instance of the class is allowed
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
			
			/** 
			* The special label in the Add and Search tabs is displayed differently
			* depending on the selected type of item 
			*/

			private void changeSpecialLabel() {
				if (getItemType().name().toUpperCase() == "CD") {
					addGUI.setSpecialFieldLabel("Hit Single");
					searchGUI.setSpecialFieldLabel("Hit Single");
					updateGUI.addSpecialAttributeToComboBox(CD.getSpecialField().replaceAll("\\s+", ""));
				}
				else if (getItemType().name().toUpperCase() == "DVD") { 
					addGUI.setSpecialFieldLabel("Bonus Scenes"); 
					searchGUI.setSpecialFieldLabel("Bonus Scenes");
					updateGUI.addSpecialAttributeToComboBox(DVD.getSpecialField().replaceAll("\\s+", ""));
					}
				else if (getItemType().name().toUpperCase() == "BOOK") { 
					addGUI.setSpecialFieldLabel("Publisher");
					searchGUI.setSpecialFieldLabel("Publisher");
					updateGUI.addSpecialAttributeToComboBox(Book.getSpecialField().replaceAll("\\s+", ""));
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
		bookRadioBtn = new JRadioButton("BOOK");
		bookRadioBtn.addActionListener(rbal);
		
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
		
		// turn off default selection
		cdRadioBtn.setSelected(false);
		
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
	
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setEnabledAt(i, false);
		}
		
		add(tabbedPane);
	}
	
	/* Getters and Setters */
	
	public DisplayGUI getDisplayGUI() {
		return displayGUI;
	}
 	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
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
				controller.setSelectedItem(ItemType.valueOf(cdRadioBtn.getText().toUpperCase()).ordinal());
				specialFieldLabel = CD.getSpecialField();
			}
			else if (e.getSource() == dvdRadioBtn) {
				// This gets the item type from the enum
				controller.setSelectedItem(ItemType.valueOf(dvdRadioBtn.getText().toUpperCase()).ordinal());
				specialFieldLabel = DVD.getSpecialField();
			}
			else if (e.getSource() == bookRadioBtn) {
				// This gets the item type from the enum
				controller.setSelectedItem(ItemType.valueOf(bookRadioBtn.getText().toUpperCase()).ordinal());
				specialFieldLabel = Book.getSpecialField();
			}
			
			// Enabled tabbed Pane
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				tabbedPane.setEnabledAt(i, true);
			}
			
			// This creates an object based on the selected enum
			controller.createNewInventoryItem(getItemType());	
 		}
	} // End of JButton ActionListener
}
