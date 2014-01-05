package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class RenameSpriteAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public RenameSpriteAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Rename sprite");
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);

		setIcon("rename", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Sprite sprite = factory.getActiveSprite();

		if (sprite == null) {
			Dialogs.warning(parent, R.SELECT_SPRITE_BEFORE);

			return;
		}

		String newnane = Dialogs.inputString(parent, R.CHOOSE_SPRITE_NAME,
				sprite.name);

		if (newnane != null && !newnane.isEmpty()) {
			sprite.name = newnane;

			factory.updateSprites();
		}
	}

}
