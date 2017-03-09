package se.wiklund.haderengine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.graphics.Window;
import se.wiklund.haderengine.input.Keyboard;

public class Main {
	
	public static final int WIDTH = 1920 / 2, HEIGHT = 1080 / 2;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static Window window;
	private static Instance instance;
	
	public static void start() {
		instance = new Instance(new Texture("/bird.png"), 0, 0);
		
		long lastTime = System.nanoTime();
		
		while (!window.isCloseRequested()) {
			long now = System.nanoTime();
			double delta = now - lastTime;
			lastTime = now;
			
			update(delta);
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			render();
			
			window.repaint();
		}
		
		exit();
	}
	
	private static void update(double delta) {
		if (Keyboard.isKeyDown(GLFW_KEY_LEFT_ALT) && Keyboard.isKeyPressed(GLFW_KEY_ENTER)) {
			window.setFullscreen(!window.isFullscreen());
		}
	}
	
	private static void render() {
		Renderer.render(instance);
	}
	
	public static void exit() {
		exit(0);
	}
	
	public static void exit(int code) {
		if (window != null)
			window.close();
		glfwTerminate();
		if (code != 0)
			System.out.println("Stopped with exit code " + code + "!");
		System.exit(code);
	}
	
	public static void main(String[] args) {
		if (!glfwInit()) {
			System.err.println("Failed to initialize GLFW!");
			exit(-1);
			return;
		}
		window = new Window("HaderEngine", false);
		
		start();
	}
}
