package project.controller;

import project.model.Model;
import project.view.View;

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
