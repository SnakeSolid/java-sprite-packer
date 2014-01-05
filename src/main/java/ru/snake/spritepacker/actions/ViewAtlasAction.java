package ru.snake.spritepacker.actions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.component.TextureAtlas;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.packer.ImageData;
import ru.snake.spritepacker.core.packer.PackerOutput;
import ru.snake.spritepacker.util.Dialogs;

@SuppressWarnings("serial")
public class ViewAtlasAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public ViewAtlasAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "View atlas");
		putValue(MNEMONIC_KEY, KeyEvent.VK_V);

		setIcon("atlas", false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PackerOutput output = factory.createTextureAtlas();

		if (output.atlasSizes.isEmpty()) {
			Dialogs.warning(parent, R.CANNT_CREATE_ATLAS);

			return;
		}

		if (output.packedImages.isEmpty()) {
			Dialogs.warning(parent, R.CANNT_CREATE_ATLAS);

			return;
		}

		JTabbedPane atlasTabs = new JTabbedPane(JTabbedPane.TOP);

		for (Entry<Integer, Dimension> atlasEntry : output.atlasSizes
				.entrySet()) {
			Collection<ImageData> textures = new LinkedList<ImageData>();
			int index = atlasEntry.getKey();

			for (ImageData eachData : output.packedImages) {
				if (eachData.atlas == index) {
					textures.add(eachData);
				}
			}

			Dimension size = atlasEntry.getValue();
			String sizeStr = String.format(R.ATLAS_SIZE_FORMAT, size.width,
					size.height);

			LayoutManager atlasLayout = new BorderLayout(4, 4);
			JPanel atlasPane = new JPanel(atlasLayout);
			TextureAtlas atlas = new TextureAtlas(size.width, size.height,
					textures);
			JLabel sizeLabel = new JLabel(sizeStr);

			atlasPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

			atlasPane.add(atlas, BorderLayout.CENTER);
			atlasPane.add(sizeLabel, BorderLayout.PAGE_END);

			atlasTabs
					.add(String.valueOf(output.getAtlasName(index)), atlasPane);
		}

		JOptionPane.showMessageDialog(parent, atlasTabs, R.APPLICATION_NAME,
				JOptionPane.PLAIN_MESSAGE);
	}

}
