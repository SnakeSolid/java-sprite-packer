package ru.snake.spritepacker.util;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.component.AnimationView;
import ru.snake.spritepacker.component.ImageView;
import ru.snake.spritepacker.component.SpriteView;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.Sprite;

public class Dialogs {

	private static final String APPLICATION_NAME = "Texture packer"; //$NON-NLS-1$
	private static final String ALL_IMAGE_FORMATS = "All supported image formats"; //$NON-NLS-1$
	private static final String FILE_FILTER_FORMAT = "%s image file format (*.%s)"; //$NON-NLS-1$

	private static final String PROJECT_FILE_FORMAT = "Sprite packer project (*.spz)"; //$NON-NLS-1$
	private static final String PROJECT_FILE_SUFFIX = "spz"; //$NON-NLS-1$

	private static final File[] NO_FILES = {};

	private static File lastImagesDir = new File(System.getProperty("user.dir")); //$NON-NLS-1$
	private static File lastExportDir = new File(System.getProperty("user.dir")); //$NON-NLS-1$
	private static File lastProjectDir = new File(
			System.getProperty("user.dir")); //$NON-NLS-1$

	public static File[] getImages(Component parent) {
		JFileChooser chooser = createImageChooser();

		chooser.setMultiSelectionEnabled(true);

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			lastImagesDir = chooser.getCurrentDirectory();

			return chooser.getSelectedFiles();
		}

		return NO_FILES;
	}

	public static File getImage(Component parent) {
		JFileChooser chooser = createImageChooser();

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			lastImagesDir = chooser.getCurrentDirectory();

			return chooser.getSelectedFile();
		}

		return null;
	}

	private static JFileChooser createImageChooser() {
		JFileChooser chooser = new JFileChooser(lastImagesDir);
		String[] suffixes = ImageIO.getReaderFileSuffixes();
		FileFilter filter;

		filter = new FileNameExtensionFilter(ALL_IMAGE_FORMATS, suffixes);

		chooser.setFileFilter(filter);

		for (String suffix : suffixes) {
			String lowersuffix = suffix.toLowerCase();
			String uppersuffix = suffix.toUpperCase();
			String name;

			name = String.format(FILE_FILTER_FORMAT, uppersuffix, lowersuffix);

			filter = new FileNameExtensionFilter(name, lowersuffix);

			chooser.addChoosableFileFilter(filter);
		}

		chooser.setAcceptAllFileFilterUsed(false);

		return chooser;
	}

	public static void showImage(Component parent, BufferedImage image) {
		if (image == null) {
			return;
		}

		ImageView view = new ImageView(image);

		JOptionPane.showOptionDialog(parent, view, APPLICATION_NAME,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				null, null);
	}

	public static void showSprite(Component parent, Sprite sprite) {
		if (sprite == null) {
			return;
		}

		SpriteView view = new SpriteView(sprite);

		JOptionPane.showOptionDialog(parent, view, sprite.toString(),
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				null, null);
	}

	public static void showAnimation(Component parent, Animation animation) {
		if (animation == null) {
			return;
		}

		AnimationView view = new AnimationView(animation);

		JOptionPane.showOptionDialog(parent, view, animation.toString(),
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				null, null);
	}

	public static boolean confirm(Component parent, String message) {
		int n = JOptionPane.showConfirmDialog(parent, message,
				APPLICATION_NAME, JOptionPane.YES_NO_OPTION);

		return n == JOptionPane.YES_OPTION;
	}

	public static File openProject(Component parent) {
		JFileChooser chooser = createProjectChooser();

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			lastProjectDir = chooser.getCurrentDirectory();

			return chooser.getSelectedFile();
		}

		return null;
	}

	private static JFileChooser createProjectChooser() {
		JFileChooser chooser = new JFileChooser(lastProjectDir);
		FileFilter filter;

		filter = new FileNameExtensionFilter(PROJECT_FILE_FORMAT,
				PROJECT_FILE_SUFFIX);

		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);

		return chooser;
	}

	public static File saveProject(Component parent) {
		JFileChooser chooser = createProjectChooser();

		if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			lastProjectDir = chooser.getCurrentDirectory();

			return chooser.getSelectedFile();
		}

		return null;
	}

	public static int yesnocancel(Component parent, String message) {
		int result = JOptionPane.showConfirmDialog(parent, message,
				APPLICATION_NAME, JOptionPane.YES_NO_CANCEL_OPTION);

		return result;
	}

	public static void message(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, APPLICATION_NAME,
				JOptionPane.PLAIN_MESSAGE);
	}

	public static void warning(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, APPLICATION_NAME,
				JOptionPane.WARNING_MESSAGE);
	}

	public static void error(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, APPLICATION_NAME,
				JOptionPane.ERROR_MESSAGE);
	}

	public static String inputString(Component parent, String message,
			String name) {
		return JOptionPane.showInputDialog(parent, message, name);
	}

	public static File selectDirectory(Component parent) {
		JFileChooser chooser = new JFileChooser(lastExportDir);

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			lastExportDir = chooser.getSelectedFile();

			return chooser.getSelectedFile();
		}

		return null;
	}

	public static String chooseImageFormat(Component parent) {
		String[] formats = ImageIO.getWriterFormatNames();

		if (formats.length == 0) {
			return null;
		}

		Map<String, String> formatnames = new HashMap<String, String>();

		for (String format : formats) {
			String lowersuffix = format.toLowerCase();
			String uppersuffix = format.toUpperCase();
			String name;

			name = String.format(FILE_FILTER_FORMAT, uppersuffix, lowersuffix);

			formatnames.put(name, lowersuffix);
		}

		Object[] values = formatnames.keySet().toArray();
		String defaultformat = formatnames.keySet().iterator().next();

		String choosen = (String) JOptionPane
				.showInputDialog(
						parent,
						Messages.getString("Dialogs.MESSAGE_EXPORT_FORMAT"), APPLICATION_NAME, //$NON-NLS-1$
						JOptionPane.QUESTION_MESSAGE, null, values,
						defaultformat);

		return formatnames.get(choosen);
	}

}
