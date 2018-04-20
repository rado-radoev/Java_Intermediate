package com.amazonlite.props;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.io.OutputStream;

import com.amazonlite.model.ItemType;
import com.amazonlite.model.Model;

public class InitializeProperties {
	
	private Model model;
	
	public InitializeProperties() {
		model = new Model();
		
		InitializeProperties initProps = new InitializeProperties();
		for (ItemType itemType : ItemType.values()) {
			initProps.initializeDefaultProperites(itemType);
		}	
	}

	private void initializeDefaultProperites(ItemType itemType) {
		if (model.checkPropertiesFileExists(itemType)) { 
			model.displayProperties(model.loadProperties(itemType));
		}
		else {
			createDummyProperties(itemType);
		}
	}
	
	private void createDummyProperties(ItemType itemType) {
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
}
