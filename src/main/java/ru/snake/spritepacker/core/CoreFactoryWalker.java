package ru.snake.spritepacker.core;

public interface CoreFactoryWalker {

	public void start();

	public void end();

	public void startAnimation(int index, String name);

	public void endAnimation();

	public void startSprite(int index, String name, int offsetX, int offsetY,
			Texture texture);

	public void endSprite();

}
