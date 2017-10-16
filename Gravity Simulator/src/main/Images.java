package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Images {
	
	public static BufferedImage background;
	
	public static void loadImages(){
		try{
			background = ImageIO.read(Images.class.getResourceAsStream("/background.png"));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Images could not be loaded");
		}
	}
	
}
