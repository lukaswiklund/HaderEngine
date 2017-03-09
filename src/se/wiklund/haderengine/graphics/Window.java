package se.wiklund.haderengine.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import se.wiklund.haderengine.Main;

public class Window {
	
	private String title, suffix;
	private boolean fullscreen;
	private JFrame frame;
	private Canvas screen;
	
	public Window(String title, boolean fullscreen) {
		this.title = title;
		this.fullscreen = fullscreen;
		
		createWindow();
	}
	
	private void createWindow() {
		frame = new JFrame();
		screen = new Canvas();
		
		if (fullscreen) {
			screen.setSize(Main.SCREEN_SIZE);
			frame.setUndecorated(true);
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
	}
	
	public void render() {
		BufferStrategy bs = screen.getBufferStrategy();
		if (bs == null) {
			screen.createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		bs.show();
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
}
