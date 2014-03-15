package ru.snake.spritepacker.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
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

import ru.snake.spritepacker.Configuration;
import ru.snake.spritepacker.core.Texture;

@SuppressWarnings("serial")
public abstract class AbstractIconCellRender extends JPanel implements
		ListCellRenderer {

	private final Map<Texture, Icon> cache;

	private final int thumnailSize;
	private final int gridSize;
	private final Paint paint;
	private final Color fill;

	protected final JLabel icon;
	protected final JLabel label;

	public AbstractIconCellRender() {
		cache = new HashMap<Texture, Icon>();

		thumnailSize = getThumnailSize();
		gridSize = getGridSize();
		paint = createPaint();
		fill = getDefaultColor();

		BorderLayout layout = new BorderLayout(4, 4);
		setLayout(layout);

		icon = new JLabel();
		label = new JLabel();

		add(icon, BorderLayout.LINE_START);
		add(label, BorderLayout.CENTER);
	}

	private int getGridSize() {
		Configuration config = Configuration.getInstance();

		return config.getListGridSize();
	}

	private int getThumnailSize() {
		Configuration config = Configuration.getInstance();

		return config.getListThumbSize();
	}

	private Color getDefaultColor() {
		Configuration config = Configuration.getInstance();

		return config.getListForeground();
	}

	private Paint createPaint() {
		Configuration config = Configuration.getInstance();
		Color foreground = config.getListForeground();
		Color background = config.getListBackground();

		Rectangle anchor = new Rectangle(2 * gridSize, 2 * gridSize);
		BufferedImage txtr = new BufferedImage(2 * gridSize, 2 * gridSize,
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = txtr.getGraphics();
		g.setColor(foreground);
		g.fillRect(0, 0, gridSize, gridSize);
		g.fillRect(gridSize, gridSize, gridSize, gridSize);
		g.setColor(background);
		g.fillRect(gridSize, 0, gridSize, gridSize);
		g.fillRect(0, gridSize, gridSize, gridSize);
		g.dispose();

		Paint paint = new TexturePaint(txtr, anchor);

		return paint;
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

	protected final Icon getTextureIcon(Texture texture) {
		if (cache.containsKey(texture)) {
			return cache.get(texture);
		}

		BufferedImage image = new BufferedImage(thumnailSize, thumnailSize,
				BufferedImage.TYPE_INT_ARGB);

		int imagewidth = texture.image.getWidth();
		int imageheight = texture.image.getHeight();
		float scalex = (float) (thumnailSize - 5) / imagewidth;
		float scaley = (float) (thumnailSize - 5) / imageheight;

		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, thumnailSize - 1, thumnailSize - 1);

		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setPaint(paint);
			g.fillRect(1, 1, thumnailSize - 2, thumnailSize - 2);
			g2.setPaint(null);
		} else {
			g.setColor(fill);
			g.fillRect(1, 1, thumnailSize - 2, thumnailSize - 2);
		}

		if (scalex < scaley) {
			int height = Math.round(scalex * imageheight);

			g.drawImage(texture.image, 2, (thumnailSize - height) / 2,
					thumnailSize - 5, height, null);
		} else {
			int width = Math.round(scaley * imagewidth);

			g.drawImage(texture.image, (thumnailSize - width) / 2, 2, width,
					thumnailSize - 5, null);
		}

		g.dispose();

		Icon icon = new ImageIcon(image);

		cache.put(texture, icon);

		return icon;
	}

}