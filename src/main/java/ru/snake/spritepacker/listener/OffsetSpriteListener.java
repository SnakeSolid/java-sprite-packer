package ru.snake.spritepacker.listener;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.snake.spritepacker.component.SpriteView;
import ru.snake.spritepacker.core.Sprite;

public class OffsetSpriteListener implements ChangeListener {

	private final SpinnerModel xmodel;
	private final SpinnerModel ymodel;
	private final SpriteView view;
	private final Sprite sprite;

	public OffsetSpriteListener(SpinnerModel xmodel, SpinnerModel ymodel,
			SpriteView view, Sprite sprite) {
		this.xmodel = xmodel;
		this.ymodel = ymodel;
		this.view = view;
		this.sprite = sprite;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int offsetX = (Integer) xmodel.getValue();
		int offsetY = (Integer) ymodel.getValue();

		sprite.offsetX = offsetX;
		sprite.offsetY = offsetY;

		view.repaint();
	}
}
