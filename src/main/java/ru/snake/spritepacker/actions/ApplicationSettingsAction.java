package ru.snake.spritepacker.actions;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ru.snake.spritepacker.Configuration;
import ru.snake.spritepacker.Messages;

@SuppressWarnings("serial")
public class ApplicationSettingsAction extends AbstractAction implements Action {

	private static final int COLOR_WIDTH = 32;

	private final Component parent;

	public ApplicationSettingsAction(Component parent) {
		this.parent = parent;

		putValue(NAME, Messages.getString("ApplicationSettingsAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_I);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Configuration config = Configuration.getInstance();

		JLabel labelPanelGrid = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_PANEL_GRID")); //$NON-NLS-1$
		JLabel labelPanelFg = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_PANEL_FOREGROUND")); //$NON-NLS-1$
		JLabel labelPanelBg = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_PANEL_BACKGROUND")); //$NON-NLS-1$
		JLabel labelListThumb = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_LIST_THUMBNAIL")); //$NON-NLS-1$
		JLabel labelListGrid = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_LIST_GRID")); //$NON-NLS-1$
		JLabel labelListFg = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_LIST_FOREGROUND")); //$NON-NLS-1$
		JLabel labelListBg = new JLabel(
				Messages.getString("ApplicationSettingsAction.LABEL_LIST_BACKGROUND")); //$NON-NLS-1$

		JPanel colorPanelFg = new JPanel();
		JPanel colorPanelBg = new JPanel();
		JPanel colorListFg = new JPanel();
		JPanel colorListBg = new JPanel();

		SpinnerNumberModel modelPanelGrid = new SpinnerNumberModel(
				config.getPanelGridSize(), 1, 256, 1);
		SpinnerNumberModel modelListThumb = new SpinnerNumberModel(
				config.getListThumbSize(), 16, 256, 1);
		SpinnerNumberModel modelListGrid = new SpinnerNumberModel(
				config.getListGridSize(), 1, 128, 1);

		JSpinner spinPanelGrid = new JSpinner(modelPanelGrid);
		JSpinner spinListThumb = new JSpinner(modelListThumb);
		JSpinner spinListGrid = new JSpinner(modelListGrid);

		JButton butonPanelFg = new JButton(
				Messages.getString("ApplicationSettingsAction.BUTTON_COLOR")); //$NON-NLS-1$
		JButton butonPanelBg = new JButton(
				Messages.getString("ApplicationSettingsAction.BUTTON_COLOR")); //$NON-NLS-1$
		JButton butonListFg = new JButton(
				Messages.getString("ApplicationSettingsAction.BUTTON_COLOR")); //$NON-NLS-1$
		JButton butonListBg = new JButton(
				Messages.getString("ApplicationSettingsAction.BUTTON_COLOR")); //$NON-NLS-1$

		// ---------------------------------------------------

		Border colorBorder = BorderFactory.createEtchedBorder(Color.WHITE,
				Color.DARK_GRAY);

		colorPanelFg.setBorder(colorBorder);
		colorPanelBg.setBorder(colorBorder);
		colorListFg.setBorder(colorBorder);
		colorListBg.setBorder(colorBorder);

		colorPanelFg.setBackground(config.getPanelForeground());
		colorPanelBg.setBackground(config.getPanelBackground());
		colorListFg.setBackground(config.getListForeground());
		colorListBg.setBackground(config.getListBackground());

		butonPanelFg.addActionListener(new ColorAction(colorPanelFg));
		butonPanelBg.addActionListener(new ColorAction(colorPanelBg));
		butonListFg.addActionListener(new ColorAction(colorListFg));
		butonListBg.addActionListener(new ColorAction(colorListBg));

		// ---------------------------------------------------

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// @formatter:off
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(labelPanelGrid)
						.addComponent(labelPanelFg)
						.addComponent(labelPanelBg)
						.addComponent(labelListThumb)
						.addComponent(labelListGrid)
						.addComponent(labelListFg)
						.addComponent(labelListBg)
						)
						
				.addGroup(layout.createParallelGroup()
						.addComponent(spinPanelGrid)

						.addGroup(layout.createSequentialGroup()
								.addComponent(colorPanelFg, GroupLayout.DEFAULT_SIZE, COLOR_WIDTH, GroupLayout.DEFAULT_SIZE)
								.addComponent(butonPanelFg)
								.addContainerGap(0, Short.MAX_VALUE)
								)
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(colorPanelBg, GroupLayout.DEFAULT_SIZE, COLOR_WIDTH, GroupLayout.DEFAULT_SIZE)
								.addComponent(butonPanelBg)
								.addContainerGap(0, Short.MAX_VALUE)
								)
								
						.addComponent(spinListThumb)
						.addComponent(spinListGrid)
								
						.addGroup(layout.createSequentialGroup()
								.addComponent(colorListFg, GroupLayout.DEFAULT_SIZE, COLOR_WIDTH, GroupLayout.DEFAULT_SIZE)
								.addComponent(butonListFg)
								.addContainerGap(0, Short.MAX_VALUE)
								)
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(colorListBg, GroupLayout.DEFAULT_SIZE, COLOR_WIDTH, GroupLayout.DEFAULT_SIZE)
								.addComponent(butonListBg)
								.addContainerGap(0, Short.MAX_VALUE)
								)
						)
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelPanelGrid)
						.addComponent(spinPanelGrid)
						)
						
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelPanelFg)
						.addComponent(colorPanelFg)
						.addComponent(butonPanelFg)
						)
						
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelPanelBg)
						.addComponent(colorPanelBg)
						.addComponent(butonPanelBg)
						)
						
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelListThumb)
						.addComponent(spinListThumb)
						)
						
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelListGrid)
						.addComponent(spinListGrid)
						)
						
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelListFg)
						.addComponent(colorListFg)
						.addComponent(butonListFg)
						)
						
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelListBg)
						.addComponent(colorListBg)
						.addComponent(butonListBg)
						)
						
				);
		// @formatter:on

		layout.linkSize(SwingConstants.HORIZONTAL, colorPanelFg, colorPanelBg,
				colorListFg, colorListBg);
		layout.linkSize(SwingConstants.HORIZONTAL, spinPanelGrid,
				spinListThumb, spinListGrid);

		int result = JOptionPane.showConfirmDialog(parent, panel,
				Messages.getString("ApplicationSettingsAction.TITLE"), //$NON-NLS-1$
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			Integer panelGrid = (Integer) modelPanelGrid.getValue();
			Integer thumbSize = (Integer) modelListThumb.getValue();
			Integer gridSize = (Integer) modelListGrid.getValue();

			config.setPanelGridSize(panelGrid);
			config.setPanelForeground(colorPanelFg.getBackground());
			config.setPanelBackground(colorPanelBg.getBackground());
			config.setListThumbSize(thumbSize);
			config.setListGridSize(gridSize);
			config.setListForeground(colorListFg.getBackground());
			config.setListBackground(colorListBg.getBackground());

			try {
				config.commit();

				JOptionPane
						.showMessageDialog(
								parent,
								Messages.getString("ApplicationSettingsAction.SAVE_REBOOT"), //$NON-NLS-1$
								Messages.getString("ApplicationSettingsAction.SAVE_DONE"), //$NON-NLS-1$
								JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, e.getMessage(), Messages
						.getString("ApplicationSettingsAction.SAVE_ERROR"), //$NON-NLS-1$
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class ColorAction extends AbstractAction {

		private final JComponent component;

		public ColorAction(JComponent component) {
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			Color color = component.getBackground();
			JColorChooser chooser = new JColorChooser(color);

			int result = JOptionPane.showConfirmDialog(null, chooser, Messages
					.getString("ApplicationSettingsAction.CHOOSE_COLOR"), //$NON-NLS-1$
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				component.setBackground(chooser.getColor());
			}
		}

	}

}
