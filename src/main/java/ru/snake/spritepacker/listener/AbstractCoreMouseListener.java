package ru.snake.spritepacker.listener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ru.snake.spritepacker.core.CoreFactory;

public abstract class AbstractCoreMouseListener implements MouseListener {

	protected final Component parent;
	protected final CoreFactory factory;

	public AbstractCoreMouseListener(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!isValueValid()) {
			return;
		}

		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			processClick();
		} else if (e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			processDoubleClick();
		}
	}

	protected abstract boolean isValueValid();

	protected void processClick() {
	}

	protected void processDoubleClick() {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
