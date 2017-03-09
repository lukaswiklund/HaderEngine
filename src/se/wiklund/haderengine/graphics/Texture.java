package se.wiklund.haderengine.graphics;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import se.wiklund.haderengine.util.Loader;

public class Texture {

	private int width, height;
	private int[] pixels;

	public Texture(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Texture(String path) {
		BufferedImage image = Loader.loadImage(path);
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}

	public void draw(int color, int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		pixels[x + y * width] = color;
	}
	
	public void fillRect(int color, int x, int y, int width, int height) {
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				draw(color, x + xa, y + ya);
			}
		}
	}

	public void fill(int color) {
		Arrays.fill(pixels, color);
	}

	public int getPixel(int x, int y) {
		return pixels[x + y * width];
	}
}
