package ru.snake.spritepacker.actions.load;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.core.TextureLoader;
import ru.snake.spritepacker.util.Dialogs;
import ru.snake.spritepacker.util.Util;

@SuppressWarnings("serial")
public class ImportTiledTextureAction extends BasicAction implements Action {

	private static final String PROP_OFFSET_X = "import-tiled-offset-x"; //$NON-NLS-1$
	private static final String PROP_OFFSET_Y = "import-tiled-offset-y"; //$NON-NLS-1$
	private static final String PROP_WIDTH = "import-tiled-width"; //$NON-NLS-1$
	private static final String PROP_HEIGHT = "import-tiled-height"; //$NON-NLS-1$
	private static final String PROP_GAP_X = "import-tiled-gap-x"; //$NON-NLS-1$
	private static final String PROP_GAP_Y = "import-tiled-gap-y"; //$NON-NLS-1$
	private static final String PROP_CROP = "import-tiled-crop"; //$NON-NLS-1$
	private static final String PROP_TRANSPARENT = "import-tiled-transparent"; //$NON-NLS-1$
	private static final String PROP_COLORED = "import-tiled-colored"; //$NON-NLS-1$

	private static final String DEFAULT_OFFSET_X = "0"; //$NON-NLS-1$
	private static final String DEFAULT_OFFSET_Y = "0"; //$NON-NLS-1$
	private static final String DEFAULT_WIDTH = "128"; //$NON-NLS-1$
	private static final String DEFAULT_HEIGHT = "128"; //$NON-NLS-1$
	private static final String DEFAULT_GAP_X = "1"; //$NON-NLS-1$
	private static final String DEFAULT_GAP_Y = "1"; //$NON-NLS-1$
	private static final String DEFAULT_CROP = "true"; //$NON-NLS-1$
	private static final String DEFAULT_TRANSPARENT = "false"; //$NON-NLS-1$
	private static final String DEFAULT_COLORED = "false"; //$NON-NLS-1$

	private final Component parent;
	private final CoreFactory factory;

	public ImportTiledTextureAction(Component parent, CoreFactory factory) {
		this.parent = parent;
		this.factory = factory;

		putValue(NAME, Messages.getString("ImportTiledTextureAction.NAME")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyEvent.VK_L);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JPanel pane = new JPanel();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);

		// -----------------------------------------------

		int propOffsetX = Integer.valueOf(factory.getPreference(PROP_OFFSET_X,
				DEFAULT_OFFSET_X));
		int propOffsetY = Integer.valueOf(factory.getPreference(PROP_OFFSET_Y,
				DEFAULT_OFFSET_Y));
		int propWidth = Integer.valueOf(factory.getPreference(PROP_WIDTH,
				DEFAULT_WIDTH));
		int propHeight = Integer.valueOf(factory.getPreference(PROP_HEIGHT,
				DEFAULT_HEIGHT));
		int propGapX = Integer.valueOf(factory.getPreference(PROP_GAP_X,
				DEFAULT_GAP_X));
		int propGapY = Integer.valueOf(factory.getPreference(PROP_GAP_Y,
				DEFAULT_GAP_Y));

		boolean propCrop = Boolean.valueOf(factory.getPreference(PROP_CROP,
				DEFAULT_CROP));
		boolean propTransparent = Boolean.valueOf(factory.getPreference(
				PROP_TRANSPARENT, DEFAULT_TRANSPARENT));
		boolean propColored = Boolean.valueOf(factory.getPreference(
				PROP_COLORED, DEFAULT_COLORED));

		// -----------------------------------------------

		JLabel labelOffset = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_OFFSET")); //$NON-NLS-1$
		JLabel labelOffsetX = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_OFFSET_X")); //$NON-NLS-1$
		JLabel labelOffsetY = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_OFFSET_Y")); //$NON-NLS-1$
		JLabel labelSize = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_SIZE")); //$NON-NLS-1$
		JLabel labelWidth = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_WIDTH")); //$NON-NLS-1$
		JLabel labelHeight = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_HEIGHT")); //$NON-NLS-1$
		JLabel labelGap = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_GAP")); //$NON-NLS-1$
		JLabel labelGapX = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_GAP_X")); //$NON-NLS-1$
		JLabel labelGapY = new JLabel(
				Messages.getString("ImportTiledTextureAction.LABEL_GAP_Y")); //$NON-NLS-1$

