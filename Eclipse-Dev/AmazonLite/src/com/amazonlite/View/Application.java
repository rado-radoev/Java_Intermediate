package com.amazonlite.View;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Model;
import com.amazonlite.View.View;

public class Application {
	
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller();
		View view = new View();
		
		view.setController(controller);
		view.setModel(model);		
		controller.setView(view);
		model.setView(view);
		
		view.start();
		
	}
}
