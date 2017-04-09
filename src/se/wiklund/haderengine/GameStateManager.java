package se.wiklund.haderengine;

import org.lwjgl.glfw.GLFW;

import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.InputEnabledViews;
import se.wiklund.haderengine.ui.UIContextMenu;

public class GameStateManager {

	private State state;
	private float offsetX, offsetY;
	private float mouseDownOffsetX, mouseDownOffsetY;
	private float mouseUpOffsetX, mouseUpOffsetY;
	private Engine engine;
	private boolean altDown = false;
	
	public GameStateManager(Engine engine) {
		this.engine = engine;
	}
	
	public void update(float delta) {
		if (state != null)
			state.update(delta);
	}

	public void render() {
		if (state == null)
			return;

		offsetX = 0;
		offsetY = 0;
		for (View view : state.getSubviews()) {
			renderView(view);
		}
	}

	public void onKeyDown(int key) {
		if (key == GLFW.GLFW_KEY_LEFT_ALT) {
			altDown = true;
		}
		
		if (key == GLFW.GLFW_KEY_ENTER && altDown) {
			engine.window.setFullscreen(!engine.window.isFullscreen());
		}
		
		state.onKeyDown(key);
		for (View view : state.getSubviews()) {
			callOnKeyDown(view, key);
		}
	}

	public void onKeyUp(int key) {
		state.onKeyUp(key);
		for (View view : state.getSubviews()) {
			callOnKeyUp(view, key);
		}
	}

	public void onKeyRepeat(int key) {
		state.onKeyRepeat(key);
		for (View view : state.getSubviews()) {
			callOnKeyRepeat(view, key);
		}
	}

	public void onMouseDown(int button) {
		mouseDownOffsetX = 0;
		mouseDownOffsetY = 0;
		state.onMouseButtonDown(button);
		for (View view : state.getSubviews()) {
			callOnMouseDown(view, button);
		}
	}

	public void onMouseUp(int button) {
		mouseUpOffsetX = 0;
		mouseUpOffsetY = 0;
		state.onMouseButtonUp(button);
		for (View view : state.getSubviews()) {
			callOnMouseUp(view, button);
		}
	}

	private void renderView(View view) {
		if (view.isHidden())
			return;

		offsetX += view.getTransform().getX();
		offsetY += view.getTransform().getY();

		Transform t = view.getTransform();
		Renderer.render(view.getTexture(), offsetX, offsetY, t.getWidth(), t.getHeight());
		for (View subview : view.getSubviews()) {
			renderView(subview);
		}

		offsetX -= view.getTransform().getX();
		offsetY -= view.getTransform().getY();
	}

	private void callOnKeyDown(View view, int key) {
		if (view.isHidden())
			return;

		if (InputEnabledViews.isEnabled(view))
			view.onKeyDown(key);
		for (View subview : view.getSubviews()) {
			subview.onKeyDown(key);
		}
	}
	
	private void callOnKeyUp(View view, int key) {
		if (view.isHidden())
			return;

		if (InputEnabledViews.isEnabled(view))
			view.onKeyUp(key);
		for (View subview : view.getSubviews()) {
			subview.onKeyUp(key);
		}
	}
	
	private void callOnKeyRepeat(View view, int key) {
		if (view.isHidden())
			return;

		if (InputEnabledViews.isEnabled(view))
			view.onKeyRepeat(key);
		for (View subview : view.getSubviews()) {
			subview.onKeyRepeat(key);
		}
	}

	private void callOnMouseUp(View view, int button) {
		if (view.isHidden())
			return;

		if (InputEnabledViews.isEnabled(view)) {
			Transform cursor = Cursor.getTransform();
			Transform lclC = cursor.copy().move(-mouseUpOffsetX, -mouseUpOffsetY);
			if (lclC.intersects(view.getTransform())) {
				view.onMouseButtonUp(button);
			}
		}

		mouseUpOffsetX += view.getTransform().getX();
		mouseUpOffsetY += view.getTransform().getY();

		for (View subview : view.getSubviews()) {
			callOnMouseUp(subview, button);
		}

		mouseUpOffsetX -= view.getTransform().getX();
		mouseUpOffsetY -= view.getTransform().getY();

		if (button == GLFW.GLFW_MOUSE_BUTTON_1 && view instanceof UIContextMenu) {
			((UIContextMenu) view).hide();
		}
	}

	private void callOnMouseDown(View view, int button) {
		if (view.isHidden())
			return;

		if (InputEnabledViews.isEnabled(view)) {
			Transform cursor = Cursor.getTransform();
			Transform lclC = cursor.copy().move(-mouseDownOffsetX, -mouseDownOffsetY);
			if (lclC.intersects(view.getTransform())) {
				view.onMouseButtonDown(button);
			}
		}

		mouseDownOffsetX += view.getTransform().getX();
		mouseDownOffsetY += view.getTransform().getY();

		for (View subview : view.getSubviews()) {
			callOnMouseDown(subview, button);
		}

		mouseDownOffsetX -= view.getTransform().getX();
		mouseDownOffsetY -= view.getTransform().getY();
	}

	public void setState(State state) {
		this.state = state;
	}
}
