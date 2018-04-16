package com.amazonlite.application;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;

public class Controller {
	
	private ItemType itemType;
	private Item item;

	public Controller(ItemType itemType) {
		this.itemType = itemType;
	}
	
	public Controller(Item item) {
		this.item = item;
	}

	
	
}
