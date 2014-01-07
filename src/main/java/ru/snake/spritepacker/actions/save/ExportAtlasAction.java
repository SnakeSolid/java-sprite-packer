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

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.packer.PackerOutput;
import ru.snake.spritepacker.model.ImageWriterFormatModel;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.writer.AtlasImageWriter;
import ru.snake.spritepacker.writer.AtlasTextWriter;
import ru.snake.spritepacker.writer.JoinTextWriter;
import ru.snake.spritepacker.writer.TextWriter;
import ru.snake.spritepacker.writer.expression.EvaluationException;
import ru.snake.spritepacker.writer.lexer.LexerException;
import ru.snake.spritepacker.writer.parser.ParserException;

@SuppressWarnings("serial")
public class ExportAtlasAction extends BasicAction implements Action {

	private static final String ICON_NAME = "atlas"; //$NON-NLS-1$

	private static final String ATLAS_FORMAT = "atlas-format"; //$NON-NLS-1$
	private static final String ATLAS_POSTFIX = "atlas-postfix"; //$NON-NLS-1$
	private static final String ATLAS_PREFIX = "atlas-prefix"; //$NON-NLS-1$
	private static final String DESCRIPTION_FORMAT = "description-format"; //$NON-NLS-1$
	private static final String DESCRIPTION_JOIN = "description-join"; //$NON-NLS-1$
	private static final String DESCRIPTION_POSTFIX = "description-postfix"; //$NON-NLS-1$
	private static final String DESCRIPTION_PREFIX = "description-prefix"; //$NON-NLS-1$

	private static final String DEFAULT_ATLAS_PREFIX = ""; //$NON-NLS-1$
	private static final String DEFAULT_ATLAS_EXTENSION = ".png"; //$NON-NLS-1$
	private static final String DEFAULT_ATLAS_FORMAT = "png"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_PREFIX = "description"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_POSTFIX = ".txt"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_FORMAT = "atlas - {atlas.id};{atlas.name};{atlas.width};{atlas.height};\nanimation - {animation.id};{animation.name};\nsprite - {sprite.id};{sprite.name};{sprite.offsetx};{sprite.offsety};\ntexture - {texture.top};{texture.left};{texture.width};{texture.height}\n"; //$NON-NLS-1$
	private static final boolean DEFAULT_JOIN_VALUE = false;

	private final CoreFactory factory;
	private final Component parent;

