package com.amazonlite.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

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
	
	public boolean updateInventoryItem(String tableName, String column, String condition) {
		boolean successful = false;
		
		String sql = String.format("UPDATE %S SET %S = ? WHERE %S = ?", tableName, column, condition); 
		
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
		m.updateInventoryItem("CD", "Title", "Author");
	}
}