		SpinnerModel modelOffsetX = new SpinnerNumberModel(propOffsetX, 0,
				65536, 1);
		SpinnerModel modelOffsetY = new SpinnerNumberModel(propOffsetY, 0,
				65536, 1);
		SpinnerModel modelWidth = new SpinnerNumberModel(propWidth, 1, 65536, 1);
		SpinnerModel modelHeight = new SpinnerNumberModel(propHeight, 1, 65536,
				1);
		SpinnerModel modelGapX = new SpinnerNumberModel(propGapX, 0, 65536, 1);
		SpinnerModel modelGapY = new SpinnerNumberModel(propGapY, 0, 65536, 1);

		JSpinner spinOffsetX = new JSpinner(modelOffsetX);
		JSpinner spinOffsetY = new JSpinner(modelOffsetY);
		JSpinner spinWidth = new JSpinner(modelWidth);
		JSpinner spinHeight = new JSpinner(modelHeight);
		JSpinner spinGapX = new JSpinner(modelGapX);
		JSpinner spinGapY = new JSpinner(modelGapY);

		JCheckBox checkCrop = new JCheckBox(
				Messages.getString("ImportTiledTextureAction.LABEL_CROP"), propCrop); //$NON-NLS-1$
		JCheckBox checkTransparent = new JCheckBox(
				Messages.getString("ImportTiledTextureAction.LABEL_TRANSPARENT"), propTransparent); //$NON-NLS-1$
		JCheckBox checkColored = new JCheckBox(
				Messages.getString("ImportTiledTextureAction.LABEL_COLORED"), propColored); //$NON-NLS-1$

