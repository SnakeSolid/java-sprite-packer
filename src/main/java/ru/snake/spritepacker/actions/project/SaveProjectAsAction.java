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
public class SaveProjectAsAction extends BasicAction implements Action {

	private static final String ICON_NAME = "saveas";

	private final Component parent;
	private final CoreFactory factory;

	public SaveProjectAsAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("SaveProjectAsAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);

		setIcon(ICON_NAME, false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		File file = Dialogs.saveProject(parent);

		if (file != null) {
			file = Util.checkForExtension(file);

			if (file.exists()) {
				if (!Dialogs.confirm(parent,
						Messages.getString("SaveProjectAsAction.MESSAGE"))) { //$NON-NLS-1$
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
