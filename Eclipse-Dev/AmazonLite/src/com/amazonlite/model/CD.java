package com.amazonlite.model;

public class CD extends InventoryItem {
	
	private String hitSingle;

	public CD(String hitSingle) {
		this.hitSingle = hitSingle;
	}
	
	public CD() { }

	
	public String getHitSingle() {
		return hitSingle;
	}

	public void setHitSingle(String hitSingle) {
		this.hitSingle = hitSingle;
	}

	@Override
	public String toString() {
		return String.format("Title: %s, Author: %s, Length: %1f, Hit Single: %s ,Release Date: %tD", 
				getTitle(),
				getAuthor(),
				getLength(),
				getHitSingle(),
				getReleaseDate());
	}
}
