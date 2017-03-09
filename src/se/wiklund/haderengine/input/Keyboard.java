package se.wiklund.haderengine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	public static boolean[] keys = new boolean[1024];
	public static boolean[] last = new boolean[1024];
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		last[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
	
	public static boolean isKeyPressed(int keycode) {
		if (keys[keycode] && !last[keycode]) {
			last[keycode] = true;
			return true;
		}
		return false;
	}
}
