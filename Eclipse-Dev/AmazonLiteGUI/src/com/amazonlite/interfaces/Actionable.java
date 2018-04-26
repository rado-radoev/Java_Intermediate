package com.amazonlite.interfaces;

import java.util.ArrayList;
import java.util.Properties;

import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public interface Actionable {

	public void updateProperty(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate);
	public void addItem(InventoryItem item);
	public ArrayList<String> findProperty(String propertyToFind, String valueToSearch ,ItemType itemType);
	public void displayProperties(Properties prop);
}
