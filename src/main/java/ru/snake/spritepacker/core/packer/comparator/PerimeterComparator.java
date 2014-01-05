package ru.snake.spritepacker.core.packer.comparator;

import java.util.Comparator;

import ru.snake.spritepacker.core.packer.ImageData;

public class PerimeterComparator implements Comparator<ImageData> {

	@Override
	public int compare(ImageData obj1, ImageData obj2) {
		int halfper1 = obj1.width + obj1.height;
		int halfper2 = obj2.width + obj2.height;

		return halfper2 - halfper1;
	}

}
