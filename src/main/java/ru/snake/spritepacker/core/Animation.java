package ru.snake.spritepacker.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Animation {

	private final List<Sprite> sprites;

	private Rectangle bounds;

	public String name;

	public Animation(String name, List<Sprite> sprites) {
		this.sprites = new ArrayList<Sprite>(sprites);
		this.name = name;

		updateBounds();
	}

	private void updateBounds() {
		bounds = new Rectangle();

		for (Sprite each : sprites) {
			bounds.add(-each.offsetX, -each.offsetY);
			bounds.add(-each.offsetX + each.getWidth(),
					-each.offsetY + each.getHeight());
		}
	}

	@Override
	public String toString() {
		return name;
	}

	public int getWidth() {
		return bounds.width;
	}

	public int getHeight() {
		return bounds.height;
	}

	public void drawTo(Graphics g, int imagex, int imagey, int frame) {
		if (frame >= 0 && frame < sprites.size()) {
			Sprite sprite = sprites.get(frame);

			sprite.drawTo(g, imagex - (bounds.x + bounds.width / 2), imagey
					- (bounds.y + bounds.height / 2));

			g.setColor(Color.GREEN);
			g.drawRect(imagex - bounds.width / 2, imagey - bounds.height / 2,
					bounds.width, bounds.height);
		}
	}

	public void drawScaledTo(Graphics g, int imagex, int imagey, int scale,
			int frame) {
		if (frame >= 0 && frame < sprites.size()) {
			Sprite sprite = sprites.get(frame);

			sprite.drawScaledTo(g, imagex - (bounds.x + bounds.width / 2)
					* scale, imagey - (bounds.y + bounds.height / 2) * scale,
					scale);

			g.setColor(Color.GREEN);
			g.drawRect(imagex - bounds.width * scale / 2, imagey
					- bounds.height * scale / 2, bounds.width * scale,
					bounds.height * scale);
		}
	}

	public int getFrameCount() {
		return sprites.size();
	}

	public List<Sprite> getSprites() {
		return Collections.unmodifiableList(sprites);
	}

	public void setSprites(Collection<Sprite> collection) {
		sprites.clear();
		sprites.addAll(collection);

		updateBounds();
	}

	public Point getOffset() {
		Point offset = new Point();

		for (Sprite sprite : sprites) {
			offset.translate(sprite.offsetX, sprite.offsetY);
		}

		int size = sprites.size();

		if (size > 0) {
			offset.x /= size;
			offset.y /= size;
		}

		return offset;
	}

	public void translate(int x, int y) {
		for (Sprite each : sprites) {
			each.offsetX += x;
			each.offsetY += y;
		}

		updateBounds();
	}

}
