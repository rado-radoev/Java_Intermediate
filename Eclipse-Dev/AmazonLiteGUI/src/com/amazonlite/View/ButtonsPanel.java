package com.amazonlite.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel {
	
	private final JButton addRecord;
	private final JButton cancel;
	
	
	public ButtonsPanel() {
		
		addRecord = new JButton("Add record");
		addRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String title = getTitleTextField().getText();
				String author = getAuthorTextField().getText();
				String releaseDate = getReleaseDateTextField().getText();
				
				Date date = null;
				try {
					date = new SimpleDateFormat("MM/dd/yyyy").parse(releaseDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				double length = Double.valueOf(getLengthTextField().getText());
				String specialField = getSpecialFieldTextField().getText();
				
				
				System.out.println("Title" + title);
				System.out.println("Author" + author);
				System.out.println("Release Date" + releaseDate);
				System.out.println("Length" + length);
				System.out.println("SpecialField" + specialField);
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