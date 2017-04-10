package se.wiklund.haderengine.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;

import se.wiklund.haderengine.util.Loader;

public class Texture {

	private int textureID;
	private int width, height;
	private int[] rawData, data;

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

		int a = (color & 0xff000000) >> 24;
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0xff00) >> 8;
		int b = (color & 0xff);

		int pixel = a << 24 | b << 16 | g << 8 | r;

		rawData = new int[] { pixel };
		data = rawData.clone();

		registerTexture(data);
	}

	public Texture(int color, int width, int height) {
		this.width = width;
		this.height = height;

		rawData = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				rawData[x + y * width] = color;
			}
		}
		
		data = new int[width * height];
		for (int i = 0; i < rawData.length; i++) {
			int a = (color & 0xff000000) >> 24;
			int r = (color & 0xff0000) >> 16;
			int g = (color & 0xff00) >> 8;
			int b = (color & 0xff);

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		registerTexture(data);
	}

	private void createTexture(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		rawData = new int[width * height];
		image.getRGB(0, 0, width, height, rawData, 0, width);

		data = new int[width * height];
		for (int i = 0; i < rawData.length; i++) {
			int a = (rawData[i] & 0xff000000) >> 24;
			int r = (rawData[i] & 0xff0000) >> 16;
			int g = (rawData[i] & 0xff00) >> 8;
			int b = (rawData[i] & 0xff);

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		registerTexture(data);
	}

	private void registerTexture(int[] data) {
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
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

	public int[] getRawData() {
		return rawData;
	}

	public int[] getData() {
		return data;
	}
}
