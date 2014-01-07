package ru.snake.spritepacker.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class ExitApplicationAction extends BasicAction implements Action {

	private static final String ICON_NAME = "quit";

	private final Window parent;

	public ExitApplicationAction(Window mainFrame) {
		this.parent = mainFrame;

		putValue(NAME, Messages.getString("ExitApplicationAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);

		setIcon(ICON_NAME, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Dialogs.confirm(parent,
				Messages.getString("ExitApplicationAction.MESSAGE"))) { //$NON-NLS-1$
			parent.setVisible(false);
			parent.dispose();
		}
	}

}
