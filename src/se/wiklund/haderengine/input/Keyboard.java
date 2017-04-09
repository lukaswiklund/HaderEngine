package se.wiklund.haderengine.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import se.wiklund.haderengine.GameStateManager;

public class Keyboard extends GLFWKeyCallback {
	
	private GameStateManager gsm;
	
	public Keyboard(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	@Override
	public void invoke(long windowID, int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS) {
			gsm.onKeyDown(key);
		} else if (action == GLFW_RELEASE) {
			gsm.onKeyUp(key);
		} else if (action == GLFW_REPEAT) {
			gsm.onKeyRepeat(key);
		}
	}
}
