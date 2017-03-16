package se.wiklund.haderengine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import se.wiklund.haderengine.graphics.Window;
import se.wiklund.haderengine.input.Keyboard;

public class Engine {
	
	public static final int WIDTH = 1920, HEIGHT = 1080;
	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	public static final String NAME = "HaderEngine";
	public static final String NAME_PREFIX = "[" + NAME + "] ";
	
	public Window window;
	private GameStateManager gsm;
	
	public Engine(String title, boolean fullscreen, boolean vSync) {
		if (!glfwInit()) {
			System.err.println(NAME_PREFIX + "Failed to initialize GLFW!");
			exit(-1);
			return;
		}
		window = new Window(title, fullscreen, vSync);
		gsm = new GameStateManager();
	}
	
	public void start() {
		long lastTime = System.nanoTime();
		
		while (!window.isCloseRequested()) {
			long now = System.nanoTime();
			double delta = (now - lastTime) / 1000000000D;
			lastTime = now;
			
			update(delta);
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			render();
			
			window.repaint();
		}
		
		exit();
	}
	
	private void update(double delta) {
		if (Keyboard.isKeyDown(GLFW_KEY_LEFT_ALT) && Keyboard.isKeyPressed(GLFW_KEY_ENTER)) {
			window.setFullscreen(!window.isFullscreen());
		}
		
		gsm.update(delta);
	}
	
	private void render() {
		gsm.render();
	}
	
	public void exit() {
		exit(0);
	}
	
	public void exit(int code) {
		if (window != null)
			window.close();
		glfwTerminate();
		if (code != 0)
			System.out.println(NAME_PREFIX + "Stopped with exit code " + code + "!");
		System.exit(code);
	}
	
	public void setState(State state) {
		gsm.setState(state);
	}
	
	public Window getWindow() {
		return window;
	}
	
	public static void main(String[] args) {
	}
}
