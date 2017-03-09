package se.wiklund.haderengine.graphics;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.Main;
import se.wiklund.haderengine.maths.Transform;

public class Batch {
	
	private List<Instance> instances = new ArrayList<>();
	private BufferedImage image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_RGB);
	private Texture screen = new Texture(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Main.WIDTH, Main.HEIGHT);
	private Window window;
	
	protected void setWindow(Window window) {
		this.window = window;
	}
	
	public void render(Instance instance) {
		instances.add(instance);
	}
	
	public void renderToScreen() {
		BufferStrategy bs = window.getScreen().getBufferStrategy();
		if (bs == null) {
			window.getScreen().createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		screen.fill(0xFFFFFF);
		
		for (Instance instance : instances) {
			Texture texture = instance.getTexture();
			Transform transform = instance.getTransform();
			texture.render(screen, (int) transform.getX(), (int) transform.getY(), transform.getWidth(), transform.getHeight());
		}
		
		g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		
		bs.show();
		g.dispose();
		
		instances.clear();
	}
}
