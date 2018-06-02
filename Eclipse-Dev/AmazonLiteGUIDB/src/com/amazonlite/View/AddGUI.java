package com.amazonlite.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.amazonlite.model.Book;
import com.amazonlite.model.CD;
import com.amazonlite.model.DVD;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public class AddGUI extends ActionsVewTemplate {

	private final JButton addRecord;
	private final JButton cancel;
	private final JPanel buttonPanel;
	
	public AddGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		/**
		 * Button that adds records to inventory
		 */
		addRecord = new JButton("Add");
		addRecord.setPreferredSize(new Dimension(75, 26));
		addRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InventoryItem item = View.getInstance().getItem();
				item.setItemType(View.getInstance().getItemType());
				
				String title = getTitleTextField().getText();
				String author = getAuthorTextField().getText();
				String releaseDate = getReleaseDateTextField().getText();
				double length = 0D;
				String specialField = getSpecialFieldTextField().getText();
				
				// Basic data validation performed. Date only allowed in the MM-DD-YYYY format
				Date date = null;
				try {
					// convert the date entered and set
					SimpleDateFormat dateFormat = new SimpleDateFormat(getDATE_FORMAT());
					date = dateFormat.parse(releaseDate);
					item.setReleaseDate(date);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(AddGUI.this, 
							String.format("Date must be in the %s format", getDATE_FORMAT()),
							"Date Format Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				// Checking if the input is a valid double
				try {
					length = Double.valueOf(getLengthTextField().getText());
					item.setLength(length);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(AddGUI.this, 
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

				// Add item to the properties file
				boolean itemAddSuccessful = View.getInstance().getController().addItem(item);
				// Message will be output to the user if addition was successful or not
				JOptionPane.showMessageDialog(AddGUI.this, 
						"Item Add " + (itemAddSuccessful ? "successfull" : "not successfull"),
						"Item Add",
						itemAddSuccessful ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
			}
		});
		
		/**
		 * Cancel button clears all text fields
		 */
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(75, 26));
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Clear all text fields
				setTextFieldText(getAuthorTextField(), "");
				setTextFieldText(getTitleTextField(), "");
				setTextFieldText(getLengthTextField(), "");
				setTextFieldText(getReleaseDateTextField(), getDATE_FORMAT());
				getReleaseDateTextField().setForeground(Color.GRAY);
				setTextFieldText(getSpecialFieldTextField(), "");
			}
		});
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		buttonPanel.add(addRecord);
		buttonPanel.add(cancel);
		
		add(buttonPanel);
	}
	
}