package ru.snake.spritepacker.plugin;

import java.io.File;
import java.io.FileFilter;

public class JarFileFilter implements FileFilter {

	private static final String JAR_FILE_EXTENSION = ".jar";

	@Override
	public boolean accept(File file) {
		return file.getName().endsWith(JAR_FILE_EXTENSION);
	}

}
