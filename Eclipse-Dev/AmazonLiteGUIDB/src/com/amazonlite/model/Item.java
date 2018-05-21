package com.amazonlite.model;

import java.util.Date;

import com.amazonlite.test.Actionable2;

public class Item {
	
	private String title;
	private String author;
	private double length;
	private Date releaseDate;
	private ItemType itemType;
	
	// Hide c-tor from user
	private Item() { }
	
	public static final class Builder {
		private Item item;
		
		// Initialize state
		private Builder(final Item item) {
			this.item = item;
		}
		
		// Start building an item
		public static Builder build() {
			return new Builder(new Item());
		}
		
		public Builder title(final String title) {
			this.item.title = title;
			return this;
		}
		
		public Builder author(final String author) {
			this.item.author = author;
			return this;
		}
		
		public Builder lenght(final double length) {
			this.item.length = length;
			return this;
		}
		
		public Builder releaseDate(final Date releaseDate) {
			this.item.releaseDate = releaseDate;
			return this;
		}
		
		public Builder itemType(final ItemType itemType) {
			this.item.itemType = itemType;
			return this;
		}
		
		// End construction & return unique item obj
		public Item get() {
			final Item iget = new Item();
			iget.author = item.author;
			iget.title = item.title;
			iget.length = item.length;
			iget.releaseDate = item.releaseDate;
			iget.itemType = item.itemType;
			return iget;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public double getLength() {
		return length;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public ItemType getItemType() {
		return itemType;
	}
	
	@Override
	public String toString() {
		return String.format("Title: %s,Author: %s,Length: %1f,Release Date: %tD,Item Type: %s", 
				getTitle(), getAuthor(), getLength(), getReleaseDate(), getItemType());
	}

}
