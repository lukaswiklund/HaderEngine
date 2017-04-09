package se.wiklund.haderengine.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.GameStateManager;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Keyboard;
import se.wiklund.haderengine.input.Mouse;
import se.wiklund.haderengine.input.Scroll;

public class Window {
	
	private static HashMap<Long, Window> windows = new HashMap<>();
	
	private String title, suffix;
	private boolean fullscreen;
	private boolean vSync;
	private long windowID;
	private int width, height;
	private GameStateManager gsm;
	
	public Window(String title, boolean fullscreen, boolean vSync, GameStateManager gsm) {
		this.title = title;
		this.fullscreen = fullscreen;
		this.vSync = vSync;
		this.gsm = gsm;
		
		createWindow(0);
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		createWindow(windowID);
	}
	
	private void createWindow(long oldID) {
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		final int height = fullscreen ? vidmode.height() : vidmode.height() / 2;
		final int width = fullscreen ? vidmode.width() : height * 16 / 9;
		long monitor = 0;
		if (fullscreen) {
			monitor = glfwGetPrimaryMonitor();
		}
		windowID = glfwCreateWindow(width, height, title, monitor, oldID);
		if (oldID != 0) {
			windows.remove(oldID);
			glfwDestroyWindow(oldID);
		}
		if (windowID == 0) {
			System.err.println(Engine.NAME_PREFIX + "Failed to create window!");
			return;
		}
		windows.put(windowID, this);
		
		glfwSetWindowPos(windowID, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		this.width = width;
		this.height = height;
		
		glfwMakeContextCurrent(windowID);
		
		glfwSetWindowSizeCallback(windowID, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long windowID, int width, int height) {
				glViewport(0, 0, width, height);
				if (windows.get(windowID) == null) {
					System.err.println(Engine.NAME_PREFIX + "Windows list does not contain window ID: " + windowID + "!");
					return;
				}
				Window window = windows.get(windowID);
				window.width = width;
				window.height = height;
			}
		});
		
		glfwSetKeyCallback(windowID, new Keyboard(gsm));
		glfwSetCursorPosCallback(windowID, new Cursor(this));
		glfwSetMouseButtonCallback(windowID, new Mouse(gsm));
		glfwSetScrollCallback(windowID, new Scroll());
		
		GL.createCapabilities();
		
		glfwSwapInterval(vSync ? 1 : 0);
		
		glOrtho(0, Engine.WIDTH, 0, Engine.HEIGHT, 1, -1);
		glClearColor(0, 0, 0, 0);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void repaint() {
		glfwSwapBuffers(windowID);
		glfwPollEvents();
	}
	
	public void setTitle(String title) {
		this.title = title;
		if (suffix == null || suffix.trim().equalsIgnoreCase("")) {
			glfwSetWindowTitle(windowID, title);
		} else {
			glfwSetWindowTitle(windowID, title + " | " + suffix);
		}
	}
	
	public void setTitleSuffix(String suffix) {
		this.suffix = suffix;
		setTitle(title);
	}
	
	public void close() {
		if (windowID != 0) {
			glfwDestroyWindow(windowID);
		}
	}
	
	public boolean isCloseRequested() {
		return glfwWindowShouldClose(windowID);
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isvSync() {
		return vSync;
	}
	
	public void setvSync(boolean vSync) {
		this.vSync = vSync;
		glfwSwapInterval(vSync ? 1 : 0);
	}
}
