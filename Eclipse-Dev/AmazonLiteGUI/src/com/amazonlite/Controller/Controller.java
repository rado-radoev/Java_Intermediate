package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.model.Model;
import com.amazonlite.props.Props;
import com.amazonlite.model.InventoryItem;

import java.util.ArrayList;
import java.util.Properties;

public class Controller implements Actionable  {
	
	private View view;
	private ItemType itemType;
	private Item item;
	private Model model;
	private Props props;
	
	public Controller() { }
	
	public Controller(View view) {
		this.view = view;
	}
	
	public View getView() {
		return view;
	}
	
	public void setView(View view2) {
		this.view = view2;
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void addObserver() {
		model.addObserver(view);
	}
	
	public void createNewInventoryItem(ItemType itemType) {
		model.createNewInventoryItem(itemType);
	}
	
	public void setSelectedItem(int selectedItem) {
		view.setItemType(selectedItem);
	}
	
	public void initDefault() {
		model.initializeDefaultProperties();
	}
	

	@Override
	public boolean addItem(InventoryItem item) {
		return model.addItem(item);
	}
	
	public void updateItem(String propertyToModify, String attributeToModify, String oldValueToUpdate, String newValueToUpdate) {
		model.updateRecord(propertyToModify, attributeToModify, oldValueToUpdate, newValueToUpdate);
	}
	
	public String findRecordById(ArrayList<String> records, String recordId) {
		return model.findRecordById(records, recordId);
	}
	
	
	public void displayInventory(ItemType item) {
		model.displayRecords(model.loadRecords(item));
	}

	@Override
	public void displayRecords(Properties prop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRecord(String recrodID, String attributeToModify, String newValueToUpdate) {
		model.updateRecord(recrodID, attributeToModify, newValueToUpdate);
		
	}

	@Override
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch, ItemType itemType) {
		return model.findRecord(propertyToFind, valueToSearch, view.getItemType());
	}
}
