package se.wiklund.haderengine.input;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWScrollCallback;

public class Scroll extends GLFWScrollCallback {
	
	private static List<ScrollListener> listeners = new ArrayList<>();
	
	@Override
	public void invoke(long windowID, double xOffset, double yOffset) {
		for (ScrollListener listener : listeners) {
			listener.scroll((int) yOffset);
		}
	}
	
	public static void addScrollListener(ScrollListener listener) {
		if (listeners.contains(listener)) {
			System.err.println("Tried to add a ScrollListener that already exists!");
			return;
		}
		listeners.add(listener);
	}
	
	public static void removeScrollListener(ScrollListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println("Tried to remove a ScrollListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}
}
