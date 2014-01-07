package ru.snake.spritepacker;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final String BUNDLE_NAME = "ui/messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE;

	static {
		Locale locale = Locale.getDefault();
		ResourceBundle.Control control = new Utf8BundleControl();

		RESOURCE_BUNDLE = ResourceBundle
				.getBundle(BUNDLE_NAME, locale, control);
	}

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}