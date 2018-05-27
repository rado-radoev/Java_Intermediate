package com.amazonlite.test;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

import sun.management.ConnectorAddressLink;

public class ResultSetTableModel extends AbstractTableModel {

	private final Connection connection;
	private final Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	
	// keep track of database connection status
	private boolean connectedToDatabase = false;
	
	// constructor initializes resultSet and obtains its meta data object
	// determines number of rows
	public ResultSetTableModel(String url, String username, 
			String password, String query) throws SQLException {
		// connect to database
		connection = DriverManager.getConnection(url, username, password);
		
		// create statement to query database
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		// update database connection status
		connectedToDatabase = true;
		
		// set query and execute it
		setQuery(query);
	}
	
	public void setQuery(String query) throws SQLException, IllegalStateException {
		// ensure database connection is available 
		if (!connectedToDatabase)
			throw new IllegalStateException("Not connected to databse");
		
		// specify query and execute it
		resultSet = statement.executeQuery(query);
		
		// obtain meta data for ResultSet
		metaData = resultSet.getMetaData();
		
		// determine number of rows in ResultSet
		resultSet.last(); // move to the last row
		numberOfRows = resultSet.getRow();
		
		// notify JTable that model has changed
		fireTableDataChanged();
	}
	
	// get class that represents column type
	public Class getColumnClass(int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not connected to Databse");
		
		// determine class of column
		try {
			String className = metaData.getColumnClassName(column + 1);
			
			// return Class object that represents className
			return Class.forName(className);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		return Object.class;
	}
	
	// get name of a particular column in ResultSet
	public String getColumnName(int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase) 
			throw new IllegalStateException("Not Connected to Databse!");
		
		String columnName = "";
		
		// determine column name
		try {
			columnName = metaData.getColumnTypeName(column + 1); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		// if problem occurs return empty string for column name
		return columnName;
	}

	@Override
	public int getRowCount() throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase) 
			throw new IllegalStateException("Not Connected to Databse!");
		
		return numberOfRows;
	}

	// get number of columns in ResultSet
	@Override
	public int getColumnCount() {		
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Databse");
		
		// set default column count
		int columnCount = 0;
		
		// determine number of columns
		try {
			columnCount = metaData.getColumnCount();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		// if problem occurs return 0 for number of columns;
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
