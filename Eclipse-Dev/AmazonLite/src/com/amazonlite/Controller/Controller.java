package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.model.Model;
import com.amazonlite.props.Props;
import com.amazonlite.model.InventoryItem;

import java.util.ArrayList;
import java.util.Observer;

public class Controller  {
	
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
	
	public void setView(View view) {
		this.view = view;
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
	
	public void startMenu() {
		model.initializeDefault();
		view.start();
	}
	
	public void displayMenu() {
		view.displayAddMenu();
	}
	
	public void displayActionMenu() {
		view.displayActionMenu();
	}
	
	public void closeApp() {
		view.closeApp();
	}

	public void selectActionMenu(int menuIndex) {
		switch (menuIndex) {
		case 1:
			view.displayAddMenu();
			break;
		case 2:
			view.displayUpdateMenu();
			break;
		case 3:
			view.displaySearchMenu();
			break;
		case 4:
			view.displayInventory();
			break;
		default:
			break;
		}
	}
	
	public void addItem(InventoryItem item) {
		model.addItem(item);
	}
	
	public void updateItem(String recrdIdToMofiy, String attributeToModify, String oldValueToUpdate, String newValueToUpdate) {
		model.updateRecord(recrdIdToMofiy, attributeToModify, oldValueToUpdate, newValueToUpdate);
	}
	
	public ArrayList<String> searchItem(String attributeToFind, String valueToSearch, ItemType itemType) {
		return model.findRecord(attributeToFind, valueToSearch, view.getItemType());
	}
	
	public void displayInventory(InventoryItem item) {
		model.displayProperties(model.loadProperties(item));
	}
}
