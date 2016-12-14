package main;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {
	private static String URL = "";
	private static String imageFormat = "";

	public static void main(String[] args) {
		URL = "http://www.flaticon.es";
		imageFormat = "png";
		getImages();
	}

	public static void getImages() {

		Document doc = null;
		try {
			doc = Jsoup.connect(URL).userAgent("Mozilla").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Elements images = doc.select("img[src$=." + imageFormat + "]");

		RenderedImage image = null;
		File file = new File("images//");
		file.mkdirs();
		try {
			for (int i = 0; i < images.size(); i++) {
				URL url = new URL(images.get(i).absUrl("src"));
				System.out.println("Image " + i);
				image = ImageIO.read(url);
				String[] splitted = url.getPath().split("/");
				String imageName = splitted[splitted.length-1];
				file = new File("images//" + imageName);
				file.createNewFile();
				ImageIO.write(image, imageFormat, file);
			}
		} catch (IOException e) {
			// TODO
		}

	}

}
