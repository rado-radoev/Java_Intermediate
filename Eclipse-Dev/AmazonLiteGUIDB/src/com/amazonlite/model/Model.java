package com.amazonlite.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
	public boolean addInventoryItem(String inventoryType) {
		boolean successful = false;
		
		PreparedStatement statement = null;
		String sql = "INSERT INTO CD (title, author, length, release_date, hit_single)" +
				" values (?, ?, ?, ?, ?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "test title");
			statement.setString(2, "test author");
			statement.setString(3, "5.5");
			statement.setString(4, "05/05/2005");
			statement.setString(5, "test hit single");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return successful;
	}
	
	public static void main(String[] args) {
		Model m = new Model();
	}
}
