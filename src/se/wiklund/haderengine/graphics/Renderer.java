package se.wiklund.haderengine.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
	
	public static void render(Texture texture, float x, float y, float width, float height) {
		texture.bind();
		
		glBegin(GL_QUADS);
		
		glTexCoord2i(0, 1);
		glVertex2f(x, y);
		
		glTexCoord2i(1, 1);
		glVertex2f(x + width, y);
		
		glTexCoord2i(1, 0);
		glVertex2f(x + width, y + height);
		
		glTexCoord2i(0, 0);
		glVertex2f(x, y + height);
		
		glEnd();
	}
}
