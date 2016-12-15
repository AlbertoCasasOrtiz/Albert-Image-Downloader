package project.controller;

import project.model.Model;
import project.view.View;

public class Controller {
	
	public Controller(){

		Model model = new Model();
		@SuppressWarnings("unused")
		View view = new View(model);
	}
}
