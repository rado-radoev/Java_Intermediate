package com.amazonlite.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class SearchGUI extends ActionsVewTemplate {
	
	private final JButton searchRecord;
	private final JButton cancel;
	private final JPanel buttonPanel;
	String ACTION_KEY = "theAction";
	
	public SearchGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		/**
		 * Text fields will be disabled once text is entered in one of the text fields
		 * All text fields but the one that has text in it will be disabled
		 * Text fields are enabled again as long as there is not text entered in any of them
		 */
		List<JTextField> textFields = getTextFields();
		for (JTextField textField : textFields) {
			textField.getDocument().addDocumentListener(new DocumentListener() {
							
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					if (textField.getText().equals("")) {
						for (JTextField jTextField : textFields) {
							jTextField.setEnabled(true);
						}
					}
					
				}
				
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					for (JTextField jTextField : textFields) {
						if (textField != jTextField) {
							jTextField.setEnabled(false);
						}
					}
				}
				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					//Plain text components do not fire these events
					
				}
			});
		}
		
		/**
		 * Search button gathers text from text fields and searches
		 * inventory. Then moves to Display tab to display results
		 */
		searchRecord = new JButton("Search");
		searchRecord.setPreferredSize(new Dimension(75, 26));
		searchRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				View.getInstance().getDisplayGUI().setSearchPattern(findRecords());
				View.getInstance().getDisplayGUI().displayResults();
				View.getInstance().getTabbedPane().setSelectedIndex(4);
			}
		});
		
		/**
		 * Cancel button clears text fields
		 */
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(75, 26));
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTextFields();
			}
		});

		buttonPanel = new JPanel();
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		buttonPanel.add(searchRecord);
		buttonPanel.add(cancel);
		
		add(buttonPanel);
	}

	private void clearTextFields() {
		List<JTextField> textFields = getTextFields();
		setTextFieldText(getAuthorTextField(), "");
		setTextFieldText(getTitleTextField(), "");
		setTextFieldText(getLengthTextField(), "");
		// when clearing the date field set it back to the default text
		setTextFieldText(getReleaseDateTextField(), getDATE_FORMAT());
		getReleaseDateTextField().setForeground(Color.GRAY);
		setTextFieldText(getSpecialFieldTextField(), "");
		for (JTextField jTextField : textFields) {
			jTextField.setEnabled(true);
		}
	}
	
	/**
	 * Method to search for records that match specific criteria
	 * @return ArrayList<String> of results that are matching the query
	 */
	public String findRecords() {
		String recordsFound = "";
		
		String title = getTitleTextField().getText();
		String author = getAuthorTextField().getText();
		String releaseDate = getReleaseDateTextField().getText();
		double length = 0D;
		String specialField = getSpecialFieldTextField().getText();
		
		try {
			length = Double.valueOf(getLengthTextField().getText());
		} catch (NumberFormatException nfe) {
			// No implementation
		}
		
		if (!title.equals("")) {
			recordsFound = View.getInstance().getController().constructSearchPattern("Title", getTitleTextField().getText(), View.getInstance().getItemType());
		}
		else if (!author.equals("")) {
			recordsFound = View.getInstance().getController().constructSearchPattern("Author", getAuthorTextField().getText(), View.getInstance().getItemType());
		}
		else if (!releaseDate.equals("") && !releaseDate.equals(getDATE_FORMAT())) {
			recordsFound = View.getInstance().getController().constructSearchPattern("ReleaseDate", getReleaseDateTextField().getText(), View.getInstance().getItemType());
		}
		else if (length > 0) {
			recordsFound = View.getInstance().getController().constructSearchPattern("Length", getLengthTextField().getText(), View.getInstance().getItemType());
		}
		else if (!specialField.equals("")) {
			String special = View.getInstance().getModel().getItemSpecialField(View.getInstance().getItem())[0];
			recordsFound = View.getInstance().getController().constructSearchPattern(special, getSpecialFieldTextField ().getText(), View.getInstance().getItemType());
		}
		
		return recordsFound;
	}
	
	/**
	 * Helper method that returns all components in a container
	 * @param container the container to search through and return all components
	 * @return List of components
	 */
	private Component[]	getComponents(Component container) {
		ArrayList<Component> list = null;
		
		// Get every component in the container and add it to the array
		try {
			list = new ArrayList<Component>(Arrays.asList(
					((Container)container).getComponents()));
			for (int index = 0; index < list.size(); index++) {
				for (Component currentComponent : getComponents(list.get(index))) {
					list.add(currentComponent);
				}
			}
		} catch (ClassCastException e) {
			list = new ArrayList<Component>();
		}
		
		return list.toArray(new Component[list.size()]);
	}
	
	/**
	 * Helper method that returns all text fields from array of components
	 * @return List<JTextField> containing all text fields in the provided components collection
	 */
	private List<JTextField> getTextFields () {
		List<JTextField> textFields = new ArrayList<JTextField>();
		Component[] components = getComponents(this);
	
		for (Component component : components) {
			if (component.getClass().equals(JTextField.class)) {
				textFields.add((JTextField)component);
			}
		}
		
		return textFields;
	}

}
