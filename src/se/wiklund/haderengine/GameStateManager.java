package se.wiklund.haderengine;

public class GameStateManager {

	private State state;

	public void update(double delta) {
		if (state != null)
			state.update(delta);
	}

	public void render() {
		if (state != null)
			state.render();
	}

	public void setState(State state) {
		this.state = state;
	}
}
