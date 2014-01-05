package ru.snake.spritepacker.actions.project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JOptionPane;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class OpenProjectAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public OpenProjectAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Open project");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);

		setIcon("open", false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (factory.isModified()) {
			int result = Dialogs.yesnocancel(parent, R.SAVE_CURRENT_PROJECT);

			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			}

			if (result == JOptionPane.YES_OPTION) {
				File file = factory.getProjectFile();

				if (file != null) {
					file = Dialogs.saveProject(parent);
				}

				if (file == null) {
					return;
				}

				try {
					factory.saveTo(file);
				} catch (IOException e) {
					Dialogs.error(parent, e.getMessage());
				}
			}
		}

		File file = Dialogs.openProject(parent);

		if (file != null && file.exists()) {
			try {
				factory.loadFrom(file);
			} catch (IOException e) {
				Dialogs.error(parent, e.getMessage());
			}
		}
	}

}
