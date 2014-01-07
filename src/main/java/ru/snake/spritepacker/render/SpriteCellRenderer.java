package ru.snake.spritepacker.render;

import javax.swing.Icon;
import javax.swing.ListCellRenderer;

import ru.snake.spritepacker.Messages;
import ru.snake.spritepacker.core.Sprite;
import ru.snake.spritepacker.core.Texture;

@SuppressWarnings("serial")
public class SpriteCellRenderer extends AbstractIconCellRender implements
		ListCellRenderer {

	@Override
	protected boolean isValueValid(Object value) {
		return value instanceof Sprite;
	}

	@Override
	protected void updateIcon(Object value) {
		Sprite sprite = (Sprite) value;
		Texture texture = sprite.texture;
		Icon textureIcon = getTextureIcon(texture);

		icon.setIcon(textureIcon);
	}

	@Override
	protected void updateText(Object value) {
		Sprite sprite = (Sprite) value;

		label.setText(String.format(
				Messages.getString("SpriteCellRenderer.NAME_FORMAT"), sprite.name, //$NON-NLS-1$
				sprite.offsetX, sprite.offsetY));
	}

}
