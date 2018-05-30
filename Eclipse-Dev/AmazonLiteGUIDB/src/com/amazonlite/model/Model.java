package com.amazonlite.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.amazonlite.View.View;
import com.amazonlite.interfaces.Actionable;
import com.amazonlite.model.DVD;
import com.amazonlite.model.CD;
import com.amazonlite.model.Book;

public class Model implements Actionable {

	private Connection connection;
	private View view;
	private Item item;
	private ItemType itemType;
	
	public Model() {
		// get DB properties
		Properties props = new Properties();
		
		try { 
			props.load(new FileInputStream("database.properties"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		String user = props.getProperty("user");
		String pass = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		
		// Connect to DB 
		try {
			connection = DriverManager.getConnection(dburl, user, pass);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}
	
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
	
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Method to disconnect DB connection
	 * @return boolean if disconnect was successful or not
	 */
	public boolean disconnectFromDatabase() {
		boolean disconnected = false;
		
		try {
			getConnection().close();
			disconnected = true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return disconnected;
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
	
	public String constructSearchPattern(String propertyToFind, String valueToSearch, ItemType itemType) {
		
		String sql = String.format("SELECT * FROM %s WHERE %s LIKE \"%%%s%%\"", itemType.name(), 
				propertyToFind, valueToSearch);
			
		return sql;
	}
	
	/**
	 * Method to find an item record
	 * @param propertyToFind Name of property to find
	 * @param valueToSearch value to search for in property
	 * @param itemType type of item to search for
	 * @return ArrayList<Strings> representing every property that matches the search
	 */
	@Override
	public ArrayList<String> findRecord(String propertyToFind, String valueToSearch, ItemType itemType) {
		ArrayList<String> searchResults = new ArrayList<String>();
		ResultSet resultSet = null;
		
		String sql = String.format("SELECT * FROM %s WHERE %s LIKE ?", itemType.name(), propertyToFind);
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, valueToSearch);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				searchResults.add(convertRowToString(resultSet));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return searchResults;
		
	}
	
	/**
	 * Helper method to convert resultSet to strings
	 * @param resultset the result set entry to convert to string
	 * @return String representing the current entry in the resultSet
	 */
	private String convertRowToString(ResultSet resultset) {
		String title = null, author = null, special = null;
		Date releaseDate = null;
		Double length = null;
		Boolean bonusScenes = null;

		
		try {
			String tableName = resultset.getMetaData().getTableName(1).toUpperCase();
			title = resultset.getString("title");
			author = resultset.getString("author");
			releaseDate = resultset.getDate("releaseDate");
			length = resultset.getDouble("length");
			
			if (tableName.equals("CD")) {
				special = resultset.getString("hitsingle");
			}
			else if (tableName.equals("Book")) {
				special = resultset.getString("publisher");
			}
			else {
				bonusScenes = resultset.getBoolean("bonusscenes");
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return String.format("%s %s %s %.2f %s", title,
												 author,
												 releaseDate,
												 length,
												 special != null ? special : bonusScenes);
	}
	
	@Override
	public ArrayList<String> displayRecords(String inventoryItem, String searchString) {
		ArrayList<String> res = new ArrayList<String>();
		
		String sql = String.format("SELECT ? FROM %s", inventoryItem);
		ResultSet results; 
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, searchString);
			
			results = statement.executeQuery();
			
			while (results.next()) {
				res.add(convertRowToString(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
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
		boolean successful = false;
		
		String sql = String.format("UPDATE %S SET %S = ? WHERE ID = %S", 
														   View.getInstance().getItemType().name(),
														   attributeToModify,
														   recrodID);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, newValueToUpdate);
			
			statement.executeUpdate();
			successful = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return successful;
	}
	
	public <T> String[] getItemSpecialField(T item) {
		String selectedItemType = item.getClass().getSimpleName();
		String specialField = "";
		String itemSpecificity = ""; 
		
		if (selectedItemType.toLowerCase().equals("cd")) {
			specialField = CD.getSpecialField().replaceAll("\\s+", "").toLowerCase();
			itemSpecificity = String.valueOf(((CD)item).getHitSingle());
		} else if (selectedItemType.toLowerCase().equals("dvd")) {
			specialField = DVD.getSpecialField().replaceAll("\\s+", "").toLowerCase();
			itemSpecificity = String.valueOf(((DVD)item).getBonusScenes());
		} else if (selectedItemType.toLowerCase().equals("book")) {
			specialField = Book.getSpecialField().replaceAll("\\s+", "").toLowerCase();
			itemSpecificity = ((Book)item).getPublisher();
		}
		
		return new String[] {specialField, itemSpecificity};
	}
	
	public boolean addItem(InventoryItem item) {
		boolean successful = false;
		
		String[] itemSpecials = getItemSpecialField(item);
		String specialField = itemSpecials[0];
		String itemSpecificity = itemSpecials[1];
		LocalDate relDate = item.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		try (Statement statement = connection.createStatement()) {
		
		String sql = String.format("INSERT INTO %S (title, author, length, releasedate, %s)" +
								   " values (\"%s\", \"%s\", %.2f, \"%s\", \"%s\")", 
								   		item.getItemType().name(),
								   		specialField,
								   		item.getTitle(),
								   		item.getAuthor(),
								   		Double.valueOf(item.getLength()),
								   		relDate.toString(),
								   		itemSpecificity);
		
		statement.executeUpdate(sql);
		successful = true;	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return successful;
	}
}
