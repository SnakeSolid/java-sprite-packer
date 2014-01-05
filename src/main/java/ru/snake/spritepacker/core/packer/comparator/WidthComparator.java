package ru.snake.spritepacker.core.packer.comparator;

import java.util.Comparator;

import ru.snake.spritepacker.core.packer.ImageData;

public class WidthComparator implements Comparator<ImageData> {

	@Override
	public int compare(ImageData obj1, ImageData obj2) {
		if (obj1.width == obj2.width) {
			return obj2.height - obj1.height;
		}

		return obj2.width - obj1.width;
	}

}
