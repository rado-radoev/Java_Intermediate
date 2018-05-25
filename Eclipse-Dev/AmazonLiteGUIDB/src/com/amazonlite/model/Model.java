package com.amazonlite.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
			System.out.println("DB connection successful to: " + connection.getSchema());
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
	
	
	public void displayInventory(String inventoryItem, String searchString) {
		String sql = String.format("SELECT ? FROM %s", inventoryItem);
		ResultSet results; 
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, searchString);
			
			results = statement.executeQuery();
			
			while (results.next()) {
				System.out.println(convertRowToString(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		String sql = String.format("UPDATE %S SET %S = ? WHERE ID = %S", "CD"/*View.getInstance().getItemType().name()*/,
														   attributeToModify,
														   recrodID);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, newValueToUpdate);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return successful;
	}
	
	
	public boolean addItem(InventoryItem item) {
		boolean successful = false;
			
		String sql = String.format("INSERT INTO %s (title, author, length, releasedate)" +
				" values (?, ?, ?, ?)", item.getItemType());
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, item.getTitle());
			statement.setString(2, item.getAuthor());
			statement.setDouble(3, item.getLength());
			Instant rdInst = item.getReleaseDate().toInstant();
			LocalDate ldt = LocalDateTime.ofInstant(rdInst, ZoneOffset.UTC).toLocalDate();
			statement.setDate(4, java.sql.Date.valueOf(ldt));
						
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return successful;
	}
	
	
//	public boolean addInventoryItem(String inventoryType) {
//		boolean successful = false;
//		
//		String sql = String.format("INSERT INTO %s (title, author, length, releasedate, hitsingle)" +
//				" values (?, ?, ?, ?, ?)", inventoryType);
//		
//		try (PreparedStatement statement = connection.prepareStatement(sql)) {
//			statement.setString(1, "fsfa");
//			statement.setString(2, "fadsfas");
//			statement.setDouble(3, Double.valueOf("1.2"));
//			statement.setDate(4, java.sql.Date.valueOf("2001-01-01"));
//			statement.setString(5, "fadfasf");
//			
//			statement.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return successful;
//	}
	
	public static void main(String[] args) {
		Model m = new Model();
		InventoryItem iv = new InventoryItem("This title is too late", 
				"This author is too late",
				65.5,
				new Date(2001-05-03),
				ItemType.CD);
		m.addItem(iv);
		//m.addInventoryItem("CD");
		//m.updateInventoryItem("CD", "Title", "Author");
		//m.displayInventory("CD", "*");
		//System.out.println(m.findRecord("Title", "fsfa", ItemType.CD));
		m.updateRecord("2", "Title", "UpdateRecordTestTItle");
	}

	@Override
	public ArrayList<String> displayRecords(Properties prop) {
		// TODO Auto-generated method stub
		return null;
	}
}
