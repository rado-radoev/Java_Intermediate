package com.amazonlite.props;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Savepoint;
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
	public boolean saveProperties(Properties properties, ItemType itemType) {
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
		property.setProperty(String.valueOf(property.size() + 5), item.toString());
		saveProperties(property, item.getItemType());
	}
	
	/**
	 * Update property
	 */
	
	
	/**
	 * Delete property
	 */
	
	/**
	 * Find property
	 */
	
}









































