package se.wiklund.haderengine.ui.style;

import se.wiklund.haderengine.graphics.Texture;

public class UIProgressBarStyle {

	private Texture frameTexture, backgroundTexture, foregroundTexture;
	private int frameSizeX, frameSizeY;
	
	public UIProgressBarStyle(int backgroundColor, int foregroundColor, int frameColor, int frameSizeX, int frameSizeY) {
		if (frameSizeX > 0 || frameSizeY > 0) {
			frameTexture = new Texture(frameColor);
		}
		backgroundTexture = new Texture(backgroundColor);
		foregroundTexture = new Texture(foregroundColor);
		this.frameSizeX = frameSizeX;
		this.frameSizeY = frameSizeY;
	}
	
	public Texture getFrameTexture() {
		return frameTexture;
	}
	
	public Texture getBackgroundTexture() {
		return backgroundTexture;
	}
	
	public Texture getForegroundTexture() {
		return foregroundTexture;
	}
	
	public int getFrameSizeX() {
		return frameSizeX;
	}
	
	public int getFrameSizeY() {
		return frameSizeY;
	}
}
