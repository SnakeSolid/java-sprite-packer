package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class MoveSpriteUpAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public MoveSpriteUpAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Move sprite up");
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);

		setIcon("moveup", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();
		Sprite sprite = factory.getActiveSprite();

		if (animation == null || sprite == null) {
			Dialogs.warning(parent, R.SELECT_SPRITE_BEFORE);

			return;
		}

		List<Sprite> list = animation.getSprites();
		int index = list.indexOf(sprite);

		if (index != -1 && index > 0) {
			List<Sprite> ordered = new ArrayList<Sprite>(list);

			ordered.set(index, ordered.get(index - 1));
			ordered.set(index - 1, sprite);

			animation.setSprites(ordered);
			factory.updateSprites();
		}
	}

}
