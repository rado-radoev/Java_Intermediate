package com.amazonlite.model;

import java.util.Date;

public abstract class Item {
	
	private String title;
	private String author;
	private String length;
	private Date released;
	private ItemType itemType;
	
	
	// TO DO: Create a builder
	// Follow this: https://codereview.stackexchange.com/a/127509
	
	
	
	public Item(String title, String author, String length, Date released, ItemType itemType) {
		this.title = title;
		this.author = author;
		this.length = length;
		this.released = released;
		this.itemType = itemType;
	}

}
