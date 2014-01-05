package ru.snake.spritepacker.core.packer.comparator;

import java.util.Comparator;

import ru.snake.spritepacker.core.packer.ImageData;

public class GreaterDimComparator implements Comparator<ImageData> {

	@Override
	public int compare(ImageData obj1, ImageData obj2) {
		int dim1 = Math.max(obj1.width, obj1.height);
		int dim2 = Math.max(obj2.width, obj2.height);

		return dim2 - dim1;
	}

}
