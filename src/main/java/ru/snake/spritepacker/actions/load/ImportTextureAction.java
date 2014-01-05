package ru.snake.spritepacker.actions.load;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class ImportTextureAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public ImportTextureAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Import texture...");
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		File file = Dialogs.getImage(parent);

		if (file == null) {
			return;
		}

		if (Dialogs.confirm(parent, R.CROP_IMAGES_WHILE_IMPORT)) {
			factory.createTexture(file, null);
		} else {
			factory.createTexture(file);
		}
	}

}
