package main;

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

public class Main {
	private static String imageFormats[] = {"ANI", "BMP", "CAL", "FAX", "GIF", "IMG", "JBG", "JPE", "JPEG", "JPG", "MAC", "PBM", "PCD", "PCX", "PCT", "PGM", "PNG", "PPM", "PSD", "RAS", "TGA", "TIFF", "WMF"};
	
	public static void main(String[] args) {
		//Insert URL here.
		String URL = "";
		
		//Select which image formats want to download.
		ArrayList<String> imageFormat = new ArrayList<String>();
		imageFormat.add("png");
		imageFormat.add("jpg");
		imageFormat.add("jpeg");
		
		//Min width and minHeight for images.
		int minWidth = 200;
		int minHeight = 200;
		
		//Call function to get images.
		try {
			saveImages(URL, imageFormat, minWidth, minHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save images from an URL with determined formats and minimum size.
	 * @param URL URL from where images will be downloaded.
	 * @param imageFormats Formats of the downloaded images.
	 * @param minWidth Min width of the downloaded images.
	 * @param minHeight Min height of the downloaded images.
	 * @throws IOException
	 */
	public static void saveImages(String URL, ArrayList<String> imageFormats, int minWidth, int minHeight) throws IOException {
		//Get HTML from URL.
		Document doc = connectAndGetHTML(URL);
		
		//Get URLs of images in HTML code.
		ArrayList<URL> imagesURLs = selectImagesFromDocument(doc, imageFormats);

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
			if(saveConditions(image, minWidth, minHeight)){
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
	 * @param imageFormats Array with formats of images which we want.
	 * @return ArrayList with URLs.
	 * @throws MalformedURLException 
	 */
	public static ArrayList<URL> selectImagesFromDocument(Document doc, ArrayList<String> imageFormats) throws MalformedURLException{
		ArrayList<URL> urls = new ArrayList<URL>();
		Elements images = new Elements();
		
		//Get images from all image formats.
		for(int i = 0; i < imageFormats.size(); i++){
			if(Arrays.asList(Main.imageFormats).contains(imageFormats.get(i).toUpperCase()))
				images.addAll(doc.select("img[src$=." + imageFormats.get(i) + "]"));
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
	 * @param minWidth Min width for the image.
	 * @param minHeight Max width for the image.
	 * @return Return true if the image satisfy the condition.
	 */
	private static boolean saveConditions(RenderedImage image, int minWidth, int minHeight){
		boolean save = false;
		if(image.getWidth() >= minWidth && image.getHeight() >= minHeight){
			save = true;
		}
		return save;
	}
}
