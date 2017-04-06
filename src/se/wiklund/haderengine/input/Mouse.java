package se.wiklund.haderengine.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import se.wiklund.haderengine.Engine;

public class Mouse extends GLFWMouseButtonCallback {

    private static List<MouseButtonListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void invoke(long windowID, int button, int action, int mods) {
        if (action == GLFW_RELEASE) {
            for (MouseButtonListener listener : listeners) {
                listener.onMouseButtonUp(button);
            }
        } else if (action == GLFW_PRESS) {
            for (MouseButtonListener listener : listeners) {
                listener.onMouseButtonDown(button);
            }
        }
    }

    public static void addMouseButtonListener(MouseButtonListener listener) {
        if (listeners.contains(listener)) {
            System.err.println(Engine.NAME_PREFIX + "Tried to add a MouseButtonListener that already exists!");
            return;
        }
        listeners.add(listener);
    }

    public static void removeMouseButtonListener(MouseButtonListener listener) {
        if (!listeners.contains(listener)) {
            System.err.println(Engine.NAME_PREFIX + "Tried to remove a MouseButtonListener that does not exist!");
            return;
        }
        listeners.remove(listener);
    }

    public static List<MouseButtonListener> getMouseButtonListeners() {
        return listeners;
    }
}
