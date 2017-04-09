package se.wiklund.haderengine.ui;

import se.wiklund.haderengine.View;
import se.wiklund.haderengine.maths.Transform;

public class UILabel extends View {
	
	private float rawX, rawY;
	private String text;
	private UIFont font;
	private boolean center;

	public UILabel(String text, UIFont font, float fontSize, float x, float y, boolean center) {
		super(null, new Transform(x, y, 0, (int) fontSize));
		this.rawX = x;
		this.rawY = y;
		this.font = font;
		this.center = center;
		setText(text);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		getTransform().setWidth((int) font.getTextWidth(text, getTransform().getHeight()));
		if (center) {
			getTransform().setX(rawX - getTransform().getWidth() / 2);
			getTransform().setY(rawY - getTransform().getHeight() / 2);
		}
		
		setTexture(font.getTexture(text, getTransform().getHeight()));
	}
	
	//TODO: Override Transform, so you change it if center is enabled
}
