package ru.snake.spritepacker.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class ExitApplicationAction extends BasicAction implements Action {

	private final Window parent;

	public ExitApplicationAction(Window mainFrame) {
		this.parent = mainFrame;

		putValue(NAME, "Exit");
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);

		setIcon("quit", false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Dialogs.confirm(parent, "Do you really want to exit application.")) {
			parent.setVisible(false);
			parent.dispose();
		}
	}

}
