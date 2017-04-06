package se.wiklund.haderengine.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import se.wiklund.haderengine.graphics.Texture;

public class TextureCreator {

	private BufferedImage image;

	public TextureCreator(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public void addTexture(Texture texture, int x, int y) {
		BufferedImage sub = convertTextureToImage(texture);
		image.getGraphics().drawImage(sub, x, y, null);
	}

	public void addTexture(Texture texture, int x, int y, int width, int height) {
		if (width == texture.getWidth() && height == texture.getHeight()) {
			addTexture(texture, x, y);
			return;
		}
		
		BufferedImage sub = convertTextureToImage(texture);
		image.getGraphics().drawImage(sub, x, y, width, height, null);
	}
	
	private BufferedImage convertTextureToImage(Texture texture) {
		int width = texture.getWidth();
		int height = texture.getHeight();
		int[] pixels = texture.getRawData();
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int color = pixels[xa + ya * width];
				if (color == 0) continue;
				g.setColor(new Color(color));
				g.drawRect(xa, ya, 1, 1);
			}
		}
		return result;
	}

	public Texture getTexture() {
		return new Texture(image);
	}
}
