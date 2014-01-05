package ru.snake.spritepacker.actions.project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.util.Util;

@SuppressWarnings("serial")
public class ProjectSettingsAction extends BasicAction implements Action {

	private static final int MAX_SIZE_VALUE = 2048;
	private static final int MAX_PADDING_VALUE = 256;

	private final Component parent;
	private final CoreFactory factory;

	public ProjectSettingsAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Settings...");
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);

		setIcon("settings", false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int minTextureSize = Util.nearestPowerOf2(2 * factory.getMargin() + 1);

		JLabel marginlabel = new JLabel(R.SETTINGS_MARGIN);
		JLabel paddinglabel = new JLabel(R.SETTINGS_PADDING);
		JLabel sizelabel = new JLabel(R.SETTINGS_ATLAS_SIZE);

		SpinnerModel marginmodel = new SpinnerNumberModel(factory.getMargin(),
				0, MAX_PADDING_VALUE, 1);
		SpinnerModel paddingmodel = new SpinnerNumberModel(
				factory.getPadding(), 0, MAX_PADDING_VALUE, 1);
		SpinnerModel widthmodel = new SpinnerNumberModel(factory.getMaxWidth(),
				minTextureSize, MAX_SIZE_VALUE, 1);
		SpinnerModel heightmodel = new SpinnerNumberModel(
				factory.getMaxHeight(), minTextureSize, MAX_SIZE_VALUE, 1);

		JSpinner marginspin = new JSpinner(marginmodel);
		JSpinner paddingspin = new JSpinner(paddingmodel);
		JSpinner widthspin = new JSpinner(widthmodel);
		JSpinner heightspin = new JSpinner(heightmodel);

		JCheckBox atlasbox = new JCheckBox(R.SETTINGS_ATLAS_PER_ANIMATION);

		atlasbox.setSelected(factory.isAtlasPerAnim());

		// ---------------------------------------------------

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(marginlabel)
								.addComponent(paddinglabel)
								.addComponent(sizelabel))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(marginspin)
								.addComponent(paddingspin)
								.addComponent(atlasbox)
								.addGroup(
										layout.createSequentialGroup()
												.addComponent(widthspin)
												.addComponent(heightspin))));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(marginlabel)
								.addComponent(marginspin))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(paddinglabel)
								.addComponent(paddingspin))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(atlasbox))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(sizelabel)
								.addComponent(widthspin)
								.addComponent(heightspin)));

		layout.linkSize(SwingConstants.HORIZONTAL, widthspin, heightspin);

		int result = JOptionPane.showConfirmDialog(parent, panel,
				R.APPLICATION_NAME, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			factory.setMargin((Integer) marginmodel.getValue());
			factory.setPadding((Integer) paddingmodel.getValue());

			factory.setAtlasPerAnim(atlasbox.isSelected());

			factory.setMaxWidth((Integer) widthmodel.getValue());
			factory.setMaxHeight((Integer) heightmodel.getValue());
		}
	}

}
