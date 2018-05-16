package com.amazonlite.model;

import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.interfaces.Observable;
import com.amazonlite.interfaces.Observer;
import com.amazonlite.props.InitializeProperties;

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

public class Model implements Actionable, Observable {
	
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

	public void setView(View view2) {
		this.view = view2;
	}

	/**
	 * Method to create new Inventory Item object
	 * based on the selected Enum
	 * @param itemType the Enum object selected
	 */
	@Override
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
	 * Method to read properties, passed as argument and notify observers for each K,V pair as a String
	 * @param prop the properties to iterate through
	 */
	@Override
	public void displayRecords(Properties prop) {
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.putAll(prop.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		
		Iterator<Entry<String, String>> propMapIterator = propMap.entrySet().iterator();
		while (propMapIterator.hasNext()) {
			Map.Entry<String, String> property = (Map.Entry<String, String>) propMapIterator.next();
			notifyObserver(view, property.getKey() + " = " + property.getValue());
		}
	}
	
	/**
	 * Helper method that reads all properties from a properties file
	 * @param fileName the name of the properties file to read
	 * @return Properties object or null if no properties are loaded or file is missing
	 */
	private Properties readProps(String fileName) {
		Properties props = new Properties();
		
		try (InputStream input = new FileInputStream(fileName)) {
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
	 * Method that loads properties file
	 * @param item InventoryItem object to load properties from file
	 * @return Properties object containing all properties from InventoryItem's properties file
	 */
	public Properties loadRecords(InventoryItem item) {
		String fileName = item.getClass().getCanonicalName().substring(item.getClass().getCanonicalName().lastIndexOf(".") + 1) + ".properties";
		
		return readProps(fileName);
	}
	
	/**
	 * Method that loads properties
	 * @param itemType ItemType Enum to load properties from file
	 * @return Properties object containing all properties from InventoryItem's properties file
	 */
	public Properties loadRecords(ItemType itemType) {
		String fileName = itemType.name() + ".properties";
		
		return readProps(fileName);
	}
	
	/**
	 * Method that saves Properties to file
	 * @param properties Properties object to save
	 * @param itemType Type of item to save properties for
	 * @return true if file is saved successfully and false if file was not saved
	 */
	private boolean saveProperties(Properties properties, ItemType itemType) {
		boolean saveSuccessful = false;
		String fileName = itemType.name() + ".properties";
		
		try (OutputStream output = new FileOutputStream(fileName)) {
			properties.store(output, null);
			saveSuccessful = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return saveSuccessful;
	}
	
	/**
	 * Method to add item
	 * @param item item to add
	 * @return boolean if item addition is successful or not
	 */
	@Override
	public boolean addItem(InventoryItem item) {
		boolean successfullAdd = false;
		Properties property = new Properties();
		property = loadRecords(item);

		property.setProperty(String.valueOf(property.size() + 1), item.toString());
		if (saveProperties(property, item.getItemType())) {
			successfullAdd = true;
		}
			
		return successfullAdd;
	}

	/**
	 * Method to update property value. Method is <b>deprecated</b> but is left for backward compatibility
	 * @param propertyToModify Id of the property to modify
	 * @param attributeToModify Name of attribute to modify
	 * @param oldValueToUpdate old value to be modified
	 * @param newValueToUpdate new value to replace old value
	 */
	@Deprecated
	public void updateRecord(String propertyToModify, String attributeToModify ,String oldValueToUpdate, String newValueToUpdate) {
		
		ItemType itemType = ItemType.valueOf(propertyToModify.substring(propertyToModify.indexOf("Type: ") + 6));
		String key = propertyToModify.substring(0, propertyToModify.indexOf(" = "));
		String newPropValue1 = propertyToModify.substring(0, propertyToModify.indexOf(attributeToModify) + attributeToModify.length() + 2);
		String newPropValue2 = propertyToModify.substring(newPropValue1.length() + oldValueToUpdate.length());
		String newPropValueFull = newPropValue1 + newValueToUpdate + newPropValue2;
		
		Properties property = new Properties();
		property = loadRecords(itemType);
		
		property.setProperty(key, newPropValueFull.substring(newPropValueFull.indexOf("Title: ")));
		saveProperties(property, itemType);
	}
	
	/**
	 * Method to update record
	 * @param recordID the id of the record to update. Represented as a String
	 * @param attributeToModify the name of the to modify. Represented as a String
	 * @param newValueToUpdate the new value to be added updated
	 * @return boolean true or false if update is successful or not
	 */
	@Override
	public boolean updateRecord(String recrodID, String attributeToModify, String newValueToUpdate) {
		boolean successfullyUpdate = false;
		ItemType itemType = View.getInstance().getItemType();
		Properties property = new Properties();
		property = loadRecords(itemType);
		
		String fullRecord = property.getProperty(recrodID);
		int startIndexToModify = fullRecord.indexOf(attributeToModify); 
		
		String modfiedRecord = fullRecord.substring(0, startIndexToModify + attributeToModify.length() + 2) + 
				newValueToUpdate + 
				fullRecord.substring(startIndexToModify + attributeToModify.length() + 2 + newValueToUpdate.length());
		
		property.setProperty(recrodID, modfiedRecord);
		if (saveProperties(property, itemType)) {
			successfullyUpdate = true;
		}
		
		return successfullyUpdate;
	}
	
	
	/**
	 * Method to find record by provided ID
	 * @param records an ArrayList<String> of records
	 * @param recordId the record ID to search for
	 * @return String representation of the record that matches the search or null if 
	 * no matches found
	 */
	public String findRecordById(ArrayList<String> records, String recordId) {
		for (String record : records) {
			String currentRecordId = record.substring(0, record.indexOf(" = "));
			if (currentRecordId.equals(recordId)) {
				return record;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Method to find an item record
	 * @param propertyToFind Name of property to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList<Strings> representing every property that matches the search
	 */
	@Override
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch ,ItemType itemType) {

		Properties property = new Properties();
		property = loadRecords(itemType);

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
					res.add(String.format("%s = %s", prop.getKey(), prop.getValue()));
				}
			}
		}

		if (res.isEmpty()) {
			res.add(String.format("%S", "no match found"));
		}
		
		return res;
	}
	
	/**
	 * Method to initialize default inventory files if none are existing
	 */
	public void initializeDefaultProperties() {
		InitializeProperties.init();
	}
	
	
	
	/**
	 * Method to check if inventory file exists
	 * @param itemType the item type to check for
	 * @return boolean if file exists or not
	 */
	public boolean checkInventoryExists(ItemType itemType) {
		if (new File(itemType.name() + ".properties").exists()) return true;
		else return false;
	}
	
	/**
	 * Method that outputs data in ArrayList<String> to View class
	 * @return date in ArrayList<String> or null
	 */
	public ArrayList<String> outputToView() {
		return null;
	}

	/**
	 * Method to unregister observers
	 */
	@Override
	public void removeObserver(Observer o) {
		views.remove(o);
	}


	/**
	 * Mehtod to notify all observers for a change
	 */
	@Override
	public void notifyObserver() {
		for (Observer view : views) {
			view.update();
		}
	}
		
	/**
	 * Method to notify a specific observer for a change 
	 * @param message The message to be sent to the observer
	 */
	@Override
	public void notifyObserver(Observer o, String message) {
		o.update(message);
	}

	/**
	 * Method to add subscribe observer to model events
	 * @param o observer to add a subscriber
	 */
	@Override
	public void addObserver(Observer o) {
		views.add(o);
		
	}
}
