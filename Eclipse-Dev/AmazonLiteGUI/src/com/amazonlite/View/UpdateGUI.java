package com.amazonlite.View;

import javax.swing.JButton;

public class UpdateGUI extends ActionsVewTemplate {

	private final JButton update;
	private final JButton cancel;
	
	public UpdateGUI() {
		
		update = new JButton("Update");
		cancel = new JButton("Cancel");
		
		add(update);
		add(cancel);
				
	}
}
