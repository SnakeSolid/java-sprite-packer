package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.render.TextureCellRenderer;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class CreateSpriteAction extends BasicAction implements Action {

	private static final String ICON_NAME = "new"; //$NON-NLS-1$
	private static final String NAME_FORMAT = "sprite-%d";

	private static final int TEXTURES_LIST_HEIGHT = 300;

	private final Component parent;
	private final CoreFactory factory;

	public CreateSpriteAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("CreateSpriteAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();

		if (animation == null) {
			Dialogs.warning(parent,
					Messages.getString("CreateSpriteAction.NO_ANIMATION")); //$NON-NLS-1$

			return;
		}

		Random random = new Random();
		String defaultname;

		defaultname = String.format(NAME_FORMAT, random.nextInt());

		// ===================================================

		JLabel offslabel = new JLabel(
				Messages.getString("CreateSpriteAction.LABEL_OFFSET")); //$NON-NLS-1$
		SpinnerModel offsxmodel = new SpinnerNumberModel(0, Integer.MIN_VALUE,
				Integer.MAX_VALUE, 1);
		SpinnerModel offsymodel = new SpinnerNumberModel(0, Integer.MIN_VALUE,
				Integer.MAX_VALUE, 1);
		JSpinner offxspin = new JSpinner(offsxmodel);
		JSpinner offyspin = new JSpinner(offsymodel);

		// ---------------------------------------------------

		JLabel namelabel = new JLabel(
				Messages.getString("CreateSpriteAction.LABEL_NAME")); //$NON-NLS-1$
		JTextField namefield = new JTextField(defaultname, 20);

		// ---------------------------------------------------

		ListModel textmodel = factory.getTexturesModel();
		JLabel texlabel = new JLabel(
				Messages.getString("CreateSpriteAction.LABEL_TEXTURE")); //$NON-NLS-1$
		JList texlist = new JList(textmodel);
		JScrollPane texscroll = new JScrollPane(texlist);
		ListCellRenderer texrender = new TextureCellRenderer();

		texlist.setCellRenderer(texrender);

		texlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		texlist.setVisibleRowCount(-1);

		texscroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		texscroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// ===================================================

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING, false)
												.addComponent(namelabel)
												.addComponent(offslabel))
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING, false)
												.addComponent(namefield)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		offxspin)
																.addComponent(
																		offyspin))))
				.addComponent(texlabel, Alignment.LEADING)
				.addComponent(texscroll));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(namelabel)
								.addComponent(namefield))

				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(offslabel).addComponent(offxspin)
								.addComponent(offyspin))

				.addComponent(texlabel)
				.addComponent(texscroll, TEXTURES_LIST_HEIGHT,
						TEXTURES_LIST_HEIGHT, TEXTURES_LIST_HEIGHT));

		layout.linkSize(SwingConstants.HORIZONTAL, offxspin, offyspin);

		while (true) {
			int result = JOptionPane
					.showConfirmDialog(
							parent,
							panel,
							Messages.getString("CreateSpriteAction.TITLE"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
							JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				Object offsxselected = offsxmodel.getValue();
				Object offsyselected = offsymodel.getValue();
				String spritename = namefield.getText();
				Object texselected = texlist.getSelectedValue();

				if (spritename.isEmpty()) {
					spritename = defaultname;
				}

				if (texselected == null) {
					Dialogs.error(parent,
							Messages.getString("CreateSpriteAction.NO_TEXTURE")); //$NON-NLS-1$

					continue;
				}

				int offsetx = (Integer) offsxselected;
				int offsety = (Integer) offsyselected;
				Texture texture = (Texture) texselected;

				Sprite sptere = new Sprite(offsetx, offsety, spritename,
						texture);

				factory.addSprite(animation, sptere);

				break;
			} else {
				break;
			}
		}
	}

}
