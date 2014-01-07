package ru.snake.spritepacker.render;

import java.awt.image.RenderedImage;

import javax.swing.Icon;
import javax.swing.ListCellRenderer;

import ru.snake.spritepacker.core.Texture;

@SuppressWarnings("serial")
public class TextureCellRenderer extends AbstractIconCellRender implements
		ListCellRenderer {

	private static final String TEXTURE_NAME_FORMAT = "<HTML>width=%d<BR>height=%d"; //$NON-NLS-1$

	@Override
	protected boolean isValueValid(Object value) {
		return value instanceof Texture;
	}

	@Override
	protected void updateIcon(Object value) {
		Texture texture = (Texture) value;
		Icon textureIcon = getTextureIcon(texture);

		icon.setIcon(textureIcon);
	}

	@Override
	protected void updateText(Object value) {
		Texture texture = (Texture) value;
		RenderedImage image = texture.image;

		label.setText(String.format(TEXTURE_NAME_FORMAT, image.getWidth(),
				image.getHeight()));
	}
}
