package ru.snake.spritepacker.core.packer.comparator;

import java.util.Comparator;

import ru.snake.spritepacker.core.packer.ImageData;

public class AreaComparator implements Comparator<ImageData> {

	@Override
	public int compare(ImageData obj1, ImageData obj2) {
		int area1 = obj1.width * obj1.height;
		int area2 = obj2.width * obj2.height;

		return area2 - area1;
	}

}
