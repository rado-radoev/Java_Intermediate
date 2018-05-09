package com.amazonlite.interfaces;

import java.util.ArrayList;
import java.util.Properties;

import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public interface Actionable2 {

	public void updateRecord(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate);
	public void addItem(InventoryItem item);
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch ,ItemType itemType);
	public void displayRecords(Properties prop);
}
