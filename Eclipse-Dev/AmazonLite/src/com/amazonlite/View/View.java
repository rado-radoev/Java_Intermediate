package com.amazonlite.View;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.amazonlite.model.ItemType;
import com.sun.corba.se.impl.ior.GenericTaggedComponent;
import com.amazonlite.model.Item;
import com.amazonlite.Controller.Controller;

public class View {
	
	private static Controller controller = new Controller(new View());
	private static ItemType itemType;
	
	public static void main(String[] args) {
		controller.startMenu();
	}
	
	public void start() {
		System.out.println("Select an item type");
		displayInitialMenu();
		int selected = 0;
		Scanner input = new Scanner(System.in);
		
		while (selected <= 0 || selected > ItemType.values().length) {
			System.out.print("Please select an item from the list: ");
			selected = input.nextInt();
			controller.displayActionMenu();
		}
		
		controller.setSelectedItem(setItemType(Integer.valueOf(selected - 1)));
	}
	
	public void displayAddMenu() {
		int selected = 0;
		Item item = null;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Add Item details: ");
		
		System.out.println("Add Title: ");
		String title = input.nextLine();
		
		System.out.println("Add Author: ");
		String author = input.nextLine();
		
		System.out.println("Add Release Date (m/d/y): ");
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
				.get
				.get();
		
		System.out.println(item.toString());
	}
	
	
	
	public int displayUpdateMenu() {
		System.out.println("1. update menu");
		return 0;
	}

	
	public void displaySearchMenu() {
		int selected = 0;
		Scanner input = new Scanner(System.in);
		
		while (selected <= 0 || selected > 2) {
			System.out.println("1. Search by Author");
			System.out.println("2. Search by Title");
			System.out.println("2. Search by Release Date");
			System.out.println("2. Search by Length");
			selected = input.nextInt();
		}
	}
	
	public void displayActionMenu() {
		int selected = 0;
		Scanner input = new Scanner(System.in);
		
		while (selected <= 0 || selected > 3) {
			System.out.println("1. Add");
			System.out.println("2. Update");
			System.out.println("3. Search");
			selected = input.nextInt();
		}
		
		System.out.println("selected: " + selected);
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
