package ru.snake.spritepacker.actions;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public abstract class BasicAction extends AbstractAction {

	private static final String ICON_SMALL_RESOURCE_FMT = "/icons/16x16/%s.png";
	private static final String ICON_LARGE_RESOURCE_FMT = "/icons/48x48/%s.png";

	@SuppressWarnings("rawtypes")
	protected void setIcon(String iconName, boolean onlysmall) {
		Class clazz = getClass();
		String resName;
		URL resource;

		resName = String.format(ICON_SMALL_RESOURCE_FMT, iconName);
		resource = clazz.getResource(resName);

		if (resource != null) {
			Icon icon = new ImageIcon(resource);

			putValue(SMALL_ICON, icon);

			if (onlysmall) {
				putValue(LARGE_ICON_KEY, icon);

				return;
			}
		}

		resName = String.format(ICON_LARGE_RESOURCE_FMT, iconName);
		resource = clazz.getResource(resName);

		if (resource != null) {
			Icon icon = new ImageIcon(resource);

			putValue(LARGE_ICON_KEY, icon);
		}
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
