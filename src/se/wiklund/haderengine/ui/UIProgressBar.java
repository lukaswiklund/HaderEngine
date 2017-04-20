package se.wiklund.haderengine.ui;

import se.wiklund.haderengine.View;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.style.UIProgressBarStyle;
import se.wiklund.haderengine.util.TextureCreator;

public class UIProgressBar extends View {

	private UIProgressBarStyle style;
	private float minValue, maxValue;
	private float progress;

	public UIProgressBar(UIProgressBarStyle style, float minValue, float maxValue, Transform transform) {
		super(null, transform);
		this.style = style;
		this.minValue = minValue;
		this.maxValue = maxValue;

		setProgress(minValue);
	}

	public void setProgress(float progress) {
		if (this.progress == progress)
			return;
		if (progress < minValue)
			progress = minValue;
		if (progress > maxValue)
			progress = maxValue;
		this.progress = progress;

		Transform t = getTransform();
		TextureCreator creator = new TextureCreator(t.getWidth(), t.getHeight());
		int barWidth = t.getWidth();
		int barHeight = t.getHeight();
		if (style.getFrameTexture() != null) {
			creator.addTexture(style.getFrameTexture(), 0, 0, t.getWidth(), t.getHeight());
			barWidth -= style.getFrameSizeX() * 2;
			barHeight -= style.getFrameSizeY() * 2;
		}

		creator.addTexture(style.getBackgroundTexture(), style.getFrameSizeX(), style.getFrameSizeY(), barWidth, barHeight);

		float percentage = (progress - minValue) / ((maxValue - minValue) / 100);
		int filledBarWidth = (int) (barWidth * (percentage / 100));
		creator.addTexture(style.getForegroundTexture(), style.getFrameSizeX(), style.getFrameSizeY(), filledBarWidth, barHeight);

		setTexture(creator.getTexture());
	}

	public float getProgress() {
		return progress;
	}
}
