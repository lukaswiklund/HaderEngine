package se.wiklund.haderengine.graphics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.maths.Transform;

public class Animator {

	private List<AnimationObject> animationObjects = new CopyOnWriteArrayList<>();
	
	public void animate(Instance instance, Transform target, float seconds) {
		AnimationObject object = new AnimationObject(instance, target, seconds);
		animationObjects.add(object);
	}
	
	public void animate(Instance instance, float x, float y, int width, int height, float seconds) {
		AnimationObject object = new AnimationObject(instance, new Transform(x, y, width, height), seconds);
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

		private final Instance INSTANCE;
		private final Transform TARGET;
		private final float SECONDS;
		private final float X_PER_SECOND, Y_PER_SECOND, WIDTH_PER_SECOND, HEIGHT_PER_SECOND;

		private float elapsed;

		public AnimationObject(Instance instance, Transform target, float seconds) {
			this.INSTANCE = instance;
			this.TARGET = target;
			this.SECONDS = seconds;

			Transform transform = instance.getTransform();
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
			
			INSTANCE.getTransform().move((float) (X_PER_SECOND * delta), (float) (Y_PER_SECOND * delta));
			INSTANCE.getTransform().setWidth((int) (INSTANCE.getTransform().getWidth() + WIDTH_PER_SECOND * delta));
			INSTANCE.getTransform().setHeight((int) (INSTANCE.getTransform().getHeight() + HEIGHT_PER_SECOND * delta));
		}
		
		public void setToTarget() {
			INSTANCE.getTransform().set(TARGET.getX(), TARGET.getY(), TARGET.getWidth(), TARGET.getHeight());
		}
	}
}
