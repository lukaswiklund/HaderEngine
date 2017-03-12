package se.wiklund.haderengine.maths;

public class Transform {
	
	private float x, y;
	private int width, height;
	
	public Transform() {
		x = 0.0f;
		y = 0.0f;
		width = 0;
		height = 0;
	}
	
	public Transform(float x, float y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void move(float dx, float dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean intersects(Transform transform) {
		return (x >= transform.x && x <= transform.x + transform.width && y >= transform.y && y <= transform.y + transform.height);
	}
}
