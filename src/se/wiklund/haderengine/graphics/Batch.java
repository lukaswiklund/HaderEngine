package se.wiklund.haderengine.graphics;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.Main;
import se.wiklund.haderengine.maths.Transform;

public class Batch {
	
	private BufferedImage image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
	private Texture screen = new Texture(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Main.WIDTH, Main.HEIGHT);
	private Window window;
	
	protected void setWindow(Window window) {
		this.window = window;
	}
	
	public void render(Instance instance) {
		Texture texture = instance.getTexture();
		Transform transform = instance.getTransform();
		render(texture, transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight());
	}
	
	public void render(Texture texture, float x, float y) {
		render(texture, x, y, texture.getWidth(), texture.getHeight());
	}
	
	public void render(Texture texture, float x, float y, int width, int height) {
		texture.render(screen, (int) x, (int) y, width, height);
	}
	
	public void renderToScreen() {
		BufferStrategy bs = window.getScreen().getBufferStrategy();
		if (bs == null) {
			window.getScreen().createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		
		bs.show();
		g.dispose();
		
		screen.fill(0xFFFFFF);
	}
}
