package se.wiklund.haderengine.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import se.wiklund.haderengine.Engine;

public class Keyboard extends GLFWKeyCallback {
	
	public static boolean[] keys = new boolean[1024];
	public static boolean[] last = new boolean[1024];
	
	@Override
	public void invoke(long windowID, int key, int scancode, int action, int mods) {
		if (key >= keys.length) {
			System.err.println(Engine.NAME_PREFIX + "Key index out of bounds: " + key + "!");
			return;
		}
		
		if (action != GLFW_RELEASE) {
			keys[key] = true;
		} else {
			keys[key] = false;
			last[key] = false;
		}
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
