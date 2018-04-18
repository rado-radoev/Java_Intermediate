package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.model.Model;

public class Controller  {
	
	private View view;
	private ItemType itemType;
	private Item item;
	private Model model;
	
	public Controller(View view) {
		this.view = view;
	}
	
	public void setSelectedItem(int selectedItem) {
		view.setItemType(selectedItem);
	}
	
	public void startMenu() {
		view.start();
	}
	
	public void displayMenu() {
		view.displayAddMenu();
	}
	
	public void displayActionMenu() {
		view.displayActionMenu();
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
		default:
			break;
		}
	}
	
	public void addItem(Item item) {
		
	}
	
	public void updateItem(Item item) {
		
	}
	
	public void searchItem(Item item) {
		
	}
	
	public void displayInventory(ItemType itemType) {
		
	}
}
