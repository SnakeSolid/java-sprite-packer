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
	private static final String DESCRIPTION_POSTFIX = "description-postfix"; //$NON-NLS-1$
	private static final String DESCRIPTION_PREFIX = "description-prefix"; //$NON-NLS-1$
	private static final String DESCRIPTION_HEADER = "description-header";
	private static final String DESCRIPTION_FORMAT = "description-format"; //$NON-NLS-1$
	private static final String DESCRIPTION_FOOTER = "description-footer";
	private static final String DESCRIPTION_JOIN = "description-join"; //$NON-NLS-1$

	private static final String DEFAULT_ATLAS_PREFIX = ""; //$NON-NLS-1$
	private static final String DEFAULT_ATLAS_EXTENSION = ".png"; //$NON-NLS-1$
	private static final String DEFAULT_ATLAS_FORMAT = "png"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_PREFIX = "description"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_POSTFIX = ".txt"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_HEADER = "";
	private static final String DEFAULT_DESCRIPTION_FORMAT = "atlas - {atlas.id};{atlas.name};{atlas.width};{atlas.height};\nanimation - {animation.id};{animation.name};\nsprite - {sprite.id};{sprite.name};{sprite.offsetx};{sprite.offsety};\ntexture - {texture.top};{texture.left};{texture.width};{texture.height}\n"; //$NON-NLS-1$
	private static final String DEFAULT_DESCRIPTION_FOOTER = "";
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
				DEFAULT_ATLAS_PREFIX);
		String atlasPostfix = factory.getPreference(ATLAS_POSTFIX,
				DEFAULT_ATLAS_EXTENSION);
		String atlasFormat = factory.getPreference(ATLAS_FORMAT,
				DEFAULT_ATLAS_FORMAT);

		String descriptionPrefix = factory.getPreference(DESCRIPTION_PREFIX,
				DEFAULT_DESCRIPTION_PREFIX);
		String descriptionPostfix = factory.getPreference(DESCRIPTION_POSTFIX,
				DEFAULT_DESCRIPTION_POSTFIX);
		String descriptionHeader = factory.getPreference(DESCRIPTION_HEADER,
				DEFAULT_DESCRIPTION_HEADER);
		String descriptionFormat = factory.getPreference(DESCRIPTION_FORMAT,
				DEFAULT_DESCRIPTION_FORMAT);
		String descriptionFooter = factory.getPreference(DESCRIPTION_FOOTER,
				DEFAULT_DESCRIPTION_FOOTER);

		boolean descriptionJoin = Boolean.parseBoolean(factory.getPreference(
				DESCRIPTION_JOIN, String.valueOf(DEFAULT_JOIN_VALUE)));

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

		JLabel descHeaderLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_DESCRIPTION_HEADER")); //$NON-NLS-1$
		JTextArea descHeaderText = new JTextArea(descriptionHeader, 2, 20);
		JScrollPane descHeaderScroll = new JScrollPane(descHeaderText);

		descHeaderText.setLineWrap(true);
		descHeaderText.setWrapStyleWord(true);

		descHeaderScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

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

		JLabel descFooterLabel = new JLabel(
				Messages.getString("ExportAtlasAction.LABEL_DESCRIPTION_FOOTER")); //$NON-NLS-1$
		JTextArea descFooterText = new JTextArea(descriptionFooter, 2, 20);
		JScrollPane descFooterScroll = new JScrollPane(descFooterText);

		descFooterText.setLineWrap(true);
		descFooterText.setWrapStyleWord(true);

		descFooterScroll
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
								.addComponent(descHeaderLabel)
								.addComponent(descHeaderScroll))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(descFormatLabel)
								.addComponent(descFormatScroll))
				.addGroup(
						layout.createParallelGroup()
								.addComponent(descFooterLabel)
								.addComponent(descFooterScroll)));

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
				.addComponent(descHeaderLabel).addComponent(descHeaderScroll)
				.addComponent(descFormatLabel).addComponent(descFormatScroll)
				.addComponent(descFooterLabel).addComponent(descFooterScroll)
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
			descriptionHeader = descHeaderText.getText();
			descriptionFormat = descFormatText.getText();
			descriptionFooter = descFooterText.getText();

			descriptionJoin = descJoinBox.isSelected();

			factory.setPreference(ATLAS_PREFIX, atlasPrefix);
			factory.setPreference(ATLAS_POSTFIX, atlasPostfix);
			factory.setPreference(ATLAS_FORMAT, atlasFormat);

			factory.setPreference(DESCRIPTION_PREFIX, descriptionPrefix);
			factory.setPreference(DESCRIPTION_POSTFIX, descriptionPostfix);
			factory.setPreference(DESCRIPTION_HEADER, descriptionHeader);
			factory.setPreference(DESCRIPTION_FORMAT, descriptionFormat);
			factory.setPreference(DESCRIPTION_FOOTER, descriptionFooter);

			factory.setPreference(DESCRIPTION_JOIN,
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

			boolean atlasPerAnimation = factory.isAtlasPerAnim();

			if (descriptionJoin) {
				JoinTextWriter txtWriter = new JoinTextWriter(output,
						destination);

				txtWriter.setPrefix(descriptionPrefix);
				txtWriter.setPostfix(descriptionPostfix);

				writeOutput(descriptionHeader, descriptionFormat,
						descriptionFooter, txtWriter);
			} else {
				for (Integer atlasId : output.atlasSizes.keySet()) {
					AtlasTextWriter txtWriter = new AtlasTextWriter(output,
							destination, atlasId);

					txtWriter.setPrefix(descriptionPrefix);
					txtWriter.setPostfix(descriptionPostfix);
					txtWriter.setIgnoreAnimation(!atlasPerAnimation);

					if (!writeOutput(descriptionHeader, descriptionFormat,
							descriptionFooter, txtWriter)) {
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

	private boolean writeOutput(String descriptionHeader,
			String descriptionFormat, String descriptionFooter,
			TextWriter txtWriter) {
		try {
			txtWriter.prepareHeader(descriptionHeader);
			txtWriter.prepareLine(descriptionFormat);
			txtWriter.prepareFooter(descriptionFooter);
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
