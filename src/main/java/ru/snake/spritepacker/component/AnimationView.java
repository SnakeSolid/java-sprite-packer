package ru.snake.spritepacker.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.CoreSubscriber;

@SuppressWarnings("serial")
public class AnimationView extends CheckerPanel implements MouseMotionListener,
		CoreSubscriber {

	private static final int FRAMES_LINE_HEIGHT = 16;

	private final Font font;
	private final CoreFactory factory;

	private Animation animation;

	private int frame;

	public AnimationView() {
		this(null, null);
	}

	public AnimationView(CoreFactory factory) {
		this(factory, null);
	}

	public AnimationView(Animation animation) {
		this(null, animation);
	}

	public AnimationView(CoreFactory factory, Animation animation) {
		this.factory = factory;
		this.animation = animation;
		this.frame = 0;

		if (factory != null) {
			factory.subscribe(this);
		}

		if (animation != null) {
			int width = animation.getWidth();
			int height = animation.getHeight() + FRAMES_LINE_HEIGHT * 2;

			Dimension dimension = new Dimension(width, height);

			setMinimumSize(dimension);
			setPreferredSize(dimension);
		}

		font = new Font(Font.MONOSPACED, Font.BOLD, FRAMES_LINE_HEIGHT - 4);

		addMouseMotionListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (animation == null) {
			return;
		}

		int width = getWidth();
		int height = getHeight();
		int animwidth = animation.getWidth();
		int animheight = animation.getHeight();
		int imagex = width / 2;
		int imagey = height / 2;

		float scalex = (float) width / animwidth;
		float scaley = (float) height / animheight;
		float scale;

		if (scalex < scaley) {
			scale = scalex;
		} else {
			scale = scaley;
		}

		if (scale > 3.0f) {
			int imgscale = (int) Math.ceil(scale) - 2;

			animation.drawScaledTo(g, imagex, imagey, imgscale, frame);
		} else {
			animation.drawTo(g, imagex, imagey, frame);
		}

		g.setFont(font);

		int totalframes = animation.getFrameCount();

		for (int i = 0; i < totalframes; i++) {
			int x = i * width / totalframes;
			int w = width / totalframes;

			g.setColor(Color.BLACK);
			g.drawRect(x + w / 4, height - FRAMES_LINE_HEIGHT, w / 2,
					FRAMES_LINE_HEIGHT - 1);

			if (i != frame) {
				g.setColor(Color.LIGHT_GRAY);
			} else {
				g.setColor(Color.RED);
			}

			g.fillRect(x + 1 + w / 4, height - FRAMES_LINE_HEIGHT + 1,
					w / 2 - 1, FRAMES_LINE_HEIGHT - 2);

			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(i), x + 2 + w / 4, height - 3);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (animation == null) {
			return;
		}

		int totalframes = animation.getFrameCount();

		frame = totalframes * e.getX() / getWidth();

		cropFrame();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;

		frame = 0;

		repaint();
	}

	@Override
	public void notifyChanged() {
		animation = factory.getActiveAnimation();

		cropFrame();

		repaint();
	}

	private void cropFrame() {
		if (animation == null) {
			return;
		}

		int totalframes = animation.getFrameCount();

		if (frame < 0) {
			frame = 0;
		} else if (frame >= totalframes) {
			frame = totalframes - 1;
		}
	}

}
