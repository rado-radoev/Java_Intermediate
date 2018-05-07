package com.amazonlite.View;

import javax.swing.JFrame;

public class AddGUI extends template {
	
	public static void main(String[] args) {
	AddGUI addg = new AddGUI();
	addg.setTitle("Add Item");
	addg.setVisible(true);
	addg.setSize(300,200);
	addg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}