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
public class RenameAnimationAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public RenameAnimationAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Rename animation");
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);

		setIcon("rename", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent, R.SELECT_ANIMATION_BEFORE);

			return;
		}

		String newnane = Dialogs.inputString(parent, R.CHOOSE_ANIMATION_NAME,
				animation.name);

		if (newnane != null && !newnane.isEmpty()) {
			animation.name = newnane;

			factory.updateAnimations();
		}
	}

}
