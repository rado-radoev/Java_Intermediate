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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.amazonlite.model.Book;
import com.amazonlite.model.CD;
import com.amazonlite.model.DVD;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public class SearchGUI extends ActionsVewTemplate {
	private final JButton searchRecord;
	private final JButton cancel;

	public SearchGUI() {
						
		searchRecord = new JButton("Search");
		searchRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InventoryItem item = View.getInstance().getItem();
				item.setItemType(View.getInstance().getItemType());
				
				String title = getTitleTextField().getText();
				String author = getAuthorTextField().getText();
				String releaseDate = getReleaseDateTextField().getText();
				double length = 0D;
				String specialField = getSpecialFieldTextField().getText();
				
				Date date = null;
				try {
					date = new SimpleDateFormat("MM/dd/yyyy").parse(releaseDate);
					item.setReleaseDate(date);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(SearchGUI.this, 
							"Date must be in the MM/DD/YYYY format",
							"Date Format Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			
				try {
					length = Double.valueOf(getLengthTextField().getText());
					item.setLength(length);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(SearchGUI.this, 
							"Length not fomatted properly",
							"Length Format Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				item.setTitle(title);
				item.setAuthor(author);		
				
				// Add an additional property depending on the type of item
				if (item.getItemType().name() == "CD") {
					((CD) item).setHitSingle(specialField);
					item.setItemType(ItemType.CD);
				} else if (item.getItemType().name() == "DVD") {
					((DVD) item).setBonusScenes(specialField.isEmpty() ? false : true);
					item.setItemType(ItemType.DVD);
				} else {
					((Book) item).setPublisher(specialField);
					item.setItemType(ItemType.BOOK);
				}

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
				
				private void disbleJTextFields(JTextField textField) {
					
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
	
	
	public List<JTextField> getTextFields () {
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
