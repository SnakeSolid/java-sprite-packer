package ru.snake.spritepacker.actions.sprite;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.Animation;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.render.TextureCellRenderer;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class SetSpriteTextureAction extends BasicAction implements Action {

	private static final String ICON_NAME = "texture"; //$NON-NLS-1$

	private static final int TEXTURES_LIST_HEIGHT = 300;

	private final Component parent;
	private final CoreFactory factory;

	public SetSpriteTextureAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("SetSpriteTextureAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);

		setIcon(ICON_NAME, true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Animation animation = factory.getActiveAnimation();
		Sprite sprite = factory.getActiveSprite();

		if (animation == null || sprite == null) {
			Dialogs.warning(parent,
					Messages.getString("SetSpriteTextureAction.NO_SPRITE")); //$NON-NLS-1$

			return;
		}

		// ===================================================

		ListModel textmodel = factory.getTexturesModel();
		JLabel texlabel = new JLabel(
				Messages.getString("SetSpriteTextureAction.LABEL_TEXTURE")); //$NON-NLS-1$
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

		texlist.setSelectedValue(sprite.texture, true);

		// ===================================================

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(texlabel, Alignment.LEADING)
				.addComponent(texscroll));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addComponent(texlabel)
				.addComponent(texscroll, TEXTURES_LIST_HEIGHT,
						TEXTURES_LIST_HEIGHT, TEXTURES_LIST_HEIGHT));

		// ---------------------------------------------------

		int result = JOptionPane
				.showConfirmDialog(
						parent,
						panel,
						Messages.getString("SetSpriteTextureAction.TITLE"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
						JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			Object selected = texlist.getSelectedValue();

			if (selected != null) {
				sprite.texture = (Texture) texlist.getSelectedValue();
			}
		}

		factory.updateSprites();
	}
}
