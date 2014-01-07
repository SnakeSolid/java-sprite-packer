package ru.snake.spritepacker.actions.save;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.writer.AnimationWriter;

@SuppressWarnings("serial")
public class ExportAnimationAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public ExportAnimationAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("ExportAnimationAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent,
					Messages.getString("ExportAnimationAction.MESSAGE")); //$NON-NLS-1$

			return;
		}

		String choosen = Dialogs.chooseImageFormat(parent);

		if (choosen == null) {
			return;
		}

		File file = Dialogs.selectDirectory(parent);

		if (file != null && file.exists()) {
			AnimationWriter writer = new AnimationWriter(file, animation.name,
					choosen);
			factory.traverse(writer);
		}
	}

}
