package se.wiklund.haderengine;

import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;

public class Instance {
	
	private Texture texture;
	private Transform transform;
	
	public Instance(Texture texture, float x, float y) {
		this(texture, x, y, texture.getWidth(), texture.getHeight());
	}
	
	public Instance(Texture texture, float x, float y, int width, int height) {
		this.texture = texture;
		this.transform = new Transform(x, y, width, height);
	}
	
	public void update(double delta) {
		
	}
	
	public void render() {
		if (texture != null) {
			Renderer.render(this);
		}
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void move(float dx, float dy) {
		transform.move(dx, dy);
	}
	
	public Transform getTransform() {
		return transform;
	}
}
