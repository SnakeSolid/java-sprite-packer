package ru.snake.spritepacker.actions.project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.util.Util;

@SuppressWarnings("serial")
public class SaveProjectAction extends BasicAction implements Action {

	private static final String ICON_NAME = "save";

	private final Component parent;
	private final CoreFactory factory;

	public SaveProjectAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("SaveProjectAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);

		setIcon(ICON_NAME, false);
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
					if (!Dialogs.confirm(parent,
							Messages.getString("SaveProjectAction.MESSAGE"))) { //$NON-NLS-1$
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
