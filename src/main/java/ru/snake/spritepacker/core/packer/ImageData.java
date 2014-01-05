package ru.snake.spritepacker.core.packer;

import ru.snake.spritepacker.core.Texture;

public class ImageData {

	private static final int EMPTY_ATLAS_INDEX = -1;

	public final Texture texture;

	public int atlas;
	public int x;
	public int y;
	public int width;
	public int height;

	public ImageData(Texture texture) {
		this.texture = texture;

		width = texture.image.getWidth();
		height = texture.image.getHeight();

		reset();
	}

	public void reset() {
		atlas = EMPTY_ATLAS_INDEX;
		x = 0;
		y = 0;
	}

	public boolean isAvaliable() {
		return atlas == EMPTY_ATLAS_INDEX;
	}

}
