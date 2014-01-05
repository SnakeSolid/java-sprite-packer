package ru.snake.spritepacker.actions.save;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.packer.PackerOutput;
import ru.snake.spritepacker.model.ImageWriterFormatModel;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.writer.AtlasImageWriter;
import ru.snake.spritepacker.writer.AtlasTextWriter;
import ru.snake.spritepacker.writer.JoinTextWriter;

@SuppressWarnings("serial")
public class ExportAtlasAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;

	public ExportAtlasAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, "Export atlas...");
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);

		setIcon("atlas", false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String atlasPrefix = factory.getPreference("atlas-prefix", "");
		String atlasPostfix = factory.getPreference("atlas-postfix", ".png");
		String atlasFormat = factory.getPreference("atlas-format", "png");

		String descriptionPrefix = factory.getPreference("description-prefix",
				"description");
		String descriptionPostfix = factory.getPreference(
				"description-postfix", ".txt");
		String descriptionFormat = factory
				.getPreference(
						"description-format",
						"{animation.id};{animation.name};{atlas.height};{atlas.id};{atlas.name};{atlas.width};{sprite.offset.bottom};{sprite.id};{sprite.name};{sprite.offsetx};{sprite.offsety};{sprite.offset.right};{texture.bottom};{texture.height};{texture.left};{texture.right};{texture.top};{texture.width}");

		boolean descriptionJoin = Boolean.parseBoolean(factory.getPreference(
				"description-join", "false"));

		// ===================================================

		JLabel atlasLabel = new JLabel("Atlas file name:");
		JTextField atlasPrefixText = new JTextField(atlasPrefix, 10);
		JLabel atlasFileLabel = new JLabel("name");
		JTextField atlasProstfixText = new JTextField(atlasPostfix, 10);

		// ---------------------------------------------------

		ImageWriterFormatModel atlasFormatModel = new ImageWriterFormatModel(
				atlasFormat);
		JLabel atlasFormatLabel = new JLabel("Atlas file format:");
		JComboBox atlasFormatBox = new JComboBox(atlasFormatModel);

		// ---------------------------------------------------

		JLabel descriptionLabel = new JLabel("Description file name:");
		JTextField descriptionPrefixText = new JTextField(descriptionPrefix, 10);
		JLabel descriptionFileLabel = new JLabel("name");
		JTextField descriptionProstfixText = new JTextField(descriptionPostfix,
				10);
		// ---------------------------------------------------

		JLabel descFormatLabel = new JLabel("Description output format:");
		JTextArea descFormatText = new JTextArea(descriptionFormat, 5, 20);
		JScrollPane descFormatScroll = new JScrollPane(descFormatText);

		descFormatText.setLineWrap(true);
		descFormatText.setWrapStyleWord(true);

		descFormatScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// ---------------------------------------------------

		JCheckBox descJoinBox = new JCheckBox(
				"Join description for multiple atlases.", descriptionJoin);

		// ---------------------------------------------------

		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.TRAILING, false)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING, false)
												.addComponent(atlasLabel)
												.addComponent(atlasFormatLabel)
												.addComponent(descriptionLabel))
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING, false)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		atlasPrefixText)
																.addComponent(
																		atlasFileLabel)
																.addComponent(
																		atlasProstfixText))
												.addComponent(atlasFormatBox)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		descriptionPrefixText)
																.addComponent(
																		descriptionFileLabel)
																.addComponent(
																		descriptionProstfixText))
												.addComponent(descJoinBox)))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(descFormatLabel)
								.addComponent(descFormatScroll)));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(atlasLabel)
								.addComponent(atlasPrefixText)
								.addComponent(atlasFileLabel)
								.addComponent(atlasProstfixText))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(atlasFormatLabel)
								.addComponent(atlasFormatBox))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(descriptionLabel)
								.addComponent(descriptionPrefixText)
								.addComponent(descriptionFileLabel)
								.addComponent(descriptionProstfixText))
				.addComponent(descFormatLabel).addComponent(descFormatScroll)
				.addComponent(descJoinBox));

		layout.linkSize(SwingConstants.HORIZONTAL, atlasPrefixText,
				atlasProstfixText);

		int result = JOptionPane.showConfirmDialog(parent, panel,
				R.APPLICATION_NAME, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			atlasPrefix = atlasPrefixText.getText();
			atlasPostfix = atlasProstfixText.getText();
			atlasFormat = atlasFormatModel.getSelectedFormat();

			descriptionPrefix = descriptionPrefixText.getText();
			descriptionPostfix = descriptionProstfixText.getText();
			descriptionFormat = descFormatText.getText();
			descriptionJoin = descJoinBox.isSelected();

			factory.setPreference("atlas-prefix", atlasPrefix);
			factory.setPreference("atlas-postfix", atlasPostfix);
			factory.setPreference("atlas-format", atlasFormat);

			factory.setPreference("description-prefix", descriptionPrefix);
			factory.setPreference("description-postfix", descriptionPostfix);
			factory.setPreference("description-format", descriptionFormat);
			factory.setPreference("description-join",
					String.valueOf(descriptionJoin));

			PackerOutput output = factory.createTextureAtlas();

			if (!isOutputValud(output)) {
				return;
			}

			File destination = Dialogs.selectDirectory(parent);

			if (destination == null || !destination.exists()) {
				return;
			}

			AtlasImageWriter imgWriter = new AtlasImageWriter(output,
					destination, atlasFormat);

			imgWriter.setPrefix(atlasPrefix);
			imgWriter.setPostfix(atlasPostfix);
			factory.traverse(imgWriter);

			if (descriptionJoin) {
				JoinTextWriter txtWriter = new JoinTextWriter(output,
						destination);

				txtWriter.setPrefix(descriptionPrefix);
				txtWriter.setPostfix(descriptionPostfix);
				txtWriter.setFormat(descriptionFormat);
				factory.traverse(txtWriter);
			} else {
				for (Integer atlasId : output.atlasSizes.keySet()) {
					AtlasTextWriter txtWriter = new AtlasTextWriter(output,
							destination, atlasId);

					txtWriter.setPrefix(descriptionPrefix);
					txtWriter.setPostfix(descriptionPostfix);
					txtWriter.setFormat(descriptionFormat);
					factory.traverse(txtWriter);
				}
			}
		}
	}

	private boolean isOutputValud(PackerOutput output) {
		if (output.atlasSizes.isEmpty()) {
			Dialogs.warning(parent, R.CANNT_CREATE_ATLAS);

			return false;
		}

		if (output.packedImages.isEmpty()) {
			Dialogs.warning(parent, R.CANNT_CREATE_ATLAS);

			return false;
		}

		if (output.wrongImages.size() == 0) {
			return true;
		}

		return !Dialogs.confirm(parent, R.SOME_TEXTURES_WRONG);
	}
}
