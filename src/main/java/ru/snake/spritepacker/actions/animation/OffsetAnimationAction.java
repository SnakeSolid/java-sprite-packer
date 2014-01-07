package ru.snake.spritepacker.actions.animation;

import java.awt.Component;
import java.awt.Point;
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

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.component.AnimationView;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.listener.OffsetAnimationListener;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class OffsetAnimationAction extends BasicAction implements Action {

	private static final String ICON_NAME = "align";

	private final Component parent;
	private final CoreFactory factory;

	public OffsetAnimationAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("OffsetAnimationAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent,
					Messages.getString("OffsetAnimationAction.NO_ANIMATION")); //$NON-NLS-1$

			return;
		}

		Point baseOffset = animation.getOffset();

		// ===================================================

		AnimationView view = new AnimationView(animation);

		// ---------------------------------------------------

		JLabel offsxlabel = new JLabel(
				Messages.getString("OffsetAnimationAction.LABEL_X_OFFSET")); //$NON-NLS-1$
		JLabel offsylabel = new JLabel(
				Messages.getString("OffsetAnimationAction.LABEL_Y_OFFSET")); //$NON-NLS-1$

		SpinnerModel offsxmodel = new SpinnerNumberModel(baseOffset.x,
				MIN_OFFSET_VALUE, MAX_OFFSET_VALUE, 1);
		SpinnerModel offsymodel = new SpinnerNumberModel(baseOffset.y,
				MIN_OFFSET_VALUE, MAX_OFFSET_VALUE, 1);

		JSpinner offsxspin = new JSpinner(offsxmodel);
		JSpinner offsyspin = new JSpinner(offsymodel);

		// ---------------------------------------------------

		ChangeListener listener = new OffsetAnimationListener(offsxmodel,
				offsymodel, view, animation);

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
				.addComponent(view, MIN_ANIMATION_SIZE, MIN_ANIMATION_SIZE,
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
				.addComponent(view, MIN_ANIMATION_SIZE, MIN_ANIMATION_SIZE,
						GroupLayout.DEFAULT_SIZE)
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(offsxlabel)
								.addComponent(offsxspin))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(offsylabel)
								.addComponent(offsyspin)));

		int result = JOptionPane
				.showConfirmDialog(
						parent,
						panel,
						Messages.getString("OffsetAnimationAction.TITLE"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
						JOptionPane.PLAIN_MESSAGE);

		// ---------------------------------------------------

		if (result != JOptionPane.YES_OPTION) {
			Point currentOffset = animation.getOffset();

			int offsetX = baseOffset.x - currentOffset.x;
			int offsetY = baseOffset.y - currentOffset.y;

			animation.translate(offsetX, offsetY);
		}

		factory.updateAnimations();
	}

	private static final int MIN_ANIMATION_SIZE = 400;
	private static final int MIN_OFFSET_VALUE = -512;
	private static final int MAX_OFFSET_VALUE = 512;

}
