package com.amazonlite.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
		System.out.println("DB connection successful to: " + connection);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Model m = new Model();
	}
}
