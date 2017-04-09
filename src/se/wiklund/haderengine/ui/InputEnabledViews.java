package se.wiklund.haderengine.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;

public class InputEnabledViews {
	
	private static List<View> views = new CopyOnWriteArrayList<>();
	private static HashMap<String, List<View>> savedStates = new HashMap<>();
	
	public static void disableAll() {
		views.clear();
	}
	
	public static void setEnabled(View view) {
		if (views.contains(view)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to enable a view that is already enabled!");
			return;
		}
		views.add(view);
	}
	
	public static void setDisabled(View view) {
		if (!views.contains(view)) {
			System.err.println(Engine.NAME_PREFIX + "Tried to disable a view that is already disabled!");
			return;
		}
		views.remove(view);
	}
	
	public static boolean isEnabled(View view) {
		return views.contains(view);
	}

	public static void saveState(String key) {
		savedStates.put(key, new ArrayList<>(views));
		views.clear();
	}

	public static void loadState(String key) {
		if (!savedStates.containsKey(key)) {
			System.out.println(Engine.NAME_PREFIX + "InputEnabledViews state " + key + " does not exist!");
			return;
		}

		views.clear();
		views.addAll(new ArrayList<>(savedStates.get(key)));
	}
}
