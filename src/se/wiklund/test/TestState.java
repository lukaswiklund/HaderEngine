package se.wiklund.test;

import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.graphics.Renderer;
import se.wiklund.haderengine.graphics.Texture;

public class TestState extends State {
	
	private Instance instance;
	
	public TestState() {
		instance = new Instance(new Texture("/bg.jpg"), 0, 0, 500, 500);
	}
	
	@Override
	public void update(double delta) {
		
	}

	@Override
	public void render() {
		Renderer.render(instance);
	}
}
