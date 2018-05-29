package com.amazonlite.View;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import com.amazonlite.model.ResultSetTableModel;

import javax.swing.table.TableModel;

public class DisplayGUI extends JPanel {
	
	private static String searchPattern;
	private static ResultSetTableModel tableModel;
	
	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}
	
	private String getSearchPattern() {
		return searchPattern;
	}
	
	public void displayResults() {
		// create ResultSetTableModel and display database table
		try {
			
			if (getSearchPattern().equals("") || getSearchPattern().equals(null)) {
				throw new IllegalArgumentException("No search pattern provided");
			}
			
			// create TableModel for results of query 
			tableModel = new ResultSetTableModel(getSearchPattern());
				
			// create JTable based on the tableModel
			JTable resultTable = new JTable(tableModel);
			
			// place GUI components on JFrame's content pane
			add(new JScrollPane(resultTable), BorderLayout.CENTER);
				
			final TableRowSorter<TableModel> sorter = 
					new TableRowSorter<TableModel>(tableModel);
			resultTable.setRowSorter(sorter);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			//System.exit(1);
		} catch (IllegalArgumentException iae) {
			JOptionPane.showMessageDialog(null, iae.getMessage(), "Search Pattern Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}





























