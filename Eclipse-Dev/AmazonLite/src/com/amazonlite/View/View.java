package com.amazonlite.View;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.amazonlite.model.ItemType;
import com.amazonlite.model.Item;
import com.amazonlite.Controller.Controller;

public class View {
	
	private static Controller controller = new Controller(new View());
	private static ItemType itemType;
	private Scanner input;
	
	public static void main(String[] args) {
		controller.startMenu();
	}
	
	/**
	 * Start method. Entry point for the application.
	 */
	public void start() {
		System.out.println("Select an item type");
		displayInitialMenu();
		int selected = 0;
		input = new Scanner(System.in);
		
		while (selected <= 0 || selected > ItemType.values().length) {
			System.out.print("Please select an item from the list: ");
			selected = input.nextInt();
		}
		
		controller.setSelectedItem(Integer.valueOf(selected - 1));
		controller.displayActionMenu();
		input.close();
	}
	
	/**
	 * 
	 */
	public void displayAddMenu() {
		Item item = null;
		input = new Scanner(System.in);
		
		System.out.println("Add Title: ");
		String title = input.nextLine();
		
		System.out.println("Add Author: ");
		String author = input.nextLine();
		
		System.out.println("Add Release Date (mm/dd/yyyy): ");
		String dateInput = input.nextLine();
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(dateInput);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("Add Length: ");
		double length = input.nextDouble();
		input.close();
		
		item = Item.Builder
				.build()
				.title(title)
				.author(author)
				.releaseDate(date)
				.lenght(length)
				.itemType(getItemType())
				.get();
	
		controller.addItem(item);
	}
	
	
	
	public void displayUpdateMenu() {
		
		System.out.println("Find item to update");
		String itemToUpdate = displaySearchMenu();

		Scanner in = new Scanner(System.in);
		
		System.out.printf("%s: %n", "Property to modify");
		String propertyToUpdate = "Author" /*in.nextLine();*/;
		
		System.out.printf("%s: %n", "Old value to update");
		String oldValueToUpdate = "Bon Jovi" /*in.nextLine();*/;
		
		System.out.printf("%s: %n", "New value to update");
		String newValueToUpdate = "Paul McCarthy"/*in.nextLine();*/;
		
		
		controller.updateItem(itemToUpdate, propertyToUpdate, oldValueToUpdate, newValueToUpdate);
	}
	

	public void displayInventory() {
		controller.displayInventory(getItemType());
	}
	
	
	public String displaySearchMenu() {
		int selected = 0;
		String[] searchMenus = {"Author", "Title", "Release Date", "Length"};
		input = new Scanner(System.in);
		
		while (selected <= 0 || selected > searchMenus.length) {
			for (int i = 0; i < searchMenus.length; i++) {
				System.out.printf("%d. Search by: %s%n", i + 1, searchMenus[i]);
			}
			selected = input.nextInt();		
		}
		
		input.nextLine();
		
		String propertyToSearch = searchMenus[--selected];
		
		System.out.printf("%s: ", propertyToSearch);
		String valueToSearch = input.nextLine();
		
		input.close();
		
		String res = controller.searchItem(propertyToSearch, valueToSearch, getItemType());
		System.out.println(res);
		return res;
	}
	
	public void displayActionMenu() {
		int selected = 0;
		String[] actionMenus = {"Add", "Update", "Search", "Display"};
		input = new Scanner(System.in);
		
		while (selected <= 0 || selected > actionMenus.length) {
			for (int i = 0; i < actionMenus.length; i++) {
				System.out.printf("%d. %s%n", i + 1, actionMenus[i]);
			}
			selected = input.nextInt();
		}
		
		controller.selectActionMenu(selected);
		input.close();
	}
	
	public void setItemType(int selectedMenu) {
		itemType = ItemType.values()[selectedMenu];
	}
	
	public ItemType getItemType() {
 		return itemType;
	}

	public void displayInitialMenu() {
		int counter = 1;
		for (ItemType it : ItemType.values()) {
			System.out.println(String.format("%d. %s", counter, it));
			counter++;
		}
		System.out.println();
	}
}
