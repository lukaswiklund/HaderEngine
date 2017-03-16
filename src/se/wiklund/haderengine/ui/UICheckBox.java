package se.wiklund.haderengine.ui;

import java.util.ArrayList;
import java.util.List;

import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.graphics.UIFont;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.MouseButtonListener;
import se.wiklund.haderengine.ui.listener.UICheckBoxListener;

public class UICheckBox extends UIComponent implements MouseButtonListener {
	
	private List<UICheckBoxListener> listeners = new ArrayList<>();
	
	private String text;
	private UIFont font;
	private float fontSize;
	private float textX, textY;
	private Texture texChecked, texUnchecked;
	private boolean checked;
	
	public UICheckBox(String text, UIFont font, float fontSize, Texture texUnchecked, Texture texChecked, float x, float y, int width, int height) {
		super(texUnchecked, x, y, width, height);
		this.text = text;
		this.font = font;
		this.fontSize = fontSize;
		this.textX = x + width + 10;
		this.textY = y + (height - fontSize) / 2;
		this.texChecked = texChecked;
		this.texUnchecked = texUnchecked;
		
		Mouse.addMouseButtonListener(this);
	}
	
	@Override
	public void render() {
		super.render();
		if (font != null && text != null && !text.isEmpty()) {
			font.renderText(text, fontSize, textX, textY);
		}
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
		setTexture(checked ? texChecked : texUnchecked, true);
	}

	public void addCheckBoxListener(UICheckBoxListener listener) {
		if (listeners.contains(listener)) {
			System.err.println("Tried to add a UICheckBoxListener that already exists!");
			return;
		}
		listeners.add(listener);
	}
	
	public void removeCheckBoxListener(UICheckBoxListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println("Tried to remove a UICheckBoxListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}
	
	@Override
	public void onMouseButtonDown(int button) {
	}

	@Override
	public void onMouseButtonUp(int button) {
		if (Cursor.getTransform().intersects(getTransform())) {
			setChecked(!checked);
			for (UICheckBoxListener listener : listeners) {
				listener.onValueChange(this, checked, button);
			}
		}
	}
}
