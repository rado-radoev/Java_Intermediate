package com.amazonlite.model;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.derby.iapi.services.loader.GeneratedMethod;

import com.amazonlite.View.View;

public class ResultSetTableModel extends DefaultTableModel {

	private final Connection connection;
	private final Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	private Model model;
	private DefaultTableModel tableModel;
	
	// keep track of database connection status
	private boolean connectedToDatabase = false;
	
	// constructor initializes resultSet and obtains its meta data object
	// determines number of rows
	public ResultSetTableModel(String query) throws SQLException {
		
		connection = View.getInstance().getModel().getConnection();
				
		// create statement to query database
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		
		// update database connection status
		connectedToDatabase = true;
		
		// set query and execute it
		setQuery(query);
	}
	
	public void clearTable() {

	}
	
	public void setQuery(String query) throws SQLException {
		// ensure database connection is available 
		isDbConnected();
		
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
	
	// close Statement and Connection
	public void closeConnections() {
		if (connectedToDatabase) {
			// close statement and connection
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} finally {
				connectedToDatabase = false;
			}
		}
	}
	
	// get class that represents column type
	public Class getColumnClass(int column) {
		// ensure database connection is available
		isDbConnected();
		
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
	public String getColumnName(int column) {
		// ensure database connection is available
		isDbConnected();
		
		String columnName = "";
		
		// determine column name
		try {
			columnName = metaData.getColumnName(column + 1); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		// if problem occurs return empty string for column name
		return columnName;
	}
	
	// check if DB is connected or throw an error message
	private void isDbConnected() {
		try {
			if (!connectedToDatabase && connection != null) 
				throw new IllegalStateException("Not Connected to Databse!");
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
		}
	}
	
	// obtain value in particular row and column
	@Override
	public Object getValueAt(int row, int column) throws IllegalStateException {
		// ensure database connection is available
		isDbConnected();
		
		Object valueAt = null;
		
		// obtain a value at specified ResultSet row and column
		try {
			resultSet.absolute(row + 1);
			valueAt = resultSet.getObject(column + 1);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		// if object is null return empty string else return object 
		return valueAt == null ? "" : valueAt;
	}

	@Override
	public int getRowCount() throws IllegalStateException {
		// ensure database connection is available
		isDbConnected();
		
		return numberOfRows;
	}

	// get number of columns in ResultSet
	@Override
	public int getColumnCount() {		
		// ensure database connection is available
		isDbConnected();
		
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
}
