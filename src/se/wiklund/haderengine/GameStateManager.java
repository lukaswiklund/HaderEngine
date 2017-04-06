package se.wiklund.haderengine;

import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.maths.Transform;

public class GameStateManager {

	private State state;
	private float offsetX, offsetY;

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

	public void setState(State state) {
		this.state = state;
	}
}
