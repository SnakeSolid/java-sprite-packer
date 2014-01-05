package ru.snake.spritepacker.core.packer;

import java.awt.Dimension;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PackerOutput {

	public final Collection<ImageData> packedImages;
	public final Collection<ImageData> wrongImages;
	public final Map<Integer, Dimension> atlasSizes;

	private final Map<Integer, String> atlasNames;

	private String namePrefix;
	private String namePostfix;

	public PackerOutput() {
		packedImages = new LinkedList<ImageData>();
		wrongImages = new LinkedList<ImageData>();
		atlasSizes = new HashMap<Integer, Dimension>();
		atlasNames = new HashMap<Integer, String>();

		namePrefix = "atlas-";
		namePostfix = "";
	}

	public void addPackedImage(ImageData data) {
		packedImages.add(data);
	}

	public void addWrongImage(ImageData data) {
		wrongImages.add(data);
	}

	public void setAtlas(int index, int width, int height) {
		StringBuilder name = new StringBuilder();

		atlasSizes.put(index, new Dimension(width, height));

		name.append(namePrefix);
		name.append(index);
		name.append(namePostfix);

		atlasNames.put(index, name.toString());
	}

	public void clear() {
		packedImages.clear();
		wrongImages.clear();
		atlasSizes.clear();
	}

	public void setNameFormat(String prefix, String postfix) {
		if (prefix == null) {
			namePrefix = "";
		} else {
			namePrefix = prefix;
		}

		if (postfix == null) {
			namePostfix = "";
		} else {
			namePostfix = postfix;
		}
	}

	public String getAtlasName(int index) {
		return atlasNames.get(index);
	}

}
