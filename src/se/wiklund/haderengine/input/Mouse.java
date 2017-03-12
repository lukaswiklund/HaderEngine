package se.wiklund.haderengine.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Mouse extends GLFWMouseButtonCallback {
	
	private static List<MouseButtonListener> listeners = new ArrayList<>();
	
	@Override
	public void invoke(long windowID, int button, int action, int mods) {
		if (action == GLFW_RELEASE) {
			for (MouseButtonListener listener : listeners) {
				listener.onMouseButtonUp(button);
			}
		} else if (action == GLFW_PRESS){
			for (MouseButtonListener listener : listeners) {
				listener.onMouseButtonDown(button);
			}
		}
	}
	
	public static void addMouseButtonListener(MouseButtonListener listener) {
		listeners.add(listener);
	}
	
	public static void removeMouseButtonListener(MouseButtonListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println("Tried to remove a MouseButtonListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}
}
