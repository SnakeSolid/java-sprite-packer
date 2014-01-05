package ru.snake.spritepacker.render;

import java.awt.image.RenderedImage;

import javax.swing.Icon;
import javax.swing.ListCellRenderer;

import ru.snake.spritepacker.R;
import ru.snake.spritepacker.core.Texture;

@SuppressWarnings("serial")
public class TextureCellRenderer extends AbstractIconCellRender implements
		ListCellRenderer {

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

		label.setText(String.format(R.TEXTURE_RENDER_TEXT, image.getWidth(),
				image.getHeight()));
	}
}
