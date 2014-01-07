package ru.snake.spritepacker.actions.animation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class CenterAnimationAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public CenterAnimationAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("CenterAnimationAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent,
					Messages.getString("CenterAnimationAction.NO_ANIMATION")); //$NON-NLS-1$

			return;
		}

		if (Dialogs.confirm(parent,
				Messages.getString("CenterAnimationAction.MESSAGE"))) { //$NON-NLS-1$
			List<Sprite> list = animation.getSprites();

			for (Sprite each : list) {
				each.offsetX = each.getWidth() / 2;
				each.offsetY = each.getHeight() / 2;
			}

			factory.updateAnimations();
		}
	}
}
