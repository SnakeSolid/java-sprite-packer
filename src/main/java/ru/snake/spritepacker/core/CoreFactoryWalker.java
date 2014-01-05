package ru.snake.spritepacker.core;

public interface CoreFactoryWalker {

	public void start();

	public void end();

	public void startAnimation(String name);

	public void endAnimation();

	public void startSprite(String name, int offsetX, int offsetY,
			Texture texture);

	public void endSprite();

}
