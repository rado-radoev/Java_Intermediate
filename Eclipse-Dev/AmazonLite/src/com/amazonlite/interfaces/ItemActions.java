package com.amazonlite.interfaces;

import com.amazonlite.model.Item;

public interface ItemActions {

	public boolean updateItem(Item item);
	public boolean addItem(Item item);
	public Item searchItem(Item item);
	public void displayItem(Item item);
}
