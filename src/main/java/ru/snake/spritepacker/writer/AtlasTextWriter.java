package ru.snake.spritepacker.writer;

import java.awt.Dimension;
import java.io.File;

import ru.snake.spritepacker.core.CoreFactoryWalker;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.core.packer.ImageData;
import ru.snake.spritepacker.core.packer.PackerOutput;

public class AtlasTextWriter extends TextWriter implements CoreFactoryWalker {

	private final PackerOutput output;
	private final File destination;
	private final int atlasId;

	private String prefix;
	private String postfix;

	private String atlasName;

	private int animationIndex;

	private boolean ignoreAnimation;

	public AtlasTextWriter(PackerOutput output, File destination, int atlasId) {
		this.output = output;
		this.destination = destination;
		this.atlasId = atlasId;

		atlasName = output.getAtlasName(atlasId);

		prefix = "";
		postfix = "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public void setIgnoreAnimation(boolean ignoreAnimation) {
		this.ignoreAnimation = ignoreAnimation;
	}

	@Override
	public void start() {
		startWrite(getOutputFile());

		Dimension atlasSize = output.atlasSizes.get(atlasId);

		setValue("atlas.id", atlasId);
		setValue("atlas.name", atlasName);
		setValue("atlas.width", atlasSize.width);
		setValue("atlas.height", atlasSize.height);

		animationIndex = 0;
	}

	private File getOutputFile() {
		StringBuilder sb = new StringBuilder();

		if (!atlasName.isEmpty()) {
			sb.append(prefix);
			sb.append(atlasName);
		} else if (!prefix.isEmpty()) {
			sb.append(prefix);
		} else {
			sb.append("description");
		}

		sb.append(postfix);

		return new File(destination, sb.toString());
	}

	@Override
	public void end() {
		endWrite();
	}

	@Override
	public void startAnimation(int index, String name) {
		setValue("animation.id", index);
		setValue("animation.name", name);

		animationIndex = index;
	}

	@Override
	public void endAnimation() {
	}

	@Override
	public void startSprite(int index, String name, int offsetX, int offsetY,
			Texture texture) {
		setValue("sprite.id", index);
		setValue("sprite.name", name);
		setValue("sprite.offsetx", offsetX);
		setValue("sprite.offsety", offsetY);

		for (ImageData data : output.packedImages) {
			if (data.atlas != atlasId) {
				continue;
			}

			if (!ignoreAnimation && data.animation != animationIndex) {
				continue;
			}

			if (data.texture != texture) {
				continue;
			}

			setValue("texture.left", data.x);
			setValue("texture.top", data.y);
			setValue("texture.width", data.width);
			setValue("texture.height", data.height);

			writeLine();
		}
	}

	@Override
	public void endSprite() {
	}

}
