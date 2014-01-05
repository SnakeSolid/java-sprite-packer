package ru.snake.spritepacker.actions.animation;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;

@SuppressWarnings("serial")
public class CreateAnimationAction extends BasicAction implements Action {

	private final CoreFactory factory;

	public CreateAnimationAction(CoreFactory factory) {
		this.factory = factory;

		putValue(NAME, "Create animation");
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);

		setIcon("new", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Random random = new Random();
		List<Sprite> spterelist = Collections.emptyList();
		String defaultname;

		defaultname = String.format(R.DEFAULT_ANIMATION_NAME_FORMAT,
				random.nextInt());

		factory.createAnimation(defaultname, spterelist);
	}

}
