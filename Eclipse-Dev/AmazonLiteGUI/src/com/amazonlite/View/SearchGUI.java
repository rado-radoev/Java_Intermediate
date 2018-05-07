package com.amazonlite.View;

import javax.swing.JFrame;

public class SearchGUI extends template {

	public static void main(String[] args) {
		SearchGUI sg = new SearchGUI();
		sg.setTitle("Search Item");
		sg.setVisible(true);
		sg.setSize(300,200);
		sg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
