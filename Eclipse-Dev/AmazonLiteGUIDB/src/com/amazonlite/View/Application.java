package com.amazonlite.View;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Model;
import com.amazonlite.View.View;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Application {

	
	public static void main (String[] args) {
		Model model = new Model();
		Controller controller = new Controller();
		View view = View.getInstance();
		
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	view.setController(controller);
	    		view.setModel(model);		
	    		controller.setView(view);
	    		controller.setModel(model);
	    		model.setView(view);
	
	    		view.setSize(400,350);
	    		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		view.setResizable(false);
	    		view.setLocationRelativeTo(null);
	    		view.setVisible(true);
	        }
		});
	}		
}

