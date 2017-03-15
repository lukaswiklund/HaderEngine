package se.wiklund.haderengine.ui;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.graphics.Texture;

public class UIComponent extends Instance {

	public UIComponent(Texture texture, float x, float y, int width, int height) {
		super(texture, x, y, width, height);
	}
}
