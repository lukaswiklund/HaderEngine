package se.wiklund.haderengine.ui.style;

import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.ui.UIFont;

public class UIContextMenuStyle {

    private UIContextMode mode;
    private Texture background;
    private final UIFont font;
    private int fontSize;
    private int width;
    private int buttonHeight;

    public UIContextMenuStyle(UIContextMode mode, Texture background, UIFont font, int fontSize, int width, int buttonHeight) {
        this.mode = mode;
        this.background = background;
        this.font = font;
        this.fontSize = fontSize;
        this.width = width;
        this.buttonHeight = buttonHeight;
    }

    public UIContextMode getMode() {
        return mode;
    }

    public Texture getBackground() {
        return background;
    }

    public UIFont getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getWidth() {
        return width;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public enum UIContextMode {
        MODE_CENTER, MODE_CURSOR;
    }
}
