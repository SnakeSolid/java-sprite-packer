package ru.snake.spritepacker.core.packer;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import ru.snake.spritepacker.core.packer.comparator.CompareType;
import ru.snake.spritepacker.util.Util;

public class ImagePacker {

	private static final int START_ATLAS_INDEX = 1;

	private final List<ImageData> images;

	private final int margin;
	private final int padding;

	private PackerOutput output;

	public ImagePacker(int margin, int padding) {
		this.margin = margin;
		this.padding = padding;

		images = new ArrayList<ImageData>();

		output = null;
	}

	public void addItem(ImageData item) {
		images.add(item);
	};

	public void removeItem(ImageData item) {
		images.remove(item);
	};

	public void setOutput(PackerOutput output) {
		this.output = output;
	}

	public void process(int maxWidth, int maxHeight, boolean multiAtlas) {
		if (output == null) {
			return;
		}

		if (images.size() == 0) {
			return;
		}

		if (images.size() == 1) {
			packOneImage(maxWidth, maxHeight, images.get(0));

			return;
		}

		initializeImages();

		int currentAtlas = START_ATLAS_INDEX;

		while (output.atlasSizes.containsKey(currentAtlas)) {
			currentAtlas++;
		}

		while (!images.isEmpty()) {
			Collection<ImageData> packedImages;
			Dimension atlasSize = new Dimension();

			packedImages = packImagesPass(maxWidth, maxHeight, currentAtlas,
					atlasSize);

			if (packedImages.size() == 0) {
				output.wrongImages.addAll(images);

				break;
			}

			output.packedImages.addAll(packedImages);
			output.setAtlas(currentAtlas, atlasSize.width, atlasSize.height);

			if (!multiAtlas) {
				break;
			}

			images.removeAll(packedImages);

			currentAtlas++;
		}
	}

	private Collection<ImageData> packImagesPass(int maxWidth, int maxHeight,
			int currentAtlas, Dimension atlasSize) {
		PassResult bestResult = new PassResult();
		Comparator<ImageData> bestComparator = null;

		for (CompareType type : CompareType.values()) {
			Comparator<ImageData> comparator;
			comparator = ComparatorFactory.getComparator(type);

			Collections.sort(images, comparator);

			PassResult passResult = new PassResult();

			dummyPass(maxWidth, maxHeight, currentAtlas, atlasSize, passResult);

			if (passResult.isBetterThan(bestResult)) {
				bestResult = passResult;
				bestComparator = comparator;
			}
		}

		Collections.sort(images, bestComparator);

		return actualPass(maxWidth, maxHeight, currentAtlas, atlasSize);
	}

	private void dummyPass(int maxWidth, int maxHeight, int currentAtlas,
			Dimension atlasSize, PassResult result) {
		chooseSize(maxWidth, maxHeight, atlasSize);

		Collection<ImageData> packedImages = new ArrayList<ImageData>();
		List<Rectangle> blocks = new LinkedList<Rectangle>();

		while (true) {
			blocks.clear();
			blocks.add(getFullAtlasBlock(atlasSize));

			packedImages.clear();

			for (ImageData currentImage : images) {
				Rectangle splittedBlock = tryFitImage(blocks, currentImage);

				if (splittedBlock != null) {
					currentImage.atlas = currentAtlas;
					currentImage.x = splittedBlock.x;
					currentImage.y = splittedBlock.y;

					result.addImageData(currentImage);

					blocks.remove(splittedBlock);
					packedImages.add(currentImage);
				}
			}

			if (packedImages.size() == images.size()) {
				break;
			}

			if (!increaseAtlasSize(maxWidth, maxHeight, atlasSize)) {
				break;
			}
		}

		for (Rectangle block : blocks) {
			result.addBlock(block);
		}

		result.setImageDimension(atlasSize);
	}

	private Collection<ImageData> actualPass(int maxWidth, int maxHeight,
			int currentAtlas, Dimension atlasSize) {
		chooseSize(maxWidth, maxHeight, atlasSize);

		Collection<ImageData> packedImages = new ArrayList<ImageData>();
		List<Rectangle> blocks = new LinkedList<Rectangle>();

		while (true) {
			blocks.clear();
			blocks.add(getFullAtlasBlock(atlasSize));

			packedImages.clear();

			for (ImageData currentImage : images) {
				Rectangle splittedBlock = tryFitImage(blocks, currentImage);

				if (splittedBlock != null) {
					currentImage.atlas = currentAtlas;
					currentImage.x = splittedBlock.x;
					currentImage.y = splittedBlock.y;

					blocks.remove(splittedBlock);
					packedImages.add(currentImage);
				}
			}

			if (packedImages.size() == images.size()) {
				break;
			}

			if (!increaseAtlasSize(maxWidth, maxHeight, atlasSize)) {
				break;
			}
		}

		return packedImages;
	}

	private boolean increaseAtlasSize(int maxWidth, int maxHeight,
			Dimension size) {
		if (size.width == maxWidth && size.height == maxHeight) {
			return false;
		}

		int newWidth;
		int newHeight;

		if (size.width > size.height && size.width <= maxHeight) {
			newWidth = Util.nearestPowerOf2(size.height);
			newHeight = Util.nearestPowerOf2(size.width);
		} else if (size.width <= size.height && size.width < maxWidth) {
			newWidth = 2 * size.width;
			newHeight = size.height;
		} else {
			return false;
		}

		if (newWidth > maxWidth) {
			newWidth = maxWidth;
		}

		if (newHeight > maxHeight) {
			newHeight = maxHeight;
		}

		size.setSize(newWidth, newHeight);

		return true;
	}

