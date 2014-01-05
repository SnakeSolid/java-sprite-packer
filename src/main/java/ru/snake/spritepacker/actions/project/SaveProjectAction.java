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
public class SaveProjectAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public SaveProjectAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Save project");
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);

		setIcon("save", false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!factory.isModified()) {
			return;
		}

		File file = factory.getProjectFile();

		if (file == null) {
			file = Dialogs.saveProject(parent);

			if (file != null) {
				file = Util.checkForExtension(file);

				if (file.exists()) {
					if (!Dialogs.confirm(parent, R.FILE_EXISTS_OVERWRITE)) {
						return;
					}
				}
			}
		}

		if (file != null) {
			try {
				factory.saveTo(file);
			} catch (IOException e) {
				Dialogs.error(parent, e.getMessage());
			}
		}
	}

}
