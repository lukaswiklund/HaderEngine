package se.wiklund.haderengine.ui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.MouseButtonListener;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

public class UIButton extends UIComponent implements MouseButtonListener {
	
	private List<UIButtonListener> listeners = new CopyOnWriteArrayList<>();
	
	private UILabel label;
	
	public UIButton(String text, UIFont font, int fontSize, Texture background, float x, float y, int width, int height) {
		super(background, x, y, width, height);
		this.label = new UILabel(text, font, fontSize, width / 2, height / 2, true);
		
		addSubview(label);
		
		Mouse.addMouseButtonListener(this);
		EnabledUIComponents.setEnabled(this);
	}
	
	public void addButtonListener(UIButtonListener listener) {
		if (listeners.contains(listener)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to add a UIButtonListener that already exists!");
			return;
		}
		listeners.add(listener);
	}
	
	public void removeButtonListener(UIButtonListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to remove a UIButtonListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}

	@Override
	public void onMouseButtonDown(int button) {
		if (!EnabledUIComponents.isEnabled(this)) return;
		
		if (Cursor.getTransform().intersects(getTransform())) {
			for (UIButtonListener listener : listeners) {
				listener.onButtonDown(this, button);
			}
		}
	}

	@Override
	public void onMouseButtonUp(int button) {
		if (!EnabledUIComponents.isEnabled(this)) return;
		
		if (Cursor.getTransform().intersects(getTransform())) {
			for (UIButtonListener listener : listeners) {
				listener.onButtonUp(this, button);
			}
		}
	}
}
