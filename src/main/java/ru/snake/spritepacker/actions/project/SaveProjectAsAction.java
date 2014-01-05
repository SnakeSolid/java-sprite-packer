package ru.snake.spritepacker.actions.project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.util.Util;

@SuppressWarnings("serial")
public class SaveProjectAsAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public SaveProjectAsAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Save project as...");
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);

		setIcon("saveas", false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		File file = Dialogs.saveProject(parent);

		if (file != null) {
			file = Util.checkForExtension(file);

			if (file.exists()) {
				if (!Dialogs.confirm(parent, R.FILE_EXISTS_OVERWRITE)) {
					return;
				}
			}

			try {
				factory.saveTo(file);
			} catch (IOException e) {
				Dialogs.error(parent, e.getMessage());
			}
		}
	}

}
