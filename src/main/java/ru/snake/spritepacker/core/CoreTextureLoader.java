package ru.snake.spritepacker.core;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CoreTextureLoader implements TextureLoader {

	private final CoreFactory factory;

	public CoreTextureLoader(CoreFactory factory) {
		this.factory = factory;
	}

	@Override
	public Texture load(BufferedImage image) {
		Texture texture = new Texture(image);

		factory.createTexture(texture);

		return texture;
	}

	@Override
	public Texture load(File file) {
		BufferedImage image;

		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			return null;
		}

		return load(image);
	}

	@Override
	public Texture loadCroped(BufferedImage image) {
		return loadCroped(image, null);
	}

	@Override
	public Texture loadCroped(File file) {
		return loadCroped(file, null);
	}

	@Override
	public Texture loadCroped(BufferedImage image, Point offset) {
		ColorModel colorModel = image.getColorModel();

		if (!colorModel.hasAlpha()) {
			return load(image);
		}

		BufferedImage subimage;
		WritableRaster raster = image.getAlphaRaster();

		int height = image.getHeight();
		int width = image.getWidth();
		int top = 0;
		int left = 0;
		int bottom = height;
		int right = width;

		int[] rgb = new int[width * height];

		raster.getSamples(0, 0, width, height, 0, rgb);

		toploop: while (top < bottom) {
			for (int i = 0; i < width; i++) {
				if (rgb[i + width * top] != 0) {
					break toploop;
				}
			}

			top++;
		}

		bottomloop: while (top < bottom) {
			for (int i = 0; i < width; i++) {
				if (rgb[i + width * (bottom - 1)] != 0) {
					break bottomloop;
				}
			}

			bottom--;
		}

		if (top == bottom) {
			return null;
		}

		leftloop: while (left < right) {
			for (int i = top; i < bottom; i++) {
				if (rgb[left + width * i] != 0) {
					break leftloop;
				}
			}

			left++;
		}

		rightloop: while (left < right) {
			for (int i = top; i < bottom; i++) {
				if (rgb[right - 1 + width * i] != 0) {
					break rightloop;
				}
			}

			right--;
		}

		if (left == right) {
			return null;
		}

		subimage = image.getSubimage(left, top, right - left, bottom - top);

		if (offset != null) {
			offset.setLocation(left, top);
		}

		return load(subimage);
	}

	@Override
	public Texture loadCroped(File file, Point offset) {
		BufferedImage image;

		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			return null;
		}

		return loadCroped(image, offset);
	}

}
