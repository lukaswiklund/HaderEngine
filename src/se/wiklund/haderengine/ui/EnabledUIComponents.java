package se.wiklund.haderengine.ui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.Engine;

public class EnabledUIComponents {
	
	private static List<UIComponent> components = new CopyOnWriteArrayList<>();
	
	public static void disableAll() {
		components.clear();
	}
	
	public static void setEnabled(UIComponent component) {
		if (components.contains(component)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to enable a component that is already enabled!");
			return;
		}
		components.add(component);
	}
	
	public static void setDisabled(UIComponent component) {
		if (!components.contains(component)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to disable a component that is already disabled!");
			return;
		}
		components.remove(component);
	}
	
	public static boolean isEnabled(UIComponent component) {
		return components.contains(component);
	}
}
