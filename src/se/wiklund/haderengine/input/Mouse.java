package se.wiklund.haderengine.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import se.wiklund.haderengine.GameStateManager;

public class Mouse extends GLFWMouseButtonCallback {
	
	private GameStateManager gsm;
	
	public Mouse(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	@Override
    public void invoke(long windowID, int button, int action, int mods) {
        if (action == GLFW_RELEASE) {
        	gsm.onMouseUp(button);
        } else if (action == GLFW_PRESS) {
        	gsm.onMouseDown(button);
        }
    }
}
