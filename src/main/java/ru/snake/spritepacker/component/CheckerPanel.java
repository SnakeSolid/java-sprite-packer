package ru.snake.spritepacker.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ru.snake.spritepacker.Configuration;

@SuppressWarnings("serial")
public class CheckerPanel extends JPanel {

	private final Paint paint;
	private final Color fill;

	public CheckerPanel() {
		paint = createPaint();
		fill = getDefaultColor();
	}

	private Color getDefaultColor() {
		Configuration config = Configuration.getInstance();

		return config.getPanelForeground();
	}

	private Paint createPaint() {
		Configuration config = Configuration.getInstance();
		Color foreground = config.getPanelForeground();
		Color background = config.getPanelBackground();
		int grid = config.getPanelGridSize();

		Rectangle anchor = new Rectangle(2 * grid, 2 * grid);
		BufferedImage txtr = new BufferedImage(2 * grid, 2 * grid,
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = txtr.getGraphics();
		g.setColor(foreground);
		g.fillRect(0, 0, grid, grid);
		g.fillRect(grid, grid, grid, grid);
		g.setColor(background);
		g.fillRect(grid, 0, grid, grid);
		g.fillRect(0, grid, grid, grid);
		g.dispose();

		Paint paint = new TexturePaint(txtr, anchor);

		return paint;
	}

	@Override
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(paint);
			g2.fillRect(0, 0, width, height);
			g2.setPaint(null);
		} else {
			g.setColor(fill);
			g.fillRect(0, 0, width, height);
		}
	}

}
