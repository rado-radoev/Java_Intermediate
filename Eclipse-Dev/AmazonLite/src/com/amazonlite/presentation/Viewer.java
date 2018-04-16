package com.amazonlite.presentation;

import java.awt.ItemSelectable;
import java.util.Scanner;

import com.amazonlite.model.ItemType;

public class Viewer {

	public static void main(String[] args) {
		start();
	}
	
	private static void start() {
		System.out.println("Select an item type");
		displayInitialMenu();
		int selected = 0;
		Scanner input = new Scanner(System.in);
		
		while (selected <= 0 || selected > ItemType.values().length) {
			System.out.print("Please select an item from the list: ");
			selected = input.nextInt();
		}
		
		System.out.println(itemSelected(Integer.valueOf(selected - 1)));
	}
	
	private static ItemType itemSelected(int selectedMenu) {
		return ItemType.values()[selectedMenu];
	}
	

	private static void displayInitialMenu() {
		int counter = 1;
		for (ItemType it : ItemType.values()) {
			System.out.println(String.format("%d. %s", counter, it));
			counter++;
		}
		System.out.println();
	}
}
