package com.amazonlite.View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class UpdateGUI extends ActionsVewTemplate {

	private final JButton update;
	private final JButton cancel;
	
	public UpdateGUI() {
		
		update = new JButton("Update");
		update.setPreferredSize(new Dimension(75, 26));
		
		
		
		
		
		
		
		
		
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(75, 26));
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
		
		add(update);
		add(cancel);
				
	}
}
