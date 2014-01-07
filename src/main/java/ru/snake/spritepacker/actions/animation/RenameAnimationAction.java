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
public class RenameAnimationAction extends BasicAction implements Action {

	private static final String ICON_NAME = "rename";

	private final Component parent;
	private final CoreFactory factory;

	public RenameAnimationAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("RenameAnimationAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent,
					Messages.getString("RenameAnimationAction.NO_ANIMATION")); //$NON-NLS-1$

			return;
		}

		String newnane = Dialogs
				.inputString(
						parent,
						Messages.getString("RenameAnimationAction.MESSAGE"), animation.name); //$NON-NLS-1$

		if (newnane != null && !newnane.isEmpty()) {
			animation.name = newnane;

			factory.updateAnimations();
		}
	}

}
