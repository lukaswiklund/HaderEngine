package se.wiklund.haderengine;

import se.wiklund.haderengine.maths.Transform;

public abstract class State extends View {
	
	public State() {
		super(null, new Transform(0, 0, Engine.WIDTH, Engine.HEIGHT));
	}

	public abstract void update(float delta);
}
