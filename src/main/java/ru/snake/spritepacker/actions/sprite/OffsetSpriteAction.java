package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.component.SpriteView;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.listener.OffsetSpriteListener;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class OffsetSpriteAction extends BasicAction implements Action {

	private static final int MIN_SPRITE_SIZE = 400;
	private static final int MIN_OFFSET_VALUE = -512;
	private static final int MAX_OFFSET_VALUE = 512;

	private final Component parent;
	private final CoreFactory factory;

	public OffsetSpriteAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Offset sprite...");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);

		setIcon("align", true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();
		Sprite sprite = factory.getActiveSprite();

		if (animation == null || sprite == null) {
			Dialogs.warning(parent, R.SELECT_SPRITE_BEFORE);

			return;
		}

		int baseOffsetX = sprite.offsetX;
		int baseOffsetY = sprite.offsetY;

		// ===================================================

		SpriteView view = new SpriteView(sprite);

		// ---------------------------------------------------

		JLabel offsxlabel = new JLabel(R.OFFSET_ANIMATION_X);
		JLabel offsylabel = new JLabel(R.OFFSET_ANIMATION_Y);

		SpinnerModel offsxmodel = new SpinnerNumberModel(baseOffsetX,
				MIN_OFFSET_VALUE, MAX_OFFSET_VALUE, 1);
		SpinnerModel offsymodel = new SpinnerNumberModel(baseOffsetY,
				MIN_OFFSET_VALUE, MAX_OFFSET_VALUE, 1);

		JSpinner offsxspin = new JSpinner(offsxmodel);
		JSpinner offsyspin = new JSpinner(offsymodel);

		// ---------------------------------------------------

		ChangeListener listener = new OffsetSpriteListener(offsxmodel,
				offsymodel, view, sprite);

		offsxmodel.addChangeListener(listener);
		offsymodel.addChangeListener(listener);

		// ===================================================

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.addComponent(view, MIN_SPRITE_SIZE, MIN_SPRITE_SIZE,
						GroupLayout.DEFAULT_SIZE)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING, false)
												.addComponent(offsxlabel)
												.addComponent(offsylabel))
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING, false)
												.addComponent(offsxspin)
												.addComponent(offsyspin))));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addComponent(view, MIN_SPRITE_SIZE, MIN_SPRITE_SIZE,
						GroupLayout.DEFAULT_SIZE)
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(offsxlabel)
								.addComponent(offsxspin))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(offsylabel)
								.addComponent(offsyspin)));

		// ---------------------------------------------------

		int result = JOptionPane.showConfirmDialog(parent, panel,
				R.APPLICATION_NAME, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result != JOptionPane.YES_OPTION) {
			sprite.offsetX = baseOffsetX;
			sprite.offsetY = baseOffsetY;
		} else {
			animation.translate(0, 0);
		}

		factory.updateSprites();
	}

}
