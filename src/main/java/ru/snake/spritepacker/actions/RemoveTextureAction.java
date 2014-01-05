package ru.snake.spritepacker.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class RemoveTextureAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public RemoveTextureAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Remove texture");
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);

		setIcon("remove", false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Texture texture = factory.getActiveTexture();

		if (texture == null) {
			Dialogs.warning(parent, R.SELECT_TEXTURE_BEFORE_DELETE);

			return;
		}

		if (Dialogs.confirm(parent, R.SURE_DELETE_TEXTURE)) {
			factory.removeTexture(texture);
		}
	}

}
