package com.amazonlite.model;

public class Book extends InventoryItem {
	
	private String publisher;
	private static int instance;

	public Book() {
		instance++;
	}
	
	public Book(String publisher) {
		this.publisher = publisher;
		instance++;
	}
	
	public int getInstance() {
		return instance;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return String.format("Title: %s, Author: %s, Length: %.0f, Publisher: %s ,Release Date: %tD", 
				getTitle(),
				getAuthor(),
				getLength(),
				getPublisher(),
				getReleaseDate());
	}

}
