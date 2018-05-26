package com.amazonlite.model;

public class CD extends InventoryItem {
	
	private String hitSingle;
	private static int instance;

	public CD(String hitSingle) {
		this.hitSingle = hitSingle;
		instance++;
	}
	
	public CD() {
		super();
		instance++;
	}

	public int getInstance() {
		return instance;
	}
	
	public String getHitSingle() {
		return hitSingle;
	}
	
	public void removeInstance() {
		instance--;
	}

	public void setHitSingle(String hitSingle) {
		this.hitSingle = hitSingle;
	}
	
	public static String getSpecialField() {
		return "Hit Single";
	}
	
	public static String getSpecialFieldTrimmed() {
		return getSpecialField().replaceAll("\\s+", "").toLowerCase();
	}

	@Override
	public String toString() {
		return String.format("Title: %s, Author: %s, Length: %.1f, Hit Single: %s ,Release Date: %tD", 
				getTitle(),
				getAuthor(),
				getLength(),
				getHitSingle(),
				getReleaseDate());
	}
}
