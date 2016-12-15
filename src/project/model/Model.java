package project.model;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Model {
	public static String imageFormatList[] = {"ANI", "BMP", "CAL", "FAX", "GIF", "IMG", "JBG", "JPE", "JPEG", "JPG", "MAC", "PBM", "PCD", "PCX", "PCT", "PGM", "PNG", "PPM", "PSD", "RAS", "TGA", "TIFF", "WMF"};
	private String URL;
	private ArrayList<String> imageFormats;
	private int minWidth;
	private int minHeight;
	
	/**
	 * Constructor for class Image Saver..
	 */
	public Model() {
		this.URL = "";
		this.imageFormats = new ArrayList<String>();
		this.minWidth = 100;
		this.minHeight = 100;
	}

	public void setValues(String URL, ArrayList<String> imageFormats, int minWidth, int minHeight) {
		this.URL = URL;
		this.imageFormats = imageFormats;
		this.minWidth = minWidth;
		this.minHeight = minHeight;
	}
	
	/**
	 * Save images from an URL with determined formats and minimum size.
	 * 
	 * @throws IOException
	 */
	public void saveImages() throws IOException {
		//Get HTML from URL.
		Document doc = connectAndGetHTML(this.URL);
		
		//Get URLs of images in HTML code.
		ArrayList<URL> imagesURLs = selectImagesFromDocument(doc);

		//Create file which will contain the images.
		File file = new File("images//");
		file.mkdirs();
		
		//Download images.
		RenderedImage image = null;
		for (int i = 0; i < imagesURLs.size(); i++) {
			URL url = imagesURLs.get(i);
			//Read image.
			image = ImageIO.read(url);
			//Get name of the image.
			String[] splitted = url.getPath().split("/");
			String imageName = splitted[splitted.length-1];
			//Get format of the image.
			splitted = imageName.split("\\.");
			String imageFormat = splitted[splitted.length-1];
			//Save image if the image satisfy conditions.
			if(this.saveConditions(image)){
				//Create name of the image.
				file = new File("images//" + imageName);
				file.createNewFile();
				//Save image.
				ImageIO.write(image, imageFormat, file);
				
			}
		}

	}

	/**
	 * Connect to URL and get HTML in a document.
	 * @return Document with HTML.
	 * @throws IOException
	 */
	public static Document connectAndGetHTML(String URL) throws IOException{
		return Jsoup.connect(URL).userAgent("Mozilla").get();
	}
	
	/**
	 * Select all URLs of images an save it in an array.
	 * @param doc Document with HTML.
	 * @return ArrayList with URLs.
	 * @throws MalformedURLException 
	 */
	public ArrayList<URL> selectImagesFromDocument(Document doc) throws MalformedURLException{
		ArrayList<URL> urls = new ArrayList<URL>();
		Elements images = new Elements();
		
		//Get images from all image formats.
		for(int i = 0; i < this.imageFormats.size(); i++){
			if(Arrays.asList(Model.imageFormatList).contains(this.imageFormats.get(i).toUpperCase()))
				images.addAll(doc.select("img[src$=." + this.imageFormats.get(i) + "]"));
		}
		
		//Get URLs of the images.
		for(int i = 0; i < images.size(); i++){
			urls.add(new URL(images.get(i).absUrl("src")));
		}
		
		return urls;
	}
	
	/**
	 * Conditions to save the image.
	 * @param image Image to be tested.
	 * @return Return true if the image satisfy the condition.
	 */
	private boolean saveConditions(RenderedImage image){
		boolean save = false;
		if(image.getWidth() >= this.minWidth && image.getHeight() >= this.minHeight){
			save = true;
		}
		return save;
	}
}
