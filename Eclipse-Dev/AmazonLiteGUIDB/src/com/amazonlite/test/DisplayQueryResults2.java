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

import com.amazonlite.model.ResultSetTableModel;

import javax.swing.table.TableModel;

public class DisplayQueryResults2 extends JFrame {
	

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
			
			// set up JTextArea in which user types queries
//			final JTextArea queryArea = new JTextArea(DEFAULT_QUERY, 10, 100);
//			queryArea.setWrapStyleWord(true);
//			queryArea.setLineWrap(true);
//			
//			JScrollPane scrollPane = new JScrollPane(queryArea, 
//					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
//					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//			
//			// setup JButton for submitting queries
//			JButton submitButton = new JButton("Submit Query");
//			
//			// create Box to manage placement of queryArea and submitButton in GUI
//			Box boxNorth = Box.createHorizontalBox();
//			boxNorth.add(scrollPane);
//			boxNorth.add(submitButton);
			
			// create JTable based on the tableModel
			JTable resultTable = new JTable(tableModel);
			
//			JLabel filterLabel = new JLabel("Filter:");
//			final JTextField filterText = new JTextField();
//			JButton filterButton = new JButton("Apply Filter;");
			
//			Box boxSouth = Box.createHorizontalBox();
//			boxSouth.add(filterLabel);
//			boxSouth.add(filterText);
//			boxSouth.add(filterButton);
			
			// place GUI components on JFrame's content pane
			JFrame window = new JFrame("Display Query Results");
//			window.add(boxNorth, BorderLayout.NORTH);
			window.add(new JScrollPane(resultTable), BorderLayout.CENTER);
//			window.add(boxSouth, BorderLayout.SOUTH);
			
			// create an event listener for submit button
//			submitButton.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// perform a new query
//					try {
//						tableModel.setQuery(queryArea.getText());
//					} catch (SQLException sqle) {
//						JOptionPane.showMessageDialog(null, sqle.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
//					
//						// try to recover from invalid user query
//						// by executing default query
//						try {
//							tableModel.setQuery(DEFAULT_QUERY);
//							queryArea.setText(DEFAULT_QUERY);
//						} catch (SQLException sqle2) {
//							JOptionPane.showMessageDialog(null, sqle2.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
//						
//							// ensure db connection is closed
//							tableModel.disconnectFromDatabase();
//							
//							System.exit(1); // terminate application
//						}
//					
//					}
//				}
//			});
			
			final TableRowSorter<TableModel> sorter = 
					new TableRowSorter<TableModel>(tableModel);
			resultTable.setRowSorter(sorter);
			
//			// create listener for filterbutton
//			filterButton.addActionListener(new ActionListener() {
//				
//				@Override
//				// pass filter text to listener
//				public void actionPerformed(ActionEvent e) {
//					String text = filterText.getText();
//					
//					if (text.length() == 0)
//						sorter.setRowFilter(null);
//					else {
//						try {
//							sorter.setRowFilter(RowFilter.regexFilter(text));
//						} catch (PatternSyntaxException pse) {
//							JOptionPane.showMessageDialog(null, "Bad regex pattern", "Bad regex pattern", JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				}
//			});
			
			// dispose of window when user quits application (this overrides the default of HIDE_ON_CLOSE
			window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			window.setSize(500, 250);
			window.setVisible(true);
			
			// ensure database is closed when user quits application
			window.addWindowListener(new WindowAdapter() {
				// disconnect from database and exit when window has closed
				public void windowClosed(WindowEvent event) {
					tableModel.closeConnections();
					System.exit(0);
				}
			}); 
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}





























