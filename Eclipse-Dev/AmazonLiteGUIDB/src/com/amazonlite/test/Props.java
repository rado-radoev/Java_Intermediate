package com.amazonlite.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.stream.Collectors;

import com.amazonlite.model.Item;
import com.amazonlite.model.ItemType;

import java.util.Map;
import java.util.Map.Entry;

public class Props {

	public static void main(String[] args) {
		Props props = new Props();
		
		props.saveProperties(props.loadProperties(ItemType.CD), ItemType.CD);
		props.displayProperties(props.loadProperties(ItemType.CD));
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
		
			
		//1 = Title: T.N.T,Author: AC/DC,Length: 41.55,Release Date: 12/01/75,Item Type: CD

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
		}

		return res;
		
	}
		
}









































