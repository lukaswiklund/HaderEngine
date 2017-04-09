package se.wiklund.haderengine.ui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.listener.UICheckBoxListener;

public class UICheckBox extends View {

	private List<UICheckBoxListener> listeners = new CopyOnWriteArrayList<>();

	private UILabel label;
	private Texture texChecked, texUnchecked;
	private boolean checked;

	public UICheckBox(String text, UIFont font, int fontSize, Texture texUnchecked, Texture texChecked, Transform transform) {
		super(texUnchecked, transform);
		this.label = new UILabel(text, font, fontSize, transform.getX() + transform.getWidth() * 1.2f,
				transform.getY() + (transform.getHeight() - fontSize) / 2, false);
		this.texChecked = texChecked;
		this.texUnchecked = texUnchecked;

		addSubview(label);

		InputEnabledViews.setEnabled(this);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		setTexture(checked ? texChecked : texUnchecked, false);
	}

	public void addCheckBoxListener(UICheckBoxListener listener) {
		if (listeners.contains(listener)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to add a UICheckBoxListener that already exists!");
			return;
		}
		listeners.add(listener);
	}

	public void removeCheckBoxListener(UICheckBoxListener listener) {
		if (!listeners.contains(listener)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to remove a UICheckBoxListener that does not exist!");
			return;
		}
		listeners.remove(listener);
	}
	
	@Override
	public void onMouseButtonUp(int button) {
		setChecked(!checked);
		for (UICheckBoxListener listener : listeners) {
			listener.onValueChange(this, checked, button);
		}
	}
}
