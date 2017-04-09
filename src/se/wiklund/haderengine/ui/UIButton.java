package se.wiklund.haderengine.ui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.listener.UIButtonListener;

public class UIButton extends View {
	
	private List<UIButtonListener> listeners = new CopyOnWriteArrayList<>();
	
	private UILabel label;
	
	public UIButton(String text, UIFont font, int fontSize, Texture background, Transform transform) {
		super(background, transform);
		this.label = new UILabel(text, font, fontSize, transform.getWidth() / 2, transform.getHeight() / 2, true);
		
		addSubview(label);
		
		InputEnabledViews.setEnabled(this);
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
		if (!InputEnabledViews.isEnabled(this)) return;
		
		for (UIButtonListener listener : listeners) {
			listener.onButtonDown(this, button);
		}
	}
	
	@Override
	public void onMouseButtonUp(int button) {
		if (!InputEnabledViews.isEnabled(this)) return;
		
		for (UIButtonListener listener : listeners) {
			listener.onButtonUp(this, button);
		}
	}
}
