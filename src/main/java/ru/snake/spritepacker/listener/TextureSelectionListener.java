package ru.snake.spritepacker.listener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Texture;

public class TextureSelectionListener implements ListSelectionListener {

	private final JList list;
	private final CoreFactory factory;

	public TextureSelectionListener(JList list, CoreFactory factory) {
		this.list = list;
		this.factory = factory;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object value = list.getSelectedValue();

		if (value == null) {
			factory.setActiveTexture(null);

			return;
		}

		if (!(value instanceof Texture)) {
			return;
		}

		Texture texture = (Texture) value;

		factory.setActiveTexture(texture);
	}

}
