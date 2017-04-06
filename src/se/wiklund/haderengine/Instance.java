package se.wiklund.haderengine;

import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;

public class Instance extends View {

	public Instance(Texture texture, float x, float y) {
		this(texture, x, y, texture.getWidth(), texture.getHeight());
	}

	public Instance(Texture texture, float x, float y, int width, int height) {
		super(texture, new Transform(x, y, width, height));
	}
}
