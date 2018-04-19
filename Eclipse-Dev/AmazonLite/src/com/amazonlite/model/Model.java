package com.amazonlite.model;

import com.amazonlite.props.Props;
import java.util.ArrayList;

public class Model {
	
	private ItemType itemType;
	private Item item;
	private Props props = new Props();
	
	public Model(Item item) {
		this.item = item;
		this.itemType = item.getItemType();
	}

	public Model() { }
	
	public void addItem( Item item ) {
		props.addItem(item);
	}
	
	public void updateProperty(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate) {
		props.updateProperty(propertyToModify, attributeToModify, oldValueToUpdate, newValueToUpdate);
	}
	
	public ArrayList<String> findProperty(String propertyToFind, String valueToSearch ,ItemType itemType) {
		return props.findProperty(propertyToFind, valueToSearch, itemType);
	}
}
