package ru.snake.spritepacker.core.packer.comparator;

import java.util.Comparator;

import ru.snake.spritepacker.core.packer.ImageData;

public class HeightComparator implements Comparator<ImageData> {

	@Override
	public int compare(ImageData obj1, ImageData obj2) {
		if (obj1.height == obj2.height) {
			return obj2.width - obj1.width;
		}

		return obj2.height - obj1.height;
	}

}
