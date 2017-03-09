package se.wiklund.haderengine.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import se.wiklund.haderengine.Main;
import se.wiklund.haderengine.input.Keyboard;

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
		int width = Main.SCREEN_SIZE.width;
		int height = Main.SCREEN_SIZE.height;
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
			Main.exit(-1);
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
		
		GL.createCapabilities();
		
		glfwSwapInterval(0);
		
		glOrtho(0, 1920, 0, 1080, 1, -1);
		glClearColor(0, 0, 0, 0);
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public void repaint() {
		glfwSwapBuffers(windowID);
		glfwPollEvents();
	}
	
	public void setTitle(String title) {
		this.title = title;
		if (suffix == null || suffix.trim().equalsIgnoreCase(""));
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
