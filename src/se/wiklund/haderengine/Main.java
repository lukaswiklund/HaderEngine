package se.wiklund.haderengine;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.sun.glass.events.KeyEvent;

import se.wiklund.haderengine.graphics.Batch;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.graphics.Window;
import se.wiklund.haderengine.input.Keyboard;

public class Main {
	
	public static final int WIDTH = 1920 / 2, HEIGHT = 1080 / 2;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int UPS = 60;
	
	public static Batch batch;
	public static Window window;
	private boolean running;
	private int fps, ups;
	private Instance instance;
	
	public void start() {
		if (running) {
			System.err.println("Tried to start the game while running!");
			return;
		}
		running = true;
		
		final double UPDATE_INTERVAL = 1000000000.0 / UPS;
		long lastTime = System.nanoTime();
		long timer = System.nanoTime();
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		Texture texture = new Texture(100, 100);
		instance = new Instance(texture, 200, 200);
		
		while (running) {
			long now = System.nanoTime();
			delta += now - lastTime;
			lastTime = now;
			
			while (delta >= UPDATE_INTERVAL) {
				delta -= UPDATE_INTERVAL;
				
				update(UPDATE_INTERVAL);
				updates++;
			}
			
			render();
			frames++;
			
			if (timer + 1000000000 <= System.nanoTime()) {
				timer += 1000000000;
				fps = frames;
				ups = updates;
				frames = 0;
				updates = 0;
				
				window.setTitleSuffix("UPS: " + ups + ", FPS: " + fps);
			}
		}
		
		stop();
	}
	
	private void update(double delta) {
		if (Keyboard.isKeyDown(KeyEvent.VK_ALT) && Keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
			window.setFullscreen(!window.isFullscreen());
		}
	}
	
	private void render() {
		batch.render(instance);
		
		batch.renderToScreen();
	}
	
	private void stop() {
		System.exit(0);
	}
	
	public static void main(String[] args) {
		batch = new Batch();
		window = new Window("HaderEngine", false, batch);
		
		new Thread(() -> new Main().start()).start();
	}
}
