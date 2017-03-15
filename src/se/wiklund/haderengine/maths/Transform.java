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
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void set(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
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
		int tw = this.width;
        int th = this.height;
        int rw = transform.width;
        int rh = transform.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = (int) this.x;
        int ty = (int) this.y;
        int rx = (int) transform.x;
        int ry = (int) transform.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + width + ", " + height + ")";
	}
}
