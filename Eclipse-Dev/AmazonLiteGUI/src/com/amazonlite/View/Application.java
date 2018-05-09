package com.amazonlite.View;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Model;
import com.amazonlite.View.View;

public class Application {

	
	public static void main (String[] args) {
		Model model = new Model();
		View view = View.getInstance();
		Controller controller = new Controller();
		
		
		view.setVisible(true);
		view.setSize(200,300);
		
		view.setController(controller);
		view.setModel(model);		
		controller.setView(view);
		controller.setModel(model);
		model.setView(view);
		}		
	}

