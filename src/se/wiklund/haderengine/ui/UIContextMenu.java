package se.wiklund.haderengine.ui;

import com.sun.istack.internal.NotNull;
import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.MouseButtonListener;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.haderengine.ui.style.UIContextMenuStyle;

import java.util.ArrayList;

public class UIContextMenu extends UIComponent implements MouseButtonListener {

	private ArrayList<UIButton> buttons = new ArrayList<>();
	private UIContextMenuStyle style;
	
	public UIContextMenu(@NotNull UIContextMenuStyle style) {
		super(style.getBackground(), 0, 0, style.getWidth(), 0);
		this.style = style;
		setHidden(true);
		Mouse.addMouseButtonListener(this);
	}

	public UIButton addButton(String title, UIButtonListener listener) {
		UIButton button = new UIButton(title, style.getFont(), style.getFontSize(), style.getBackground(), 0, 0, style.getWidth(),
				style.getButtonHeight());
		if (listener != null)
			button.addButtonListener(listener);
		EnabledUIComponents.setDisabled(button);
		buttons.add(button);
		addSubview(button);
		getTransform().setHeight(style.getButtonHeight() * buttons.size());
		return button;
	}

	public void show() {
		if (!isHidden()) {
			hide();
		}

		EnabledUIComponents.saveState("context_menu");
		Transform t = getTransform();
		switch (style.getMode()) {
		case MODE_CENTER:
			t.setPosition((Engine.WIDTH - t.getWidth()) / 2, (Engine.HEIGHT - t.getHeight()) / 2);
			break;
		case MODE_CURSOR:
			Transform c = Cursor.getTransform();
			t.setPosition(c.getX(), c.getY() - t.getHeight());
			break;
		}

		for (int i = 0; i < buttons.size(); i++) {
			UIButton button = buttons.get(i);
			int menuHeight = style.getButtonHeight() * buttons.size();
			button.getTransform().setPosition(t.getX(), t.getY() + menuHeight - (i + 1) * style.getButtonHeight());
			if (!EnabledUIComponents.isEnabled(button)) {
				EnabledUIComponents.setEnabled(button);
			}
		}

		EnabledUIComponents.setEnabled(this);

		setHidden(false);
	}

	public void hide() {
		setHidden(true);
		EnabledUIComponents.loadState("context_menu");
	}

	@Override
	public void update(float delta) {
		if (isHidden())
			return;
		super.update(delta);
	}
	
	@Override
	public void onMouseButtonDown(int button) {

	}

	@Override
	public void onMouseButtonUp(int button) {
		if (!EnabledUIComponents.isEnabled(this))
			return;
		if (!Cursor.getTransform().intersects(getTransform())) {
			hide();
		}
	}
}
