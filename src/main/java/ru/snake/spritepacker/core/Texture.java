package ru.snake.spritepacker.core;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.Arrays;

public class Texture {

	public final BufferedImage image;
	public final int imagehash;

	public Texture(BufferedImage image) {
		this.image = image;

		int[] rgb = getRasterData(image);

		imagehash = Arrays.hashCode(rgb);
		rgb = null;
	}

	private int[] getRasterData(BufferedImage image) {
		Raster raster = image.getRaster();
		int width = image.getWidth();
		int height = image.getHeight();

		return raster.getPixels(0, 0, width, height, (int[]) null);
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

		int[] thisrgb = getRasterData(image);
		int[] otherrgb = getRasterData(other.image);

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
