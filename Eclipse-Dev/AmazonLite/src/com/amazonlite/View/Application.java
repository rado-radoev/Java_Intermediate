package com.amazonlite.View;

import com.amazonlite.Controller.Controller;
import com.amazonlite.model.Model;
import com.amazonlite.View.View;

public class Application {
	
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller();
		View view = new View(controller);
		view.setModel(model);		
		
		model.setView(view);
		
		view.start();
		
	}
}
