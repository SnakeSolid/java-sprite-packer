package ru.snake.spritepacker.writer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.snake.spritepacker.core.CoreFactoryWalker;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.util.Util;

public class ProjectWriter implements CoreFactoryWalker {

	private final File destination;
	private final String format;

	private File directory;

	public ProjectWriter(File destination, String format) {
		this.destination = destination;
		this.format = format;
	}

	@Override
	public void start() {
	}

	@Override
	public void end() {
	}

	@Override
	public void startAnimation(int index, String name) {
		directory = new File(destination, Util.getValidDirName(name));

		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	@Override
	public void endAnimation() {
	}

	@Override
	public void startSprite(int index, String name, int offsetX, int offsetY,
			Texture texture) {
		if (!directory.exists()) {
			return;
		}

		String filename = Util.getValidFileName(name, format);
		File output = new File(directory, filename);

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
