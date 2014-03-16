package ru.snake.spritepacker.plugin;

import ru.snake.spritepacker.core.CoreFactory;

public interface Plugin {

	public String getName();

	public String getMenuItemText();

	public void setFactory(CoreFactory factory);

}
