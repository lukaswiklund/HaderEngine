package se.wiklund.haderengine.input;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.glfw.GLFWScrollCallback;

import se.wiklund.haderengine.Engine;

public class Scroll extends GLFWScrollCallback {
	
	private static List<ScrollListener> listeners = new CopyOnWriteArrayList<>();
	
	@Override
	public void invoke(long windowID, double xOffset, double yOffset) {
		for (ScrollListener listener : listeners) {
			listener.scroll((int) yOffset);
		}
	}
	
	public static void addScrollListener(ScrollListener listener) {
		if (listeners.contains(listener)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to add a ScrollListener that already exists!");
			return;
		}
		listeners.add(listener);
	}
	
	public static void removeScrollListener(ScrollListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to remove a ScrollListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}
}
