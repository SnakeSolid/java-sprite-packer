package ru.snake.spritepacker.listener;

import java.awt.Point;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.snake.spritepacker.component.AnimationView;
import ru.snake.spritepacker.core.Animation;

public class OffsetAnimationListener implements ChangeListener {

	private final SpinnerModel xmodel;
	private final SpinnerModel ymodel;
	private final AnimationView view;
	private final Animation animation;

	public OffsetAnimationListener(SpinnerModel xmodel, SpinnerModel ymodel,
			AnimationView view, Animation animation) {
		this.xmodel = xmodel;
		this.ymodel = ymodel;
		this.view = view;
		this.animation = animation;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Point currentOffset = animation.getOffset();

		int x = (Integer) xmodel.getValue();
		int y = (Integer) ymodel.getValue();

		int offsetX = x - currentOffset.x;
		int offsetY = y - currentOffset.y;

		animation.translate(offsetX, offsetY);

		view.repaint();
	}
}
