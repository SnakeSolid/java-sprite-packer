package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class RenameSpriteAction extends BasicAction implements Action {

	private static final String ICON_NAME = "rename"; //$NON-NLS-1$

	private final Component parent;
	private final CoreFactory factory;

	public RenameSpriteAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("RenameSpriteAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Sprite sprite = factory.getActiveSprite();

		if (sprite == null) {
			Dialogs.warning(parent,
					Messages.getString("RenameSpriteAction.NO_SPRITE")); //$NON-NLS-1$

			return;
		}

		String newnane = Dialogs.inputString(parent,
				Messages.getString("RenameSpriteAction.MESSAGE"), //$NON-NLS-1$
				sprite.name);

		if (newnane != null && !newnane.isEmpty()) {
			sprite.name = newnane;

			factory.updateSprites();
		}
	}

}
