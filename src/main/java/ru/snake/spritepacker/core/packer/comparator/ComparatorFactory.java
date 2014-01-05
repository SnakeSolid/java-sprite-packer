package ru.snake.spritepacker.core.packer.comparator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import ru.snake.spritepacker.core.packer.ImageData;

public class ComparatorFactory {

	public static Collection<CompareType> getSupportedTypes() {
		return Arrays.asList(CompareType.HEIGHT, CompareType.WIDTH,
				CompareType.PERIMETER, CompareType.AREA,
				CompareType.GREATER_DIMENSION);
	};

	public static Comparator<ImageData> createComparator(CompareType type) {
		switch (type) {
		case HEIGHT:
			return new HeightComparator();

		case WIDTH:
			return new WidthComparator();

		case PERIMETER:
			return new PerimeterComparator();

		case AREA:
			return new AreaComparator();

		case GREATER_DIMENSION:
			return new GreaterDimComparator();

		default:
			throw new IllegalArgumentException();
		}
	}

}
