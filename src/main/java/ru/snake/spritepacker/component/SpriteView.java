package ru.snake.spritepacker.component;

import java.awt.Dimension;
import java.awt.Graphics;

import ru.snake.spritepacker.core.Sprite;

@SuppressWarnings("serial")
public class SpriteView extends CheckerPanel {

	private final Sprite sprite;

	public SpriteView(Sprite sprite) {
		this.sprite = sprite;

		int width = 2 * sprite.getWidth();
		int height = 2 * sprite.getHeight();

		Dimension dimension = new Dimension(width, height);

		setMinimumSize(dimension);
		setPreferredSize(dimension);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int width = getWidth();
		int height = getHeight();
		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();
		int imagex = width / 2;
		int imagey = height / 2;

		float scalex = (float) width / spriteWidth;
		float scaley = (float) height / spriteHeight;
		float scale;

		if (scalex < scaley) {
			scale = scalex;
		} else {
			scale = scaley;
		}

		if (scale > 3.0f) {
			int imgscale = (int) Math.ceil(scale) - 2;

			sprite.drawScaledTo(g, imagex, imagey, imgscale);
		} else {
			sprite.drawTo(g, imagex, imagey);
		}
	}

}
