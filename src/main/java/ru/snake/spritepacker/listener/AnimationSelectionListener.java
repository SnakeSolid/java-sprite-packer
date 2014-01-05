package ru.snake.spritepacker.listener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;

public class AnimationSelectionListener implements ListSelectionListener {

	private final JList list;
	private final CoreFactory factory;

	public AnimationSelectionListener(JList list, CoreFactory factory) {
		this.list = list;
		this.factory = factory;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object value = list.getSelectedValue();

		if (value == null) {
			factory.setActiveAnimation(null);

			return;
		}

		if (!(value instanceof Animation)) {
			return;
		}

		Animation animation = (Animation) value;

		factory.setActiveAnimation(animation);
	}

}
