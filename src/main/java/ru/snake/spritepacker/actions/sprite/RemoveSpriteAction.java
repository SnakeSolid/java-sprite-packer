package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class RemoveSpriteAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public RemoveSpriteAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Remove sprite");
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);

		setIcon("remove", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();
		Sprite sprite = factory.getActiveSprite();

		if (animation == null || sprite == null) {
			Dialogs.warning(parent, R.SELECT_SPRITE_BEFORE);

			return;
		}

		String message;

		message = String.format(R.SURE_DELETE_SPRITE, sprite.name,
				animation.name);

		if (Dialogs.confirm(parent, message)) {
			factory.removeSprite(animation, sprite);
		}
	}

}
