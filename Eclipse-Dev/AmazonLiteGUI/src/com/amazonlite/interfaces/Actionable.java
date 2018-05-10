package com.amazonlite.interfaces;

import java.util.ArrayList;
import java.util.Properties;

import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public interface Actionable {
	
	public void createNewInventoryItem(ItemType itemType);
	public void displayRecords(Properties prop);
	public boolean addItem(InventoryItem item);
	public void updateRecord(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate);
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch ,ItemType itemType);
}
