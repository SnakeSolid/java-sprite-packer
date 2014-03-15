package ru.snake.spritepacker.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collection;

import ru.snake.spritepacker.core.packer.ImageData;

@SuppressWarnings("serial")
public class TextureAtlas extends CheckerPanel {

	private static final int MIN_SIZE = 64;
	private static final int MAX_WIDTH = 1024;
	private static final int MAX_HEIGHT = 768;

	private final BufferedImage image;

	public TextureAtlas(int width, int height, Collection<ImageData> textures) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics g = image.getGraphics();

		for (ImageData each : textures) {
			g.drawImage(each.texture.image, each.x, each.y, null);
		}

		g.dispose();

		int prefWidth = width;
		int prefHeight = height;

		if (prefWidth < MIN_SIZE) {
			prefWidth = MIN_SIZE;
		} else if (prefWidth > MAX_WIDTH) {
			prefWidth = MAX_WIDTH;
		}

		if (prefHeight < MIN_SIZE) {
			prefHeight = MIN_SIZE;
		} else if (prefHeight > MAX_HEIGHT) {
			prefHeight = MAX_HEIGHT;
		}

		setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
		setPreferredSize(new Dimension(prefWidth, prefHeight));
		setMaximumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int width = getWidth();
		int height = getHeight();
		int imagewidth = image.getWidth(null);
		int imageheight = image.getHeight(null);

		float xscale = (float) width / (float) imagewidth;
		float yscale = (float) height / (float) imageheight;

		if (xscale > 1.0f && yscale > 1.0f) {
			int imagex = (width - imagewidth) / 2;
			int imagey = (height - imageheight) / 2;

			g.drawImage(image, imagex, imagey, null);
		} else if (xscale < yscale) {
			int imagey = height / 2;
			int imageh = Math.round(imageheight * xscale);

			g.drawImage(image, 0, imagey - imageh / 2, width, imageh, null);
		} else {
			int imagex = width / 2;
			int imagew = Math.round(imagewidth * yscale);

			g.drawImage(image, imagex - imagew / 2, 0, imagew, height, null);
		}
	}

}
