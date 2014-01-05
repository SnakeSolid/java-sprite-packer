package ru.snake.spritepacker.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.core.CoreFactory;

@SuppressWarnings("serial")
public class MergeSimilarAction extends BasicAction implements Action {

	private final CoreFactory factory;

	public MergeSimilarAction(CoreFactory factory) {
		this.factory = factory;

		putValue(NAME, "Merge similar textures");
		putValue(MNEMONIC_KEY, KeyEvent.VK_M);

		setIcon("merge", false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		factory.mergeTextures();
	}

}
