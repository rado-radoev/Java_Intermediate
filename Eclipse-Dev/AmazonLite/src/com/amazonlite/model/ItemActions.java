package com.amazonlite.model;

public interface ItemActions {

	public boolean updateItem(Item item);
	public boolean addItem(Item item);
	public Item searchItem(Item item);
}
