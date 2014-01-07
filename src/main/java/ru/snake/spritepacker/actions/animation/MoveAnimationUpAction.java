package ru.snake.spritepacker.actions.animation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class MoveAnimationUpAction extends BasicAction implements Action {

	private static final String ICON_NAME = "moveup";

	private final Component parent;
	private final CoreFactory factory;

	public MoveAnimationUpAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("MoveAnimationUpAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent,
					Messages.getString("MoveAnimationUpAction.NO_ANIMATION")); //$NON-NLS-1$

			return;
		}

		factory.moveAnimationUp(animation);
	}

}
