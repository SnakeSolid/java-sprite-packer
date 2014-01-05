package ru.snake.spritepacker.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import ru.snake.spritepacker.core.Texture;

@SuppressWarnings("serial")
public abstract class AbstractIconCellRender extends JPanel implements
		ListCellRenderer {

	private static final int THUMBNAIL_SIZE = 48;
	private static final int THUMBNAIL_GRID_SIZE = 4;

	private final Map<Texture, Icon> cache;

	protected final JLabel icon;
	protected final JLabel label;

	public AbstractIconCellRender() {
		BorderLayout layout = new BorderLayout(4, 4);
		setLayout(layout);

		icon = new JLabel();
		label = new JLabel();

		add(icon, BorderLayout.LINE_START);
		add(label, BorderLayout.CENTER);

		cache = new HashMap<Texture, Icon>();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setFont(list.getFont());

		if (isSelected) {
			setOpaque(true);
			setBackground(UIManager.getColor("Table.selectionBackground"));
		} else {
			setOpaque(false);
		}

		if (cellHasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		}

		if (isValueValid(value)) {
			updateText(value);
			updateIcon(value);

			return this;
		}

		icon.setIcon(null);
		label.setText(value.toString());

		return this;
	}

	abstract protected boolean isValueValid(Object value);

	abstract protected void updateIcon(Object value);

	abstract protected void updateText(Object value);

	protected Icon getTextureIcon(Texture texture) {
		if (!cache.containsKey(texture)) {
			BufferedImage image = new BufferedImage(THUMBNAIL_SIZE,
					THUMBNAIL_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics g;

			int imagewidth = texture.image.getWidth();
			int imageheight = texture.image.getHeight();
			float scalex = (float) (THUMBNAIL_SIZE - 5) / imagewidth;
			float scaley = (float) (THUMBNAIL_SIZE - 5) / imageheight;

			g = image.getGraphics();
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, THUMBNAIL_SIZE - 1, THUMBNAIL_SIZE - 1);
			g.setColor(Color.GRAY);
			g.fillRect(1, 1, THUMBNAIL_SIZE - 2, THUMBNAIL_SIZE - 2);
			g.setColor(Color.DARK_GRAY);

			for (int j = 0, y = 1; y < THUMBNAIL_SIZE - 2; j++, y += THUMBNAIL_GRID_SIZE) {
				for (int i = 0, x = 1; x < THUMBNAIL_SIZE - 2; i++, x += THUMBNAIL_GRID_SIZE) {
					if (((i + j) & 1) == 0) {
						g.fillRect(x, y, THUMBNAIL_GRID_SIZE,
								THUMBNAIL_GRID_SIZE);
					}
				}
			}

			if (scalex < scaley) {
				int height = Math.round(scalex * imageheight);

				g.drawImage(texture.image, 2, (THUMBNAIL_SIZE - height) / 2,
						THUMBNAIL_SIZE - 5, height, null);
			} else {
				int width = Math.round(scaley * imagewidth);

				g.drawImage(texture.image, (THUMBNAIL_SIZE - width) / 2, 2,
						width, THUMBNAIL_SIZE - 5, null);
			}

			g.dispose();

			Icon icon = new ImageIcon(image);

			cache.put(texture, icon);
		}

		return cache.get(texture);
	}

}