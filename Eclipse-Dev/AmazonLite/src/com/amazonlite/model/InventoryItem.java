package com.amazonlite.model;

import java.util.Date;

public class InventoryItem {

	private String title;
	private String author;
	private double length;
	private Date releaseDate;
	private ItemType itemType;
	
	public InventoryItem(String title, String author, double length, Date releaseDate, ItemType itemType) {
		super();
		this.title = title;
		this.author = author;
		this.length = length;
		this.releaseDate = releaseDate;
		this.itemType = itemType;
	}
	
	public InventoryItem() { }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return String.format("Title: %s,Author: %s,Length: %.1f,Release Date: %tD,Item Type: %s", 
				getTitle(), getAuthor(), getLength(), getReleaseDate(), getItemType());
	}
}
