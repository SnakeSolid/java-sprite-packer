package ru.snake.spritepacker.core.packer;

import java.awt.Dimension;
import java.awt.Rectangle;

public class PassResult {

	private long imageArea;
	private long packedArea;
	private long blockArea;

	public PassResult() {
		imageArea = Long.MAX_VALUE;
		packedArea = Long.MIN_VALUE;
		blockArea = Long.MIN_VALUE;
	}

	public void setImageDimension(Dimension atlasSize) {
		imageArea = atlasSize.width * atlasSize.height;
	}

	public void addImageData(ImageData data) {
		packedArea = data.width * data.height;
	}

	public void addBlock(Rectangle block) {
		blockArea = block.width * block.height;
	}

	public boolean isBetterThan(PassResult other) {
		if (packedArea < other.packedArea) {
			return false;
		}

		if (imageArea > other.imageArea) {
			return false;
		}

		if (blockArea < other.blockArea) {
			return false;
		}

		return true;
	}

}
