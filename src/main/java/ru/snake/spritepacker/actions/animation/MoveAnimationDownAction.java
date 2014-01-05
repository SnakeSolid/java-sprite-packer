package ru.snake.spritepacker.actions.animation;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class MoveAnimationDownAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public MoveAnimationDownAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Move animation down");
		putValue(MNEMONIC_KEY, KeyEvent.VK_D);

		setIcon("movedown", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent, R.SELECT_ANIMATION_BEFORE);

			return;
		}

		factory.moveAnimationDown(animation);
	}

}
