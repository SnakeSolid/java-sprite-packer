package ru.snake.spritepacker;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "ru.snake.spritepacker.i18n.Messages";
	private static final String NO_KEY_FOUND_FORMAT = "!{0}!";

	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			MessageFormat format = new MessageFormat(NO_KEY_FOUND_FORMAT);
			Object[] params = { key };

			return format.format(params);
		}
	}

}
