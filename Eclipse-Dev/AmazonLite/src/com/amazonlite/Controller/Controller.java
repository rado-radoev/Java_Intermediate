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
	
	public ItemType getSelectedItemType(ItemType selectedItemType) {
		return selectedItemType;
	}
	
	public void setSelectedItem(ItemType itemType) {
		this.itemType = itemType;
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
}
