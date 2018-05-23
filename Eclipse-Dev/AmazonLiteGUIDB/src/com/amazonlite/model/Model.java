package com.amazonlite.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.derby.client.am.SqlException;

public class Model {

	private Connection connection;
	
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
	
	public List<String> searchItem(String attributeToSearch, String itemType) {
		List<String> searchResults = new ArrayList<String>();
		ResultSet resultSet = null;
		String sql = String.format("SELCT * FROM %s WHERE $s LIKE ?", itemType, attributeToSearch);
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "test title");
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				searchResults.add(convertRowToString(resultSet));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return searchResults;
	}
	
	private String convertRowToString(ResultSet resultset) {
		String title = null, author = null, special = null;
		Date releaseDate = null;
		Double length = null;
		Boolean bonusScenes = null;

		
		try {
			String tableName = resultset.getMetaData().getTableName(1);
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
												 String.valueOf(releaseDate),
												 String.valueOf(length),
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
	
	public boolean updateInventoryItem(String inventoryItem, String attributeToUpdate, String conditionAttribute) {
		boolean successful = false;
		
		String sql = String.format("UPDATE %S SET %S = ? WHERE %S = ?", inventoryItem, attributeToUpdate, conditionAttribute); 
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, "KukuBend");
			statement.setString(2, "def");
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return successful;
	}
	
	
	public boolean addInventoryItem(String inventoryType) {
		boolean successful = false;
		
		String sql = String.format("INSERT INTO %s (title, author, length, releasedate, hitsingle)" +
				" values (?, ?, ?, ?, ?)", inventoryType);
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, "fsfa");
			statement.setString(2, "fadsfas");
			statement.setDouble(3, Double.valueOf("1.2"));
			statement.setDate(4, java.sql.Date.valueOf("2001-01-01"));
			statement.setString(5, "fadfasf");
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return successful;
	}
	
	public static void main(String[] args) {
		Model m = new Model();
		//m.addInventoryItem("CD");
		//m.updateInventoryItem("CD", "Title", "Author");
		//m.displayInventory("CD", "*");
		m.searchItem("Title", "CD");
	}
}
