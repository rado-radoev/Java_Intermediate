package com.amazonlite.Controller;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;
import com.amazonlite.View.View;
import com.amazonlite.model.Model;
import com.amazonlite.props.Props;

public class Controller  {
	
	private View view;
	private ItemType itemType;
	private Item item;
	private Model model = new Model();
	private Props props;
	
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
		case 4:
			view.displayInventory();
		default:
			break;
		}
	}
	
	public void addItem(Item item) {
		model.addItem(item);
	}
	
	public void updateItem(Item item) {
		
	}
	
	public void searchItem(Item item) {
		
	}
	
	public void displayInventory(ItemType itemType) {
		props = new Props();
		props.displayProperties(props.loadProperties(itemType));
	}
}
