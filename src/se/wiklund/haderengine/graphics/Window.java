package se.wiklund.haderengine.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.Keyboard;
import se.wiklund.haderengine.input.Mouse;

public class Window {
	
	private String title, suffix;
	private boolean fullscreen;
	private long windowID;
	
	public Window(String title, boolean fullscreen) {
		this.title = title;
		this.fullscreen = fullscreen;
		
		createWindow(0);
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		createWindow(windowID);
	}
	
	private void createWindow(long oldID) {
		int width = Engine.SCREEN_SIZE.width;
		int height = Engine.SCREEN_SIZE.height;
		long monitor = 0;
		if (fullscreen) {
			monitor = glfwGetPrimaryMonitor();
		} else {
			height /= 2;
			width = height * 16 / 9;
		}
		
		windowID = glfwCreateWindow(width, height, title, monitor, oldID);
		if (oldID != 0) {
			glfwDestroyWindow(oldID);
		}
		if (windowID == 0) {
			System.err.println("Failed to create window!");
			return;
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowID, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		glfwMakeContextCurrent(windowID);
		
		glfwSetWindowSizeCallback(windowID, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long windowID, int width, int height) {
				glViewport(0, 0, width, height);
			}
		});
		
		glfwSetKeyCallback(windowID, new Keyboard());
		glfwSetCursorPosCallback(windowID, new Cursor());
		glfwSetMouseButtonCallback(windowID, new Mouse());
		
		GL.createCapabilities();
		
		glfwSwapInterval(1);
		
		glOrtho(0, Engine.WIDTH, 0, Engine.HEIGHT, 1, -1);
		glClearColor(0, 0, 0, 0);
		
		glEnable(GL_TEXTURE_2D);
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
}
