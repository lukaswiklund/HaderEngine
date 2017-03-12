package se.wiklund.test;

import se.wiklund.haderengine.Engine;

public class Main {
	
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.setState(new TestState());
		engine.start();
	}
}
