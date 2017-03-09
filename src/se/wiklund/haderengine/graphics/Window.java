package se.wiklund.haderengine.graphics;

import java.awt.Canvas;

import javax.swing.JFrame;

import se.wiklund.haderengine.Main;
import se.wiklund.haderengine.input.Keyboard;

public class Window {
	
	private String title, suffix;
	private boolean fullscreen;
	private JFrame frame;
	private Canvas screen;
	
	public Window(String title, boolean fullscreen, Batch batch) {
		this.title = title;
		this.fullscreen = fullscreen;
		
		createWindow();
		batch.setWindow(this);
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		frame.dispose();
		createWindow();
	}
	
	private void createWindow() {
		frame = new JFrame();
		screen = new Canvas();
		
		if (fullscreen) {
			frame.setSize(Main.SCREEN_SIZE);
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			int width = Main.SCREEN_SIZE.width / 2;
			screen.setSize(width, width / 16 * 9);
		}
		
		setTitle(title);
		frame.add(screen);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		frame.requestFocus();
		screen.requestFocusInWindow();
		
		screen.addKeyListener(new Keyboard());
	}
	
	public void setTitle(String title) {
		this.title = title;
		if (suffix == null || suffix.trim().equalsIgnoreCase(""))
			frame.setTitle(title);
		else
			frame.setTitle(title + " | " + suffix);
	}
	
	public void setTitleSuffix(String suffix) {
		this.suffix = suffix;
		setTitle(title);
	}
	
	public Canvas getScreen() {
		return screen;
	}
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
}
