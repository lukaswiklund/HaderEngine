package se.wiklund.haderengine;

import java.util.ArrayList;

public abstract class State {
	
	private ArrayList<View> subviews = new ArrayList<>();
	
	public abstract void update(float delta);
	
	public void addSubview(View view) {
		if (subviews.contains(view)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to add a subview that is already added!");
			return;
		}
		subviews.add(view);
	}
	
	public void removeSubview(View view) {
		if (!subviews.contains(view)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to remove a subview that isn't added!");
			return;
		}
		subviews.remove(view);
	}
	
	protected ArrayList<View> getSubviews() {
		return subviews;
	}
	
	public void onKeyDown(int key) {
		
	}
	
	public void onKeyUp(int key) {
		
	}
	
	public void onKeyRepeat(int key) {
		
	}
	
	public void onMouseButtonDown(int button) {
		
	}

	public void onMouseButtonUp(int button) {
		
	}
}
