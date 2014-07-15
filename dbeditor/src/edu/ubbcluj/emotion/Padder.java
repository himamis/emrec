package edu.ubbcluj.emotion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class Padder {
	
	private int height;
	private int width;
	
	public Padder(int width, int height) {
		this.height = height;
		this.width = width;
	}
	
	public void padImages(List<BufferedImage> images) {
		for (int i = 0; i < images.size(); i++) {
			BufferedImage image = images.get(i);
			BufferedImage paddedImage = new BufferedImage(width, height, image.getType());
			Graphics g = paddedImage.getGraphics();
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
			g.drawImage(image, 0, 0, null);
			g.dispose();
			images.set(i, paddedImage);
		}
	}

}
