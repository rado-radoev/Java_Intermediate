package com.amazonlite.View;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class SearchGUI extends ActionsVewTemplate {
	private final JButton searchRecord;
	private final JButton cancel;
	private ArrayList<String> searchResults;
	
	public SearchGUI() {
						
		searchRecord = new JButton("Search");
		searchRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String title = getTitleTextField().getText();
				String author = getAuthorTextField().getText();
				String releaseDate = getReleaseDateTextField().getText();
				double length = 0D;
				String specialField = getSpecialFieldTextField().getText();
				
				Date date = null;
				try {
					date = new SimpleDateFormat("MM/dd/yyyy").parse(releaseDate);
				} catch (ParseException e) {
					// Don't care if this is empty
				}
				
			
				try {
					length = Double.valueOf(getLengthTextField().getText());
				} catch (NumberFormatException nfe) {
					// Don't care if this is empty
				}
				
				
				
				if (!title.equals("")) {
					searchResults = View.getInstance().getController().searchItem("Title", getTitleTextField().getText(), View.getInstance().getItemType());
				}
				else if (!author.equals("")) {
					searchResults = View.getInstance().getController().searchItem("Author", getAuthorTextField().getText(), View.getInstance().getItemType());
				}
				else if (!releaseDate.equals("")) {
					searchResults = View.getInstance().getController().searchItem("Release Date", getReleaseDateTextField().getText(), View.getInstance().getItemType());
				}
				else if (length > 0) {
					searchResults = View.getInstance().getController().searchItem("Length", getLengthTextField().getText(), View.getInstance().getItemType());
				}
				else if (!specialField.equals("")) {
					searchResults = View.getInstance().getController().searchItem("Special Field", getSpecialFieldTextField ().getText(), View.getInstance().getItemType());
				}
				
				View.getInstance().getDisplayGUI().setTextArea(searchResults);
				View.getInstance().getTabbedPane().setSelectedIndex(4);
			}
		});
		
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setTextFieldText(getAuthorTextField(), "");
				setTextFieldText(getTitleTextField(), "");
				setTextFieldText(getLengthTextField(), "");
				setTextFieldText(getReleaseDateTextField(), "");
				setTextFieldText(getSpecialFieldTextField(), "");
			}
		});
		
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
		
		add(searchRecord);
		add(cancel);
	}
	
	
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