	private Rectangle tryFitImage(List<Rectangle> blocks, ImageData image) {
		long bestarea = Long.MAX_VALUE;
		Rectangle bestblock = null;

		for (Rectangle eachblock : blocks) {
			if (eachblock.width < image.width) {
				continue;
			}

			if (eachblock.height < image.height) {
				continue;
			}

			long currarea = eachblock.width * eachblock.height;

			if (currarea < bestarea) {
				bestarea = currarea;
				bestblock = eachblock;
			}
		}

		if (bestblock == null) {
			return null;
		}

		long fitness = Long.MIN_VALUE;
		SplitPlane plane = SplitPlane.NONE;

		// check for horizontal split
		if (bestblock.height >= image.height) {
			long currfitness;

			currfitness = calcHorizFitness(image, bestblock);

			if (fitness < currfitness) {
				fitness = currfitness;

				plane = SplitPlane.HORIZONTAL;
			}
		}

		// check for vertical split
		if (bestblock.width >= image.width) {
			long currfitness;

			currfitness = calcVertFitness(image, bestblock);

			if (fitness < currfitness) {
				fitness = currfitness;

				plane = SplitPlane.VERTICAL;
			}
		}

		if (plane == SplitPlane.HORIZONTAL) {
			splitBlockHoriz(blocks, image, bestblock);

			return bestblock;
		}

		if (plane == SplitPlane.VERTICAL) {
			splitBlockVert(blocks, image, bestblock);

			return bestblock;
		}

		return null;
	}

	private Rectangle getFullAtlasBlock(Dimension atlasSize) {
		return new Rectangle(margin, margin, atlasSize.width - 2 * margin,
				atlasSize.height - 2 * margin);
	}

	private void initializeImages() {
		for (ImageData each : images) {
			each.reset();
		}
	}

	private void chooseSize(int maxWidth, int maxHeight, Dimension size) {
		int totalarea = 0;

		for (ImageData each : images) {
			if (each.isAvaliable()) {
				totalarea += each.width * each.height;
			}
		}

		size.width = Util.nearestPowerOf2((int) Math.sqrt(totalarea));
		size.height = Util.nearestPowerOf2(totalarea / size.width);

		if (size.width > maxWidth) {
			size.width = maxWidth;
		}

		if (size.height > maxHeight) {
			size.height = maxHeight;
		}
	}

	private void packOneImage(int maxWidth, int maxHeight, ImageData image) {
		if (image.width > maxWidth - 2 * margin) {
			output.addWrongImage(image);

			return;
		}

		if (image.height > maxHeight - 2 * margin) {
			output.addWrongImage(image);

			return;
		}

		int currentAtlas = START_ATLAS_INDEX;

		while (output.atlasSizes.containsKey(currentAtlas)) {
			currentAtlas++;
		}

		image.atlas = currentAtlas;
		image.x = margin;
		image.y = margin;

		output.addPackedImage(image);

		int atlasWidth = Util.nearestPowerOf2(image.width);
		int atlasHeight = Util.nearestPowerOf2(image.height);

		if (atlasWidth > maxWidth) {
			atlasWidth = maxWidth;
		}

		if (atlasHeight > maxHeight) {
			atlasHeight = maxHeight;
		}

		output.setAtlas(currentAtlas, atlasWidth, atlasHeight);
	}

	private void splitBlockVert(List<Rectangle> blocks, ImageData image,
			Rectangle block) {
		if (block.height > image.height + padding) {
			Rectangle firstblock;

			if (block.width <= image.width + padding) {
				firstblock = new Rectangle(block.x, block.y + image.height
						+ padding, block.width, block.y - image.height
						- padding);
			} else {
				firstblock = new Rectangle(block.x, block.y + image.height
						+ padding, image.width, block.height - image.height
						- padding);
			}

			blocks.add(firstblock);
		}

		if (block.width > image.width + padding) {
			Rectangle secondblock;

			secondblock = new Rectangle(block.x + image.width + padding,
					block.y, block.width - image.width - padding, block.height);

			blocks.add(secondblock);
		}
	}

	private void splitBlockHoriz(List<Rectangle> blocks, ImageData image,
			Rectangle block) {
		if (block.width > image.width + padding) {
			Rectangle firstblock;

			if (block.height <= image.height + padding) {
				firstblock = new Rectangle(block.x + image.width + padding,
						block.y, block.width - image.width - padding,
						block.height);
			} else {
				firstblock = new Rectangle(block.x + image.width + padding,
						block.y, block.width - image.width - padding,
						image.height);
			}

			blocks.add(firstblock);
		}

		if (block.height > image.height + padding) {
			Rectangle secondblock;

			secondblock = new Rectangle(block.x, block.y + image.height
					+ padding, block.width, block.height - image.height
					- padding);

			blocks.add(secondblock);
		}
	}

	private long calcVertFitness(ImageData image, Rectangle block) {
		long currfitness = 0;

		if (block.height > image.height + padding) {
			if (block.width <= image.width + padding) {
				currfitness += block.width
						* (block.height - image.height - padding);
			} else {
				currfitness += image.width
						* (block.height - image.height - padding);
			}
		}

		if (block.width > image.width + padding) {
			currfitness += (block.width - image.width - padding) * block.height;
		}

		return currfitness;
	}

	private long calcHorizFitness(ImageData image, Rectangle block) {
		long currfitness = 0;

		if (block.width > image.width + padding) {
			if (block.height <= image.height + padding) {
				currfitness += (block.width - image.width - padding)
						* block.height;
			} else {
				currfitness += (block.width - image.width - padding)
						* image.height;
			}
		}

		if (block.height > image.height + padding) {
			currfitness += block.width
					* (block.height - image.height - padding);
		}

		return currfitness;
	}

	public Collection<ImageData> getItems() {
		return Collections.unmodifiableList(images);
	}

}
