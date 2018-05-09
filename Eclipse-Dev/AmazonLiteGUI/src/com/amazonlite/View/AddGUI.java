package com.amazonlite.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Book;
import com.amazonlite.model.CD;
import com.amazonlite.model.DVD;
import com.amazonlite.model.InventoryItem;
import com.amazonlite.model.ItemType;

public class AddGUI extends ActionsVewTemplate {
	
	private final JButton addRecord;
	private final JButton cancel;
	private final View view;
	private final Controller controller;
	
	public AddGUI() {
		view = View.getInstance();
		controller = view.getController();
		
		addRecord = new JButton("Add record");
		addRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InventoryItem item = view.getItem();
				item.setItemType(view.getItemType());
				
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
					JOptionPane.showMessageDialog(AddGUI.this, 
							"Date must be in the MM/DD/YYYY format",
							"Date Format Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			
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
				
//				System.out.println("Title" + title);
//				System.out.println("Author" + author);
//				System.out.println("Release Date" + releaseDate);
//				System.out.println("Length" + length);
//				System.out.println("SpecialField" + specialField);
				
				
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
				controller.addItem(item);
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
		
		add(addRecord);
		add(cancel);
	}
	
}