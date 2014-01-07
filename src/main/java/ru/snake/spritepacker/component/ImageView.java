package ru.snake.spritepacker.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageView extends JPanel {

	private static final int MAX_WIDTH = 1024;
	private static final int MAX_HEIGHT = 768;

	private final Image image;

	public ImageView(Image image) {
		this.image = image;

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		if (width > MAX_WIDTH) {
			width = MAX_WIDTH;
		}

		if (height > MAX_HEIGHT) {
			height = MAX_HEIGHT;
		}

		Dimension dimension = new Dimension(width, height);

		setPreferredSize(dimension);
		setMaximumSize(dimension);
	}

	@Override
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int imagewidth = image.getWidth(null);
		int imageheight = image.getHeight(null);

		float xscale = (float) width / (float) imagewidth;
		float yscale = (float) height / (float) imageheight;

		g.fillRect(0, 0, width, height);

		if (xscale > 1.0f && yscale > 1.0f) {
			int imagex = (width - imagewidth) / 2;
			int imagey = (height - imageheight) / 2;

			g.drawImage(image, imagex, imagey, null);
		} else if (xscale < yscale) {
			int imagey = height / 2;
			int imageh = Math.round(height * xscale);

			g.drawImage(image, 0, imagey - imageh / 2, width, imageh, null);
		} else {
			int imagex = width / 2;
			int imagew = Math.round(width * yscale);

			g.drawImage(image, imagex - imagew / 2, 0, imagew, height, null);
		}
	}

}
