package se.wiklund.haderengine;

import java.awt.Dimension;
import java.awt.Toolkit;

import se.wiklund.haderengine.graphics.Window;

public class Main {
	
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int UPS = 60;
	
	public static Window window;
	private boolean running;
	private int fps, ups;
	
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
		
		while (running) {
			long now = System.nanoTime();
			delta += now - lastTime;
			lastTime = now;
			
			while (delta >= UPDATE_INTERVAL) {
				delta -= UPDATE_INTERVAL;
				
				updates++;
			}
			
			window.render();
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
	
	private void stop() {
		System.exit(0);
	}
	
	public static void main(String[] args) {
		window = new Window("HaderEngine", false);
		
		new Thread(() -> new Main().start()).start();
	}
}
