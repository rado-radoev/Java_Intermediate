package com.amazonlite.model;

import com.amazonlite.View.View;
import com.amazonlite.props.InitializeProperties;
import com.amazonlite.props.Props;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Observable;



public class Model extends Observable {
	
	private ItemType itemType;
	private Item item;
	private Props props = new Props();
	private View view = new View();
	
	public Model() {
		InitializeProperties.init();
		//addObserver(view);
	}
	
	public Model(Item item) {
		this.item = item;
		this.itemType = item.getItemType();
		InitializeProperties.init();
		//addObserver(view);
	}

	/**
	 * Display properties
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
	}
	
	
	/**
	 * Load Properties
	 */
	public Properties loadProperties(ItemType itemType) {
		String fileName = itemType.name() + ".properties";
		
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
	 * Save Properties
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
	 * Add property
	 */
	public void addItem(Item item) {
		Properties property = new Properties();
		property = loadProperties(item.getItemType());

		property.setProperty(String.valueOf(property.size() + 1), item.toString());
		saveProperties(property, item.getItemType());
	}
	
	/**
	 * Update property
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
	 * Delete property
	 */
	
	/**
	 * Find property
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
				if (string.startsWith(propertyToFind) && string.contains(valueToSearch)) {
//					System.out.println(prop.getValue());
					res.add(String.format("%s = %s", prop.getKey(), prop.getValue()));
				}
			}
		}
		
		
		if (res.isEmpty()) {
			res.add(String.format("%S", "no match found"));
			notifyObservers();
		}

		return res;
		
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

	
//	public void addItem( Item item ) {
//		props.addItem(item);
//	}
//	
//	public void updateProperty(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate) {
//		props.updateProperty(propertyToModify, attributeToModify, oldValueToUpdate, newValueToUpdate);
//	}
//	
//	public ArrayList<String> findProperty(String propertyToFind, String valueToSearch ,ItemType itemType) {
//		return props.findProperty(propertyToFind, valueToSearch, itemType);
//	}
}
