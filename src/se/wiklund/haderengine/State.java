package se.wiklund.haderengine;

public abstract class State {
	
	public abstract void update(double delta);
	public abstract void render();
}
