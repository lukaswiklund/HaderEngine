package se.wiklund.haderengine;

public class Bounds {
	
	public float x, y;
	public float width, height;
	
	public Bounds() {
		x = 0.0f;
		y = 0.0f;
		width = 0.0f;
		height = 0.0f;
	}

	public Bounds(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
