package com.amazonlite.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.amazonlite.View.AddGUI;
import com.amazonlite.View.DisplayGUI;
import com.amazonlite.View.SearchGUI;
import com.amazonlite.View.UpdateGUI;

public class Tabbed extends JFrame {
	
	public Tabbed() {
		super("Tabbed pane test");
		setSize(350,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel panel1 = new AddGUI();
		JPanel panel2 = new SearchGUI();
		JPanel panel3 = new UpdateGUI();
		JPanel panel4 = new DisplayGUI();
		
		tabbedPane.add(panel1, "Add");
		tabbedPane.add(panel2, "Search");
		tabbedPane.add(panel3, "Updated");
		tabbedPane.add(panel4, "Display");
		
		add(tabbedPane);
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Tabbed().setVisible(true);
            }
        });
	}
}
