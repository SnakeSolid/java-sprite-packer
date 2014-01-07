package ru.snake.spritepacker.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.core.CoreFactory;

@SuppressWarnings("serial")
public class MergeSimilarAction extends BasicAction implements Action {

	private static final String ICON_NAME = "merge";

	private final CoreFactory factory;

	public MergeSimilarAction(CoreFactory factory) {
		this.factory = factory;

		putValue(NAME, Messages.getString("MergeSimilarAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_M);

		setIcon(ICON_NAME, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		factory.mergeTextures();
	}

}
