package ru.snake.spritepacker.core;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

public interface TextureLoader {

	public Texture load(BufferedImage image);

	public Texture load(File file);

	public Texture loadCroped(BufferedImage image);

	public Texture loadCroped(File file);

	public Texture loadCroped(BufferedImage image, Point offset);

	public Texture loadCroped(File file, Point offset);

}
