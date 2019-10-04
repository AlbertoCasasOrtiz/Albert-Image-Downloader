package main.project.controller;

import main.project.model.Model;
import main.project.view.View;

/**
 * Class controller of the MVC from the project Albert Image Downloader.
 * 
 * @author Alberto Casas Ortiz.
 */
public class Controller {
	
	/**
	 * Constructor of the class controller.
	 */
	public Controller(){
		Model model = new Model();
		@SuppressWarnings("unused")
		View view = new View(model);
	}
}
