package ru.snake.spritepacker.listener;

import java.awt.Component;
import java.awt.event.MouseListener;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.util.Dialogs;

public class SpriteMouseListener extends AbstractCoreMouseListener implements
		MouseListener {

	public SpriteMouseListener(Component parent, CoreFactory factory) {
		super(parent, factory);
	}

	@Override
	protected boolean isValueValid() {
		return factory.getActiveSprite() != null;
	}

	@Override
	protected void processDoubleClick() {
		Sprite sprite = factory.getActiveSprite();

		String newnane = Dialogs.inputString(parent, R.CHOOSE_SPRITE_NAME,
				sprite.name);

		if (newnane == null || newnane.isEmpty()) {
			return;
		}

		sprite.name = newnane;

		factory.updateSprites();
	}

}
