package se.wiklund.haderengine.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import se.wiklund.haderengine.util.Loader;

public class Texture {

	private int textureID;
	private int width, height;

	public Texture(String path) {
		BufferedImage image = Loader.loadImage(path);
		createTexture(image);
	}

	public Texture(BufferedImage image) {
		createTexture(image);
	}

	public Texture(int color) {
		this.width = 1;
		this.height = 1;
		int[] pixels = new int[] { color };
		
		registerTexture(pixels);
	}

	private void createTexture(BufferedImage image) {
		int[] rawPixels = null;
		width = image.getWidth();
		height = image.getHeight();
		rawPixels = new int[width * height];
		image.getRGB(0, 0, width, height, rawPixels, 0, width);

		int[] pixels = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			int a = (rawPixels[i] & 0xff000000) >> 24;
			int r = (rawPixels[i] & 0xff0000) >> 16;
			int g = (rawPixels[i] & 0xff00) >> 8;
			int b = (rawPixels[i] & 0xff);

			pixels[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		registerTexture(pixels);
	}
	
	private void registerTexture(int[] pixels) {
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureID);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
