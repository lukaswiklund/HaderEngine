package se.wiklund.haderengine.ui.listener;

import se.wiklund.haderengine.ui.UICheckBox;

public interface UICheckBoxListener {
	
	void onValueChange(UICheckBox checkBox, boolean checked, int mouseButton);
}
