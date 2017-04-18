package se.wiklund.haderengine;

import java.util.ArrayList;

import com.sun.istack.internal.NotNull;

import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;

public class View {
	
	private ArrayList<View> subviews = new ArrayList<>();
	
	private Texture texture;
	private Transform transform;
	private boolean hidden;
	
	public View(@NotNull Texture texture, float x, float y) {
		this(texture, new Transform(x, y, texture.getWidth(), texture.getHeight()));
	}
	
	public View(Texture texture, Transform transform) {
		this.texture = texture;
		this.transform = transform;
	}
	
	public void update(float delta) {
		
	}
	
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
	
	public Texture getTexture() {
		return texture;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setTexture(Texture texture, boolean updateSize) {
		this.texture = texture;
		if (updateSize)
			transform.setSize(texture.getWidth(), texture.getHeight());
	}
	
	public void setTransform(Transform transform) {
		this.transform = transform;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
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
