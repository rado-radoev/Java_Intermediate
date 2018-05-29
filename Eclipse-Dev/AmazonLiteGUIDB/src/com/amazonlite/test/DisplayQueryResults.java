package com.amazonlite.test;

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
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class DisplayQueryResults extends JFrame {
	

	// database URL, username and password
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/AmazonLite?useSSL=false";
	private static final String USERNAME = "root";
	private static final String PASSWORD =  "rado";
	private static String DEFAULT_QUERY = "SELECT * FROM CD";
	private static ResultSetTableModel tableModel;
			
	public static void main(String[] args) {
		// create ResultSetTableModel and display database table
		try {
			// create TableModel for results of query SELECT * FROM CD
			tableModel = new ResultSetTableModel(DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
				
			// create JTable based on the tableModel
			JTable resultTable = new JTable(tableModel);
			
			// place GUI components on JFrame's content pane
			JFrame window = new JFrame("Display Query Results");
			window.add(new JScrollPane(resultTable), BorderLayout.CENTER);
				
			final TableRowSorter<TableModel> sorter = 
					new TableRowSorter<TableModel>(tableModel);
			resultTable.setRowSorter(sorter);
						
			// dispose of window when user quits application (this overrides the default of HIDE_ON_CLOSE
			window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			window.setSize(500, 250);
			window.setVisible(true);
			
			// ensure database is closed when user quits application
			window.addWindowListener(new WindowAdapter() {
				// disconnect from database and exit when window has closed
				public void windowClosed(WindowEvent event) {
					tableModel.disconnectFromDatabase();
					System.exit(0);
				}
			}); 
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}





























