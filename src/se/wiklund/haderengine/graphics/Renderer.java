package se.wiklund.haderengine.graphics;

import static org.lwjgl.opengl.GL11.*;
import se.wiklund.haderengine.Instance;
import se.wiklund.haderengine.maths.Transform;

public class Renderer {
	
	public static void render(Instance instance) {
		Transform transform = instance.getTransform();
		float x = transform.getX();
		float y = transform.getY();
		int width = transform.getWidth();
		int height = transform.getHeight();
		
		render(instance.getTexture(), x, y, width, height);
	}
	
	public static void render(Texture texture, Transform transform) {
		render(texture, transform.getX(), transform.getY(), transform.getWidth(), transform.getHeight());
	}
	
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
