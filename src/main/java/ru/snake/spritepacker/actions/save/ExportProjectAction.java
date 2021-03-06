package ru.snake.spritepacker.actions.save;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.writer.ProjectWriter;

@SuppressWarnings("serial")
public class ExportProjectAction extends BasicAction implements Action {

	private static final String ICON_NAME = "export"; //$NON-NLS-1$

	private final Component parent;
	private final CoreFactory factory;

	public ExportProjectAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("ExportProjectAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String choosen = Dialogs.chooseImageFormat(parent);

		if (choosen == null) {
			return;
		}

		File file = Dialogs.selectDirectory(parent);

		if (file != null && file.exists()) {
			ProjectWriter writer = new ProjectWriter(file, choosen);
			factory.traverse(writer);
		}
	}

}
