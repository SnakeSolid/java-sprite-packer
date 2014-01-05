package ru.snake.spritepacker.core;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Texture {

	public final BufferedImage image;
	public final int imagehash;

	public Texture(BufferedImage image) {
		this.image = image;

		int[] rgb = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), rgb, 0,
				image.getWidth());
		imagehash = Arrays.hashCode(rgb);
		rgb = null;
	}

	@Override
	public int hashCode() {
		return imagehash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Texture)) {
			return false;
		}

		Texture other = (Texture) obj;

		if (imagehash != other.imagehash) {
			return false;
		}

		int width = image.getWidth();
		int height = image.getHeight();

		if (width != other.image.getWidth()
				|| height != other.image.getHeight()) {
			return false;
		}

		int[] thisrgb = new int[width * height];
		int[] otherrgb = new int[width * height];

		image.getRGB(0, 0, width, height, thisrgb, 0, width);
		image.getRGB(0, 0, width, height, otherrgb, 0, width);

		return Arrays.equals(thisrgb, otherrgb);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Texture [width = ");
		builder.append(image.getWidth());
		builder.append(", height = ");
		builder.append(image.getHeight());
		builder.append("]");

		return builder.toString();
	}

}
