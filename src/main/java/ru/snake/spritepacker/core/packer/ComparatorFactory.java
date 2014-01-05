package ru.snake.spritepacker.core.packer;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

import ru.snake.spritepacker.core.packer.comparator.AreaComparator;
import ru.snake.spritepacker.core.packer.comparator.CompareType;
import ru.snake.spritepacker.core.packer.comparator.GreaterDimComparator;
import ru.snake.spritepacker.core.packer.comparator.HeightComparator;
import ru.snake.spritepacker.core.packer.comparator.PerimeterComparator;
import ru.snake.spritepacker.core.packer.comparator.WidthComparator;

public class ComparatorFactory {

	private static final Map<CompareType, Comparator<ImageData>> cache;

	static {
		cache = new EnumMap<CompareType, Comparator<ImageData>>(
				CompareType.class);
	}

	public static Comparator<ImageData> getComparator(CompareType type) {
		if (!cache.containsKey(type)) {
			switch (type) {
			case WIDTH:
				cache.put(CompareType.WIDTH, new WidthComparator());
				break;

			case HEIGHT:
				cache.put(CompareType.HEIGHT, new HeightComparator());
				break;

			case PERIMETER:
				cache.put(CompareType.PERIMETER, new PerimeterComparator());
				break;

			case AREA:
				cache.put(CompareType.AREA, new AreaComparator());
				break;

			case GREATER_DIMENSION:
				cache.put(CompareType.GREATER_DIMENSION,
						new GreaterDimComparator());
				break;

			default:
				throw new AssertionError();
			}
		}

		return cache.get(type);
	}

}
