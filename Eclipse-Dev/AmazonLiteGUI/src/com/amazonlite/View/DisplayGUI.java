package com.amazonlite.View;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class DisplayGUI extends ActionsVewTemplate{

	public DisplayGUI() {
		super.getTitleTextField().setEditable(false);
		super.getAuthorTextField().setEditable(false);
		super.getReleaseDateTextField().setEditable(false);
		super.getLengthTextField().setEditable(false);
		super.getSpecialFieldTextField().setEditable(false);
	}
	
	public static void main(String[] args) {
		DisplayGUI dg = new DisplayGUI();
		dg.setVisible(true);
		dg.setSize(300,200);
		dg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
