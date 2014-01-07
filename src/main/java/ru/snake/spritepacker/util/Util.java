package ru.snake.spritepacker.util;

import java.io.File;

public class Util {

	private static final String ALLOWED_FILENAME_CHARS_REGEX = "[^a-zA-Z0-9.-]"; //$NON-NLS-1$
	private static final String INDEX_REGEX = "[-0-9_ ]*$"; //$NON-NLS-1$
	private static final String INDEX_AND_EXTENSION_REGEX = "[-0-9_ ]*\\.[A-Za-z]+$"; //$NON-NLS-1$
	private static final String EXTENSION_REGEX = "\\.[A-Za-z]+$"; //$NON-NLS-1$

	private static final String DEFAULT_NAME = "none"; //$NON-NLS-1$;
	private static final String PROJECT_FILE_DOT_SUFFIX = ".spz"; //$NON-NLS-1$;

	private static final int MIN_PREFIX_LENGTH = 3;

	public static String getCommonFilePrefix(File[] files) {
		if (files.length == 0) {
			return null;
		}

		if (files.length == 1) {
			String result = files[0].getName();

			result = result.replaceFirst(INDEX_AND_EXTENSION_REGEX, ""); //$NON-NLS-1$

			return result;
		}

		String commonPrefix = null;

		for (File each : files) {
			String filename = each.getName();

			if (commonPrefix == null) {
				commonPrefix = filename;
			} else {
				int len = commonPrefix.length();

				for (int index = 0; index < len; index++) {
					if (commonPrefix.charAt(index) != filename.charAt(index)) {
						if (index < MIN_PREFIX_LENGTH) {
							return null;
						}

						commonPrefix = commonPrefix.substring(0, index - 1);

						break;
					}
				}
			}
		}

		commonPrefix = commonPrefix.replaceFirst(INDEX_REGEX, ""); //$NON-NLS-1$

		return commonPrefix;
	}

	public static int nearestPowerOf2(int value) {
		int result = 1;

		while (result < value) {
			result <<= 1;
		}

		return result;
	}

	public static File checkForExtension(File file) {
		String filename = file.getName();

		if (!filename.endsWith(PROJECT_FILE_DOT_SUFFIX)) {
			String fullpath = file.getAbsolutePath();

			file = new File(fullpath + PROJECT_FILE_DOT_SUFFIX);
		}
		return file;
	}

	public static String getFullName(File file) {
		if (file == null) {
			return DEFAULT_NAME;
		}

		String result = file.getName();

		result = result.replaceFirst(EXTENSION_REGEX, ""); //$NON-NLS-1$

		return result;
	}

	public static String getValidFileName(String name, String extension) {
		String filename = name;

		filename = filename.replaceAll(EXTENSION_REGEX, ""); //$NON-NLS-1$
		filename = filename.replaceAll(ALLOWED_FILENAME_CHARS_REGEX, "_"); //$NON-NLS-1$
		filename = filename.replaceAll("__+", "_"); //$NON-NLS-1$ //$NON-NLS-2$

		return filename + "." + extension; //$NON-NLS-1$
	}

	public static String getValidDirName(String name) {
		String filename = name;

		filename = filename.replaceAll(EXTENSION_REGEX, ""); //$NON-NLS-1$
		filename = filename.replaceAll(ALLOWED_FILENAME_CHARS_REGEX, "_"); //$NON-NLS-1$
		filename = filename.replaceAll("__+", "_"); //$NON-NLS-1$ //$NON-NLS-2$

		return filename;
	}

}