	public ExportAtlasAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("ExportAtlasAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);

		setIcon(ICON_NAME, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String atlasPrefix = factory.getPreference(ATLAS_PREFIX,
				DEFAULT_ATLAS_PREFIX); //$NON-NLS-2$
		String atlasPostfix = factory.getPreference(ATLAS_POSTFIX,
				DEFAULT_ATLAS_EXTENSION); //$NON-NLS-2$
		String atlasFormat = factory.getPreference(ATLAS_FORMAT,
				DEFAULT_ATLAS_FORMAT); //$NON-NLS-2$

		String descriptionPrefix = factory.getPreference(DESCRIPTION_PREFIX, //$NON-NLS-1$
				DEFAULT_DESCRIPTION_PREFIX);
		String descriptionPostfix = factory.getPreference(DESCRIPTION_POSTFIX,
				DEFAULT_DESCRIPTION_POSTFIX); //$NON-NLS-2$
		String descriptionFormat = factory.getPreference(DESCRIPTION_FORMAT, //$NON-NLS-1$
				DEFAULT_DESCRIPTION_FORMAT);

		boolean descriptionJoin = Boolean.parseBoolean(factory.getPreference(
				DESCRIPTION_JOIN, String.valueOf(DEFAULT_JOIN_VALUE))); //$NON-NLS-2$

		// ===================================================

		JLabel atlasLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_ATLAS_FILENAME")); //$NON-NLS-1$
		JTextField atlasPrefixText = new JTextField(atlasPrefix, 10);
		JLabel atlasFileLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_ATLAS_NAME")); //$NON-NLS-1$
		JTextField atlasProstfixText = new JTextField(atlasPostfix, 10);

		// ---------------------------------------------------

		ImageWriterFormatModel atlasFormatModel = new ImageWriterFormatModel(
				atlasFormat);
		JLabel atlasFormatLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_ATLAS_FORMAT")); //$NON-NLS-1$
		JComboBox atlasFormatBox = new JComboBox(atlasFormatModel);

		// ---------------------------------------------------

		JLabel descriptionLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_DESCRIPTION_FILENAME")); //$NON-NLS-1$
		JTextField descriptionPrefixText = new JTextField(descriptionPrefix, 10);
		JLabel descriptionFileLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_NAME_LABEL")); //$NON-NLS-1$
		JTextField descriptionProstfixText = new JTextField(descriptionPostfix,
				10);
		// ---------------------------------------------------

		JLabel descFormatLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_DESCRIPTION_FORMAT")); //$NON-NLS-1$
		JTextArea descFormatText = new JTextArea(descriptionFormat, 5, 20);
		JScrollPane descFormatScroll = new JScrollPane(descFormatText);

		descFormatText.setLineWrap(true);
		descFormatText.setWrapStyleWord(true);

		descFormatScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// ---------------------------------------------------

		JCheckBox descJoinBox = new JCheckBox(
				Messages.getString("ExportAtlasAction.CHECKBOX_JOIN_DESCRIPTION"), descriptionJoin); //$NON-NLS-1$

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
				Messages.getString("ExportAtlasAction.TITLE"), //$NON-NLS-1$
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			atlasPrefix = atlasPrefixText.getText();
			atlasPostfix = atlasProstfixText.getText();
			atlasFormat = atlasFormatModel.getSelectedFormat();

			descriptionPrefix = descriptionPrefixText.getText();
			descriptionPostfix = descriptionProstfixText.getText();
			descriptionFormat = descFormatText.getText();
			descriptionJoin = descJoinBox.isSelected();

			factory.setPreference(ATLAS_PREFIX, atlasPrefix); //$NON-NLS-1$
			factory.setPreference(ATLAS_POSTFIX, atlasPostfix); //$NON-NLS-1$
			factory.setPreference(ATLAS_FORMAT, atlasFormat); //$NON-NLS-1$

			factory.setPreference(DESCRIPTION_PREFIX, descriptionPrefix); //$NON-NLS-1$
			factory.setPreference(DESCRIPTION_POSTFIX, descriptionPostfix); //$NON-NLS-1$
			factory.setPreference(DESCRIPTION_FORMAT, descriptionFormat); //$NON-NLS-1$
			factory.setPreference(DESCRIPTION_JOIN, //$NON-NLS-1$
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

				writeOutput(descriptionFormat, txtWriter);
			} else {
				for (Integer atlasId : output.atlasSizes.keySet()) {
					AtlasTextWriter txtWriter = new AtlasTextWriter(output,
							destination, atlasId);

					txtWriter.setPrefix(descriptionPrefix);
					txtWriter.setPostfix(descriptionPostfix);

					if (!writeOutput(descriptionFormat, txtWriter)) {
						break;
					}
				}
			}
		}
	}

	private boolean isOutputValud(PackerOutput output) {
		if (output.atlasSizes.isEmpty()) {
			Dialogs.warning(parent,
					Messages.getString("ExportAtlasAction.NO_ATLASES")); //$NON-NLS-1$

			return false;
		}

		if (output.packedImages.isEmpty()) {
			Dialogs.warning(parent,
					Messages.getString("ExportAtlasAction.NO_TEXTURES")); //$NON-NLS-1$

			return false;
		}

		if (output.wrongImages.size() == 0) {
			return true;
		}

		return !Dialogs.confirm(parent,
				Messages.getString("ExportAtlasAction.WRONG_TEXTURES")); //$NON-NLS-1$
	}

	private boolean writeOutput(String descriptionFormat, TextWriter txtWriter) {
		try {
			txtWriter.prepareExpression(descriptionFormat);
		} catch (LexerException ex) {
			Dialogs.error(parent, ex.getMessage());

			return false;
		} catch (ParserException ex) {
			Dialogs.error(parent, ex.getMessage());

			return false;
		}

		try {
			factory.traverse(txtWriter);
		} catch (EvaluationException ex) {
			Dialogs.error(parent, ex.getMessage());

			return false;
		}

		return true;
	}

}
