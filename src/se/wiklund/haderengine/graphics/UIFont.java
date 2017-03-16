package se.wiklund.haderengine.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.util.Loader;

public class UIFont {

	private HashMap<Character, Texture> characters = new HashMap<>();
	private Font font;
	private Color color;
	private FontMetrics fontMetrics;
	private boolean antiAliased;

	public UIFont(String path, Color color, boolean antiAliased) {
		font = Loader.loadAWTFont(path);
		font = font.deriveFont(300f);

		BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempImage.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);

		this.color = color;
		fontMetrics = g.getFontMetrics();
		characters = new HashMap<Character, Texture>();
		this.antiAliased = antiAliased;
	}
	
	public void renderText(String text, float height, float x, float y) {
		float currentX = x;
		for (int i = 0; i < text.length(); i++) {
			Texture texture = getCharacterTexture(text.charAt(i));
			float width = texture.getWidth() * (height / texture.getHeight());
			Renderer.render(texture, currentX, y, width, height);
			currentX += width;
		}
	}

	public float getTextWidth(String text, float height) {
		float totalWidth = 0;
		for (int i = 0; i < text.length(); i++) {
			Texture texture = getCharacterTexture(text.charAt(i));
			totalWidth += texture.getWidth() * (height / texture.getHeight());
		}
		return totalWidth;
	}

	private Texture getCharacterTexture(char character) {
		if (characters.containsKey(character))
			return characters.get(character);

		int charwidth = fontMetrics.charWidth(character) + 8;

		if (charwidth <= 8) {
			System.err.println(Engine.NAME_PREFIX + "Character width of " + character + " is less than 1!");
			charwidth = 7;
		}
		int charheight = fontMetrics.getHeight();
		if (charheight <= 0) {
			System.err.println(Engine.NAME_PREFIX + "Character height of " + character + " is less than 1!");
			charheight = 16;
		}

		BufferedImage fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) fontImage.getGraphics();
		if (antiAliased)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(font);
		g.setColor(color);
		int charx = 3;
		int chary = 1;
		g.drawString(String.valueOf(character), (charx), (chary) + fontMetrics.getAscent());

		Texture texture = new Texture(fontImage);
		characters.put(character, texture);
		return texture;
	}
}
