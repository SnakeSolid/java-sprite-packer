package ru.snake.spritepacker.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class AboutAction extends BasicAction implements Action {

	private final Component parent;

	public AboutAction(Component parent) {
		this.parent = parent;

		putValue(NAME, "About...");
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Dialogs.message(parent, R.APPLICATION_NAME);
	}

}
