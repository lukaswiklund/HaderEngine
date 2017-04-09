package se.wiklund.haderengine.graphics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.View;
import se.wiklund.haderengine.maths.Transform;

public class Animator {

	private List<AnimationObject> animationObjects = new CopyOnWriteArrayList<>();

	public void animate(View view, Transform target, float seconds) {
		if (target.getX() == Float.MAX_VALUE)
			target.setX(view.getTransform().getX());
		if (target.getY() == Float.MAX_VALUE)
			target.setY(view.getTransform().getY());
		if (target.getWidth() == Integer.MAX_VALUE)
			target.setWidth(view.getTransform().getWidth());
		if (target.getHeight() == Integer.MAX_VALUE)
			target.setHeight(view.getTransform().getHeight());
		AnimationObject object = new AnimationObject(view, target, seconds);
		animationObjects.add(object);
	}

	public void animate(View view, float x, float y, int width, int height, float seconds) {
		AnimationObject object = new AnimationObject(view, new Transform(x, y, width, height), seconds);
		animationObjects.add(object);
	}

	public void animateAll(double delta) {
		for (AnimationObject object : animationObjects) {
			object.update(delta);
		}
	}

	public void cancelAll(boolean setAllToTarget) {
		if (setAllToTarget) {
			for (AnimationObject object : animationObjects) {
				object.setToTarget();
			}
		}
		animationObjects.clear();
	}

	private class AnimationObject {

		private final View VIEW;
		private final Transform TARGET;
		private final float SECONDS;
		private final float X_PER_SECOND, Y_PER_SECOND, WIDTH_PER_SECOND, HEIGHT_PER_SECOND;

		private float elapsed;

		public AnimationObject(View view, Transform target, float seconds) {
			this.VIEW = view;
			this.TARGET = target;
			this.SECONDS = seconds;

			Transform transform = view.getTransform();
			X_PER_SECOND = (target.getX() - transform.getX()) / seconds;
			Y_PER_SECOND = (target.getY() - transform.getY()) / seconds;
			WIDTH_PER_SECOND = (target.getWidth() - transform.getWidth()) / 2;
			HEIGHT_PER_SECOND = (target.getHeight() - transform.getHeight()) / 2;
		}

		public void update(double delta) {
			elapsed += delta;
			if (elapsed >= SECONDS) {
				setToTarget();
				animationObjects.remove(this);
				return;
			}

			VIEW.getTransform().move((float) (X_PER_SECOND * delta), (float) (Y_PER_SECOND * delta));
			VIEW.getTransform().setWidth((int) (VIEW.getTransform().getWidth() + WIDTH_PER_SECOND * delta));
			VIEW.getTransform().setHeight((int) (VIEW.getTransform().getHeight() + HEIGHT_PER_SECOND * delta));
		}

		public void setToTarget() {
			VIEW.getTransform().set(TARGET.getX(), TARGET.getY(), TARGET.getWidth(), TARGET.getHeight());
		}
	}
}
