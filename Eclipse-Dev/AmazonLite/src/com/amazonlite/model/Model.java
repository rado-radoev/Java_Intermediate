package com.amazonlite.model;

import com.amazonlite.View.View;
import com.amazonlite.props.InitializeProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class Model extends Observable {
	
	private ItemType itemType;
	private Item item;
	private View view;
	private ArrayList<Observer> views = new ArrayList<Observer>();
	
	public Model() { }
	
	public Model(View view) {
		this.view = view;
	}	
	
	public Model(Item item) {
		this.item = item;
		this.itemType = item.getItemType();
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		views.add(o);
	}
	
	/**
	 * Method to create new Inventory Item object
	 * based on the selected enum
	 * @param itemType the enum object selected
	 */
	public void createNewInventoryItem(ItemType itemType) {
		if (itemType.name().equals("CD")) {
			view.setInventoryItem(new CD());
		} else if (itemType.name().equals("DVD")) {
			view.setInventoryItem(new DVD());
		} else {
			view.setInventoryItem(new Book());
		}
	}
	

	/**
	 * Method to read a Properites argument and display all 
	 * properties in the comamnd line
	 * @param prop the properties to display
	 */
	public void displayProperties(Properties prop) {
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.putAll(prop.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		
		Iterator<Entry<String, String>> propMapIterator = propMap.entrySet().iterator();
		while (propMapIterator.hasNext()) {
			Map.Entry<String, String> property = (Map.Entry<String, String>) propMapIterator.next();
			System.out.println(property.getKey() + " = " + property.getValue());
		}
		
		setChanged();
		notifyObservers(true);
	}
	
	/**
	 * Helper method that reads all properties from a properties file
	 * @param fileName the name of the properties file to read
	 * @return Properties object or null if no properties are loaded or file is missing
	 */
	private Properties readProps(String fileName) {
		Properties props = new Properties();
		
		try (InputStream input = new FileInputStream(fileName)) {
			// load properties file
			props.load(input);
			return props;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method that loads properties
	 * @param item InventoryItem object to load properties from file
	 * @return Properties object containing all properties from InventoryItem's properties file
	 */
	public Properties loadProperties(InventoryItem item) {
		String fileName = item.getClass().getCanonicalName().substring(item.getClass().getCanonicalName().lastIndexOf(".") + 1) + ".properties";
		
		return readProps(fileName);
	}
	
	/**
	 * Method that loads properties
	 * @param itemType ItemType Enum to load properties from file
	 * @return Properties object containing all properties from InventoryItem's properties file
	 */
	public Properties loadProperties(ItemType itemType) {
		String fileName = itemType.name() + ".properties";
		
		return readProps(fileName);
	}
	
	/**
	 * Method that saves Properties to file
	 * @param properties Properties object to save
	 * @param itemType Type of item to save properties for
	 * @return true if file is saved successfully and false if file save was unsuccessful
	 */
	private boolean saveProperties(Properties properties, ItemType itemType) {
		String fileName = itemType.name() + ".properties";
		
		try (OutputStream output = new FileOutputStream(fileName)) {
			properties.store(output, null);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Method to add item to properties file
	 * @param item item to add
	 */
	public void addItem(InventoryItem item) {
		Properties property = new Properties();
		property = loadProperties(item);

		property.setProperty(String.valueOf(property.size() + 1), item.toString());
		saveProperties(property, item.getItemType());
		
		setChanged();
		notifyObservers(true);
	}

	/**
	 * Method to update property value
	 * @param propertyToModify Id of the property to modify
	 * @param attributeToModify Name of attribute to modify
	 * @param oldValueToUpdate old value to be modified
	 * @param newValueToUpdate new value to replace old value
	 */
	public void updateProperty(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate) {
		
		ItemType itemType = ItemType.valueOf(propertyToModify.substring(propertyToModify.indexOf("Type: ") + 6));
		String key = propertyToModify.substring(0, propertyToModify.indexOf(" = "));
		String newPropValue1 = propertyToModify.substring(0, propertyToModify.indexOf(attributeToModify) + attributeToModify.length() + 2);
		String newPropValue2 = propertyToModify.substring(newPropValue1.length() + oldValueToUpdate.length());
		String newPropValueFull = newPropValue1 + newValueToUpdate + newPropValue2;
		
		Properties property = new Properties();
		property = loadProperties(itemType);
		
		property.setProperty(key, newPropValueFull.substring(newPropValueFull.indexOf("Title: ")));
		saveProperties(property, itemType);
	}
	
	/**
	 * Method to find a property
	 * @param propertyToFind Name of property to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList of Strings represeinting every property that matches the search
	 */
	public ArrayList<String> findProperty(String propertyToFind, String valueToSearch ,ItemType itemType) {

		Properties property = new Properties();
		property = loadProperties(itemType);

		ArrayList<String> res = new ArrayList<String>();
		
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.putAll(property.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		
		Iterator<Entry<String, String>> propMapIterator = propMap.entrySet().iterator();
		while (propMapIterator.hasNext()) {
			Map.Entry<String, String> prop = (Map.Entry<String, String>) propMapIterator.next();
			String[] splittedValue = prop.getValue().split(",");
			for (String string : splittedValue) {
				if (string.toLowerCase().startsWith(propertyToFind.toLowerCase()) 
						&& string.toLowerCase().contains(valueToSearch.toLowerCase())) {
//					System.out.println(prop.getValue());
					res.add(String.format("%s = %s", prop.getKey(), prop.getValue()));
				}
			}
		}

		if (res.isEmpty()) {
			res.add(String.format("%S", "no match found"));
			//notifyObservers();
		}
		return res;
	}
	
	/**
	 * Method to initialize default properties if none are existing
	 */
	public void initializeDefaultProperties() {
		InitializeProperties.init();
	}
	
	/**
	 * Method to create a properties file with dummy data
	 * @param itemType type of item to generate property for
	 */
	// Replace deprecated Date.parse method
	@SuppressWarnings("deprecation")
	public void createDummyProperties(ItemType itemType) {
		Properties prop = new Properties();
		
		String fileName = itemType.name() + ".properties";
		
		try (OutputStream output = new FileOutputStream(fileName)) {
			if (itemType == ItemType.CD) {
				prop.setProperty("1", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"T.N.T", "AC/DC", 41.55D, Date.parse("12/01/1975"), itemType.name()));
				prop.setProperty("2", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"Let There Be Rock", "AC/DC", 40.19D, Date.parse("03/21/1977"), itemType.name()));
				prop.setProperty("3", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"Black Ice", "AC/DC", 55.38D, Date.parse("10/17/2008"), itemType.name()));
			}
			else if (itemType == ItemType.DVD) {
				prop.setProperty("1", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"The Fellowship of the Ring", "Peter Jackson", 3.28D, Date.parse("12/19/2001"), itemType.name()));
				prop.setProperty("2", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"The Two Towers", "Peter Jackson", 3.43D, Date.parse("12/18/2002"), itemType.name()));
				prop.setProperty("3", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"The Return of the King", "Peter Jackson", 4.12D, Date.parse("12/17/2003"), itemType.name()));
			}
			else if (itemType == ItemType.BOOK) {
				prop.setProperty("1", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"The Philosopher's Stone", "J. K. Rowling", 329D, Date.parse("09/01/1997"), itemType.name()));
				prop.setProperty("2", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"The Chamber of Secrets", "J. K. Rowling", 341D, Date.parse("06/02/1998"), itemType.name()));
				prop.setProperty("3", 
						String.format("Title: %s,Author: %s,Length: %.2f,Release Date: %tD,Item Type: %s", 
								"The Prisoner of Azkaban", "J. K. Rowling", 435D, Date.parse("09/08/1999"), itemType.name()));
			}
			
			prop.store(output, null);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Method to check if properties file exists
	 * @param itemType the item type to check for
	 * @return boolean if file exists or not
	 */
	public boolean checkPropertiesFileExists(ItemType itemType) {
		if (new File(itemType.name() + ".properties").exists()) return true;
		else return false;
	}
	
	
	// ADDING OBSERVERS: 
	// https://www.javaworld.com/article/2077258/learn-java/observer-and-observable.html
	// https://stackoverflow.com/questions/9981171/notifying-the-presenter-that-the-model-has-changed
	// https://dzone.com/articles/observer-pattern-java
}
