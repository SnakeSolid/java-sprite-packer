package ru.snake.spritepacker.actions.load;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.core.TextureLoader;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.util.Util;

@SuppressWarnings("serial")
public class ImportAnimationAction extends BasicAction implements Action {

	private static final String ICON_NAME = "import"; //$NON-NLS-1$

	private final Component parent;
	private final CoreFactory factory;

	public ImportAnimationAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("ImportAnimationAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);

		setIcon(ICON_NAME, false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		File[] images = Dialogs.getImages(parent);

		if (images.length == 0) {
			return;
		}

		if (Dialogs.confirm(parent,
				Messages.getString("ImportAnimationAction.MESSAGE"))) { //$NON-NLS-1$
			importCropped(images);
		} else {
			importAsIs(images);
		}
	}

	private void importAsIs(File[] images) {
		List<Sprite> spritelist = new ArrayList<Sprite>(images.length);

		TextureLoader textureLoader = factory.getTextureLoader();

		for (File each : images) {
			Texture texture = textureLoader.load(each);

			if (texture == null) {
				continue;
			}

			String name = Util.getFullName(each);
			Sprite sprite = new Sprite(0, 0, name, texture);

			spritelist.add(sprite);
		}

		String name = Util.getNameFromFiles(images);

		factory.createAnimation(name, spritelist);
	}

	private void importCropped(File[] images) {
		List<Sprite> spritelist = new ArrayList<Sprite>(images.length);
		List<Point> offsetlist = new ArrayList<Point>(images.length);

		TextureLoader textureLoader = factory.getTextureLoader();

		for (File each : images) {
			Point point = new Point();
			Texture texture = textureLoader.loadCroped(each, point);

			if (texture == null) {
				continue;
			}

			String name = Util.getFullName(each);
			Sprite sprite = new Sprite(point.x, point.y, name, texture);

			spritelist.add(sprite);
			offsetlist.add(point);
		}

		int commonX = 0;
		int commonY = 0;
		int width = 0;
		int height = 0;

		for (int index = 0; index < spritelist.size(); index++) {
			Point point = offsetlist.get(index);
			Sprite sprite = spritelist.get(index);

			commonX += point.x;
			commonY += point.y;

			if (width < sprite.getWidth()) {
				width = sprite.getWidth();
			}

			if (height < sprite.getHeight()) {
				height = sprite.getHeight();
			}
		}

		int destX = commonX / offsetlist.size() + width / 2;
		int destY = commonY / offsetlist.size() + height / 2;

		for (int index = 0; index < spritelist.size(); index++) {
			Point point = offsetlist.get(index);
			Sprite sprite = spritelist.get(index);

			sprite.offsetX = destX - point.x;
			sprite.offsetY = destY - point.y;
		}

		String name = Util.getNameFromFiles(images);

		factory.createAnimation(name, spritelist);
	}

}
