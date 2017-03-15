package se.wiklund.haderengine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.graphics.Window;
import se.wiklund.haderengine.maths.Transform;

public class Cursor extends GLFWCursorPosCallback {
	
	private static Transform transform = new Transform(0, 0, 1, 1);
	private static Window window;
	
	public Cursor(Window window) {
		Cursor.window = window;
	}
	
	@Override
	public void invoke(long windowID, double x, double y) {
		int wWidth = window.getWidth();
		int wHeight = window.getHeight();
		if (wWidth <= 0 || wHeight <= 0) return;
		float aspectX = (float) Engine.SCREEN_SIZE.width / window.getWidth();
		float aspectY = (float) Engine.SCREEN_SIZE.height / window.getHeight();
		
		float xt = (float) x * aspectX;
		float yt = (float) Math.abs(Engine.HEIGHT - (y * aspectY));
		transform.setPosition(xt, yt);
	}

	public static Transform getTransform() {
		return transform;
	}
}
