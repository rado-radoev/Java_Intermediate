package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.model.Model_OLD;
import com.amazonlite.test.Props;
import com.amazonlite.model.InventoryItem;

import java.util.ArrayList;
import java.util.Properties;

public class Controller implements Actionable  {
	
	private View view;
	private ItemType itemType;
	private Item item;
	private Model_OLD model_OLD;
	private Props props;
	
	// Default constructor
	public Controller() { }
	
	// Constructor accepting a View object
	public Controller(View view) {
		this.view = view;
	}
	
	// Getters and Setters
	
	public View getView() {
		return view;
	}
	
	public void setView(View view2) {
		this.view = view2;
	}
	
	public Model_OLD getModel() {
		return model_OLD;
	}

	public void setModel(Model_OLD model_OLD) {
		this.model_OLD = model_OLD;
	}
	
	public void setSelectedItem(int selectedItem) {
		view.setItemType(selectedItem);
	}

	/** 
	 * Add View as on observer to a Model_OLD 
	 */
	public void addObserver() {
		model_OLD.addObserver(view);
	}
	
	/**
	 * Method to create new inventory object
	 */
	public void createNewInventoryItem(ItemType itemType) {
		model_OLD.createNewInventoryItem(itemType);
	}
	
	/**
	 * Create new inventory file if none is present
	 * Launches only the very first time the app is run
	 */
	public void initDefault() {
		model_OLD.initializeDefaultProperties();
	}
	

	/**
	 * Method to add item
	 * @param item item to add
	 * @return boolean if item addition is successful or not
	 */
	@Override
	public boolean addItem(InventoryItem item) {
		return model_OLD.addItem(item);
	}
	
	/**
	 * Method to read properties, passed as argument and notify observers for each K,V pair as a String
	 * @param prop the properties to iterate through
	 */
	@Override
	public ArrayList<String> displayRecords(String inventoryItem, String searchString) {
		return model.displayRecords(inventoryItem, searchString);
	}

	/**
	 * Method to update record
	 * @param recordID the id of the record to update. Represented as a String
	 * @param attributeToModify the name of the to modify. Represented as a String
	 * @param newValueToUpdate the new value to be added updated
	 * @return boolean true or false if update is successful or not
	 */
	@Override
	public boolean updateRecord(String recrodID, String attributeToModify, String newValueToUpdate) {
		return model_OLD.updateRecord(recrodID, attributeToModify, newValueToUpdate);
		
	}

	/**
	 * Method to find an item record
	 * @param propertyToFind Name of property to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList<Strings> representing every property that matches the search
	 */
	@Override
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch, ItemType itemType) {
		return model_OLD.findRecord(propertyToFind, valueToSearch, view.getItemType());
	}
	
	/**
	 * Method to update property value. Method is <b>deprecated</b> but is left for backward compatibility
	 * @param propertyToModify Id of the property to modify
	 * @param attributeToModify Name of attribute to modify
	 * @param oldValueToUpdate old value to be modified
	 * @param newValueToUpdate new value to replace old value
	 */
	@Deprecated
	public void updateItem(String propertyToModify, String attributeToModify, String oldValueToUpdate, String newValueToUpdate) {
		model_OLD.updateRecord(propertyToModify, attributeToModify, oldValueToUpdate, newValueToUpdate);
	}
	
	/**
	 * Method to find record by provided ID
	 * @param records an ArrayList<String> of records
	 * @param recordId the record ID to search for
	 * @return String representation of the record that matches the search or null if 
	 * no matches found
	 */
	public String findRecordById(ArrayList<String> records, String recordId) {
		return model_OLD.findRecordById(records, recordId);
	}
	
	/**
	 * Method to read properties, passed as argument and notify observers for each K,V pair as a String
	 * @param item the type of inventory to iterate through
	 */
	public void displayInventory(ItemType item) {
		model_OLD.displayRecords(model_OLD.loadRecords(item));
	}
}
