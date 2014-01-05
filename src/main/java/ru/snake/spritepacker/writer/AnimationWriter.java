package ru.snake.spritepacker.writer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.snake.spritepacker.core.CoreFactoryWalker;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.util.Util;

public class AnimationWriter implements CoreFactoryWalker {

	private final File destination;
	private final String name;
	private final String format;

	private boolean writeSprite;

	public AnimationWriter(File destination, String name, String format) {
		this.destination = destination;
		this.name = name;
		this.format = format;
	}

	@Override
	public void start() {
	}

	@Override
	public void end() {
	}

	@Override
	public void startAnimation(String name) {
		writeSprite = this.name.equals(name);
	}

	@Override
	public void endAnimation() {
	}

	@Override
	public void startSprite(String name, int offsetX, int offsetY,
			Texture texture) {
		if (!writeSprite) {
			return;
		}

		String filename = Util.getValidFileName(name, format);
		File output = new File(destination, filename);

		try {
			ImageIO.write(texture.image, format, output);
		} catch (IOException e) {
			Dialogs.error(null, e.getMessage());
		}
	}

	@Override
	public void endSprite() {
	}

}
