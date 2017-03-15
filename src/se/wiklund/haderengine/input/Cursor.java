package se.wiklund.haderengine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import se.wiklund.haderengine.maths.Transform;

public class Cursor extends GLFWCursorPosCallback {
	
	private static Transform transform = new Transform();
	
	@Override
	public void invoke(long windowID, double x, double y) {
		transform.setPosition((float) x, (float) y);
	}
	
	public static Transform getTransform() {
		return transform;
	}
}
