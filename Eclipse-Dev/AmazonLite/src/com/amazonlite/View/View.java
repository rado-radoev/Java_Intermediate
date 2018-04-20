package com.amazonlite.View;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.amazonlite.model.ItemType;
import com.amazonlite.model.Item;
import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Model;

public class View implements Observer {
	
	private static Controller controller = new Controller(new View());
	private static ItemType itemType;
	private Scanner input;
	private Model model = new Model();
	
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
		ArrayList<String> itemToUpdate = displaySearchMenu();

		input = new Scanner(System.in);
		
		System.out.printf("%s: %n", "Property to modify");
		String propertyToUpdate = input.nextLine();
		
		System.out.printf("%s: %n", "Old value to update");
		String oldValueToUpdate = input.nextLine();
		
		System.out.printf("%s: %n", "New value to update");
		String newValueToUpdate = input.nextLine();
		
		for (String str : itemToUpdate) {
			System.out.println(str);
			controller.updateItem(str, propertyToUpdate, oldValueToUpdate, newValueToUpdate);
		}
		
	}
	

	public void displayInventory() {
		controller.displayInventory(getItemType());
	}
	
	
	public ArrayList<String> displaySearchMenu() {
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

		
		ArrayList<String> res = controller.searchItem(propertyToSearch, valueToSearch, getItemType());
		for (String str : res) {
			System.out.println(str);
		}
		
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
