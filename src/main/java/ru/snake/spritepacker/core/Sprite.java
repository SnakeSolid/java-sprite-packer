package ru.snake.spritepacker.core;

import java.awt.Color;
import java.awt.Graphics;

public class Sprite {

	public Texture texture;

	public int offsetX;
	public int offsetY;

	public String name;

	public Sprite(int offsetX, int offsetY, String name, Texture texture) {
		this.texture = texture;

		this.offsetX = offsetX;
		this.offsetY = offsetY;

		this.name = name;
	}

	public int getWidth() {
		if (texture == null) {
			return 1;
		}

		return texture.image.getWidth();
	}

	public int getHeight() {
		if (texture == null) {
			return 1;
		}

		return texture.image.getHeight();
	}

	public boolean hasTexture() {
		return texture != null;
	}

	public void drawTo(Graphics g, int x, int y) {
		if (texture != null) {
			g.drawImage(texture.image, x - offsetX, y - offsetY, null);
		}

		g.setColor(Color.BLACK);
		g.drawLine(x - 10, y, x + 10, y);
		g.drawLine(x, y - 10, x, y + 10);
		g.drawRect(x - 2, y - 2, 4, 4);

		g.setColor(Color.RED);
		g.fillRect(x - 1, y - 1, 3, 3);
	}

	public void drawScaledTo(Graphics g, int x, int y, int scale) {
		int posx = x - offsetX * scale;
		int posy = y - offsetY * scale;

		if (texture != null) {
			int width = texture.image.getWidth() * scale;
			int height = texture.image.getHeight() * scale;

			g.drawImage(texture.image, posx, posy, width, height, null);
		}

		g.setColor(Color.BLACK);
		g.drawLine(x - 10, y, x + 10, y);
		g.drawLine(x, y - 10, x, y + 10);
		g.drawRect(x - 2, y - 2, 4, 4);

		g.setColor(Color.RED);
		g.fillRect(x - 1, y - 1, 3, 3);
	}

	@Override
	public String toString() {
		return name;
	}

}
