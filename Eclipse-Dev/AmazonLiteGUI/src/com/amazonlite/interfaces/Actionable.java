package com.amazonlite.interfaces;

import java.util.ArrayList;
import java.util.Properties;

import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public interface Actionable {
	
	/**
	 * Method to create new Inventory Item 
	 * @param itemType the type of the inventory item to create object for
	 */
	public void createNewInventoryItem(ItemType itemType);
	
	/**
	 * Method to read properties, passed as argument and notify observers for each K,V pair as a String
	 * @param prop the properties to iterate through
	 */
	public void displayRecords(Properties prop);
	
	/**
	 * Method to add item
	 * @param item item to add
	 * @return boolean if item addition is successful or not
	 */
	public boolean addItem(InventoryItem item);
	
	/**
	 * Method to update record
	 * @param recordID the id of the record to update. Represented as a String
	 * @param attributeToModify the name of the to modify. Represented as a String
	 * @param newValueToUpdate the new value to be added updated
	 * @return boolean true or false if update is successful or not
	 */
	public boolean updateRecord(String recrodID, String attributeToModify, String newValueToUpdate);
	
	/**
	 * Method to find an item record
	 * @param propertyToFind Name of property to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList<Strings> representing every property that matches the search
	 */
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch ,ItemType itemType);
}
