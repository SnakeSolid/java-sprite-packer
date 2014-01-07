package ru.snake.spritepacker.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class RemoveTextureAction extends BasicAction implements Action {

	private static final String ICON_NAME = "remove";

	private final Component parent;
	private final CoreFactory factory;

	public RemoveTextureAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("RemoveTextureAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);

		setIcon(ICON_NAME, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Texture texture = factory.getActiveTexture();

		if (texture == null) {
			Dialogs.warning(parent,
					Messages.getString("RemoveTextureAction.NO_TEXTURE")); //$NON-NLS-1$

			return;
		}

		if (Dialogs.confirm(parent,
				Messages.getString("RemoveTextureAction.MESSAGE"))) { //$NON-NLS-1$
			factory.removeTexture(texture);
		}
	}

}
