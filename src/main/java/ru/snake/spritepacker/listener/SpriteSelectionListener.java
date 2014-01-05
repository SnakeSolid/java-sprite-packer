package ru.snake.spritepacker.listener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;

public class SpriteSelectionListener implements ListSelectionListener {

	private final JList list;
	private final CoreFactory factory;

	public SpriteSelectionListener(JList list, CoreFactory factory) {
		this.list = list;
		this.factory = factory;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object value = list.getSelectedValue();

		if (value == null) {
			factory.setActiveSprite(null);

			return;
		}

		if (!(value instanceof Sprite)) {
			return;
		}

		Sprite sprite = (Sprite) value;

		factory.setActiveSprite(sprite);
	}

}
