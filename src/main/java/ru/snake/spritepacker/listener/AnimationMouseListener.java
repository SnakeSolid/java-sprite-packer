package ru.snake.spritepacker.listener;

import java.awt.Component;
import java.awt.event.MouseListener;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Dialogs;

public class AnimationMouseListener extends AbstractCoreMouseListener implements
		MouseListener {

	public AnimationMouseListener(Component parent, CoreFactory factory) {
		super(parent, factory);
	}

	@Override
	protected boolean isValueValid() {
		return factory.getActiveAnimation() != null;
	}

	@Override
	protected void processDoubleClick() {
		Animation animation = factory.getActiveAnimation();

		String newnane = Dialogs.inputString(parent,
				Messages.getString("AnimationMouseListener.MESSAGE"), //$NON-NLS-1$
				animation.name);

		if (newnane == null || newnane.isEmpty()) {
			return;
		}

		animation.name = newnane;

		factory.updateAnimations();
	}

}
