package se.wiklund.haderengine.ui.listener;

import se.wiklund.haderengine.ui.UIButton;

public interface UIButtonListener {
	
	void onButtonDown(UIButton button, int mouseButton);
	void onButtonUp(UIButton button, int mouseButton);
}