		// -----------------------------------------------

		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// @formatter:off
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(labelOffset)
						.addComponent(labelSize)
						.addComponent(labelGap)
						)
						
				.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(layout.createSequentialGroup()
								.addComponent(spinOffsetX)
								.addComponent(labelOffsetX)
								.addComponent(spinOffsetY)
								.addComponent(labelOffsetY)
								
								.addContainerGap(0, Short.MAX_VALUE)
								)
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(spinWidth)
								.addComponent(labelWidth)
								.addComponent(spinHeight)
								.addComponent(labelHeight)
								
								.addContainerGap(0, Short.MAX_VALUE)
								)
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(spinGapX)
								.addComponent(labelGapX)
								.addComponent(spinGapY)
								.addComponent(labelGapY)
								
								.addContainerGap(0, Short.MAX_VALUE)
								)
						
						.addComponent(checkCrop)
						.addComponent(checkTransparent)
						.addComponent(checkColored)
						)
				);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelOffset)
						.addComponent(spinOffsetX)
						.addComponent(labelOffsetX)
						.addComponent(spinOffsetY)
						.addComponent(labelOffsetY)
						)

				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelSize)
						.addComponent(spinWidth)
						.addComponent(labelWidth)
						.addComponent(spinHeight)
						.addComponent(labelHeight)
						)

				.addGroup(layout.createBaselineGroup(false, false)
						.addComponent(labelGap)
						.addComponent(spinGapX)
						.addComponent(labelGapX)
						.addComponent(spinGapY)
						.addComponent(labelGapY)
						)
						
				.addComponent(checkCrop)
				.addComponent(checkTransparent)
				.addComponent(checkColored)
				);
		// @formatter:on

		layout.linkSize(SwingConstants.HORIZONTAL, spinOffsetX, spinOffsetY);
		layout.linkSize(SwingConstants.HORIZONTAL, spinWidth, spinHeight);
		layout.linkSize(SwingConstants.HORIZONTAL, spinGapX, spinGapY);

		// -----------------------------------------------

		int result = JOptionPane
				.showConfirmDialog(
						parent,
						pane,
						Messages.getString("ImportTiledTextureAction.TITLE"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
						JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.NO_OPTION) {
			return;
		}

		File file = Dialogs.getImage(parent);

		if (file == null) {
			return;
		}

		BufferedImage image;

		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			Dialogs.error(pane, e.getMessage());

			return;
		}

		propOffsetX = (Integer) modelOffsetX.getValue();
		propOffsetY = (Integer) modelOffsetY.getValue();
		propWidth = (Integer) modelWidth.getValue();
		propHeight = (Integer) modelHeight.getValue();
		propGapX = (Integer) modelGapX.getValue();
		propGapY = (Integer) modelGapY.getValue();

		propCrop = checkCrop.isSelected();
		propTransparent = checkTransparent.isSelected();
		propColored = checkColored.isSelected();

		factory.setPreference(PROP_OFFSET_X, String.valueOf(propOffsetX));
		factory.setPreference(PROP_OFFSET_Y, String.valueOf(propOffsetY));
		factory.setPreference(PROP_WIDTH, String.valueOf(propWidth));
		factory.setPreference(PROP_HEIGHT, String.valueOf(propHeight));
		factory.setPreference(PROP_GAP_X, String.valueOf(propGapX));
		factory.setPreference(PROP_GAP_Y, String.valueOf(propGapY));
		factory.setPreference(PROP_CROP, String.valueOf(propCrop));
		factory.setPreference(PROP_TRANSPARENT, String.valueOf(propTransparent));
		factory.setPreference(PROP_COLORED, String.valueOf(propColored));

		TextureLoader textureLoader = factory.getTextureLoader();
		List<Sprite> sprites = new ArrayList<Sprite>();
		Point current = new Point(0, propOffsetY);

		while (current.y + propHeight < image.getHeight()) {
			current.x = propOffsetX;

			while (current.x + propWidth < image.getWidth()) {
				BufferedImage subimage = newImage(image, current.x, current.y,
						propWidth, propHeight);
				StringBuilder builder = new StringBuilder();

				builder.append("tile-"); //$NON-NLS-1$
				builder.append(propWidth);
				builder.append('x');
				builder.append(propHeight);
				builder.append("+"); //$NON-NLS-1$
				builder.append(current.x);
				builder.append('+');
				builder.append(current.y);

				if (needLoad(propTransparent, propColored, subimage)) {
					Sprite sprite;

					sprite = createSprite(propWidth, propHeight, propCrop,
							textureLoader, subimage, builder.toString());

					if (sprite != null) {
						sprites.add(sprite);
					}
				}

				current.x += propWidth + propGapX;
			}

			current.y += propHeight + propGapY;
		}

		if (sprites.isEmpty()) {
			return;
		}

		String name = Util.getFullName(file);

		if (name.isEmpty()) {
			factory.createAnimation("animation", sprites); //$NON-NLS-1$
		} else {
			factory.createAnimation(name, sprites);
		}
	}

	private BufferedImage newImage(BufferedImage image, int x, int y,
			int width, int height) {
		BufferedImage subimage = image.getSubimage(x, y, width, height);

		return subimage;
	}

	private boolean needLoad(boolean transparent, boolean colored,
			BufferedImage image) {
		if (transparent && colored) {
			return true;
		}

		if (transparent) {
			for (int j = 0; j < image.getHeight(); j++) {
				for (int i = 0; i < image.getWidth(); i++) {
					if ((image.getRGB(i, j) & 0xff000000) == 0) {
						return true;
					}
				}
			}

			return false;
		}

		if (colored) {
			int value = image.getRGB(0, 0);

			for (int j = 0; j < image.getHeight(); j++) {
				for (int i = 0; i < image.getWidth(); i++) {
					if (image.getRGB(i, j) != value) {
						return true;
					}
				}
			}

			return false;
		}

		return true;
	}

	private Sprite createSprite(int width, int height, boolean crop,
			TextureLoader textureLoader, BufferedImage image, String namer) {
		Texture texture;
		Point offset;

		if (crop) {
			offset = new Point();

			texture = textureLoader.loadCroped(image, offset);

			offset.x = width / 2 - offset.x;
			offset.y = height / 2 - offset.y;
		} else {
			texture = textureLoader.load(image);

			offset = new Point(width / 2, height / 2);
		}

		if (texture == null) {
			return null;
		}

		return new Sprite(offset.x, offset.y, namer, texture);
	}

}
