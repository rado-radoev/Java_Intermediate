package com.amazonlite.props;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.OutputStream;
import java.sql.Date;

import com.amazonlite.model.ItemType;

public class InitializeProperties {

	private void initializeDefaultProperites(ItemType itemType) {
		if (checkPropertiesFileExists(itemType)) { }
	}
	
	private void createDummyProperties(ItemType itemType) {
		Properties prop = new Properties();
		
		String fileName = itemType.name() + ".prpperties";
		
		try (OutputStream output = new FileOutputStream(fileName)) {
			if (itemType == itemType.CD) {
				prop.setProperty("1", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"T.N.T", "AC/DC", 41.55, Date.valueOf("12/01/1975"), itemType.name()));
				prop.setProperty("2", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"Let There Be Rock", "AC/DC", 40.19, Date.valueOf("03/21/1977"), itemType.name()));
				prop.setProperty("3", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"Black Ice", "AC/DC", 55.38, Date.valueOf("10/17/2008"), itemType.name()));
			}
			else if (itemType == itemType.DVD) {
				prop.setProperty("1", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"The Fellowship of the Ring", "Peter Jackson", 3.28, Date.valueOf("12/19/2001"), itemType.name()));
				prop.setProperty("2", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"The Two Towers", "Peter Jackson", 3.43, Date.valueOf("12/18/2002"), itemType.name()));
				prop.setProperty("3", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"The Return of the King", "Peter Jackson", 4.12, Date.valueOf("12/17/2003"), itemType.name()));
			}
			else if (itemType == itemType.BOOK) {
				prop.setProperty("1", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"The Philosopher's Stone", "J. K. Rowling", 329, Date.valueOf("09/01/1997"), itemType.name()));
				prop.setProperty("2", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"The Chamber of Secrets", "J. K. Rowling", 341, Date.valueOf("06/02/1998"), itemType.name()));
				prop.setProperty("3", 
						String.format("Title: %s%nAuthor: %s%nLength: %2d%nRelease Date: %tD%nItem Type: %s%n", 
								"The Prisoner of Azkaban", "J. K. Rowling", 435, Date.valueOf("09/08/1999"), itemType.name()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to return list of properties
	 * @param itemType the property type to load
	 * @return the Properties in the file or null
	 */
	private Properties loadProperties(ItemType itemType) {
		String fileName = itemType.name() + ".prpperties";
		
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(fileName)) {
			
			// load properties file
			prop.load(input);
			
			return prop;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return null;
	}

	/**
	 * Method to check if properties file exists
	 * @param itemType the item type to check for
	 * @return boolean if file exists or not
	 */
	private boolean checkPropertiesFileExists(ItemType itemType) {
		if (new File(itemType.name() + ".properties").isFile()) return true;
		else return false;
	}
}
