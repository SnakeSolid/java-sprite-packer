package ru.snake.spritepacker.actions.plugin;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.Action;

import ru.snake.spritepacker.actions.BasicAction;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.plugin.ImportPlugin;

@SuppressWarnings("serial")
public class ImportPlugunAction extends BasicAction implements Action {

	private final Component parent;
	private final CoreFactory factory;
	private final ImportPlugin plugin;

	public ImportPlugunAction(Component parent, CoreFactory factory,
			ImportPlugin plugin) {
		this.parent = parent;
		this.factory = factory;
		this.plugin = plugin;

		putValue(NAME, plugin.getMenuItemText());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		plugin.setFactory(factory);
		plugin.showImport(parent);
	}

}
