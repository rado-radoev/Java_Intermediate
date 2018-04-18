package com.amazonlite.model;

import com.amazonlite.props.Props;

public class Model {
	
	private ItemType itemType;
	private Item item;
	private Props props = new Props();
	
	public Model(Item item) {
		this.item = item;
		this.itemType = item.getItemType();
	}

	public Model() { }
	
	public void addItem( Item item ) {
		props.addItem(item);
	}
}
