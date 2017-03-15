package se.wiklund.haderengine.ui;

import java.util.ArrayList;
import java.util.List;

import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.graphics.UIFont;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.MouseButtonListener;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

public class UIButton extends UIComponent implements MouseButtonListener {
	
	private List<UIButtonListener> listeners = new ArrayList<>();
	
	private String text;
	private UIFont font;
	private float fontSize;
	private float textWidth;
	private float textOffsetX, textOffsetY;
	
	public UIButton(String text, UIFont font, float fontSize, Texture background, float x, float y, int width, int height) {
		super(background, x, y, width, height);
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
		this.textWidth = font.getTextWidth(text, fontSize);
		this.textOffsetX = (width - textWidth) / 2;
		this.textOffsetY = (height - fontSize) / 2;
		
		Mouse.addMouseButtonListener(this);
	}
	
	@Override
	public void render() {
		super.render();
		font.renderText(text, getTransform().getX() + textOffsetX, getTransform().getY() + textOffsetY, fontSize);
	}
	
	public void addButtonListener(UIButtonListener listener) {
		if (listeners.contains(listener)) {
			System.err.println("Tried to add a UIButtonListener that already exists!");
			return;
		}
		listeners.add(listener);
	}
	
	public void removeButtonListener(UIButtonListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println("Tried to remove a UIButtonListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}

	@Override
	public void onMouseButtonDown(int button) {
		if (Cursor.getTransform().intersects(getTransform())) {
			for (UIButtonListener listener : listeners) {
				listener.onButtonDown(this, button);
			}
		}
	}

	@Override
	public void onMouseButtonUp(int button) {
		if (Cursor.getTransform().intersects(getTransform())) {
			for (UIButtonListener listener : listeners) {
				listener.onButtonUp(this, button);
			}
		}
	}
}
